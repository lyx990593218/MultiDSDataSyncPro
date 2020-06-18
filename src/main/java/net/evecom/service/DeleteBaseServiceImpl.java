/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.service;

import net.evecom.domain.PageList;
import net.evecom.utils.StringUtils;
import net.evecom.utils.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 描述 基类
 *
 * @author Fandy Liu
 * @created 2018年12月11日 上午9:57:24
 */
public abstract class DeleteBaseServiceImpl {
    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteBaseServiceImpl.class);

    private final static Executor executor = Executors.newFixedThreadPool(4);

    /**
     * The Jdbc template.
     */
    @Autowired
    @Qualifier("ds1JdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    /**
     * The Jdbc template.
     */
    @Autowired
    @Qualifier("ds2JdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    /**
     * 对接失败日志的文件位置
     */
    @Value("${errorDatalogs}")
    protected String errorDatalogs;
    /**
     * 每次对接的数据条数
     */
    @Value("${onceSyncRows}")
    protected Integer onceSyncRows;

    /**
     * oracel 分页sql模板
     */
    private String oracelPageTpl = " SELECT * FROM (SELECT A.*, ROWNUM RN FROM ({}) "
            + " A WHERE ROWNUM <= {})  WHERE RN >= {}";

    public String findSqlTpl = "SELECT T.* FROM ( SELECT T.* FROM ( SELECT TT.*,\n" +
            "   ROW_NUMBER() OVER(PARTITION BY TT.{} ORDER BY TT.UPLOAD_TIME DESC, TT.ADD_TIME DESC, TT.UPDATE_TIME DESC) RNS\n" +
            "   FROM PINGTAN.{} TT WHERE 1 = 1 ) T  WHERE 1 = 1 AND T.RNS = 1) T WHERE 1 = 1 AND T.UPLOAD_STATUS = 'I'\n";

    /**
     * 只查询一列数据类型对象。用于只有一行查询结果的数据
     *
     * @param sql    the sql
     * @param params the params
     * @param cla    the cla
     * @return object object
     * @author Fandy Liu
     * @created 2018年12月10日 下午5:22:19
     */
    protected Object queryOneColumnForSigetonRow(String sql, Object[] params, Class<?> cla) {
        Object result = null;
        try {
            if (params == null || params.length > 0)
                result = jdbcTemplate2.queryForObject(sql, params, cla);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 查询分页（Oracle数据库）
     *
     * @param sql     终执行查询的语句
     * @param params  填充sql语句中的问号占位符数
     * @param page    想要第几页的数据
     * @param pagerow 每页显示多少条数
     * @return pageList对象 page list
     * @author Fandy Liu
     * @created 2018年12月10日 下午5:22:19
     */
    protected PageList queryByPageForOracle(String sql, Object[] params, int page, int pagerow) {
        // String rowsql="select count(*) cnts from ("+sql+") "; //查询总行数sql
        int pages = 0; // 总页数
        /*
         * int rows=(Integer)queryOneColumnForSigetonRow(rowsql, params);
         * //查询总行数 //判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1 if
         * (rows % pagerow == 0) { pages = rows / pagerow; } else { pages = rows
         * / pagerow + 1; }
         */
        // 查询第page页的数据sql语句
        sql = StringUtils.format(oracelPageTpl, sql, (page * pagerow), (page - 1) * pagerow + 1);
        // 查询第page页数据
        @SuppressWarnings("rawtypes")
        List list = jdbcTemplate2.queryForList(sql, params);
        // 返回分页格式数据
        PageList pl = new PageList();
        pl.setPage(page); // 设置显示的当前页数
        pl.setPages(pages); // 设置总页数
        pl.setList(list); // 设置当前页数据
        // pl.setTotalRows(rows);//设置总记录数
        return pl;
    }

    /**
     * 描述 计算页数
     *
     * @param sql     the sql
     * @param params  the params
     * @param pagerow the pagerow
     * @return integer integer
     * @author Fandy Liu
     * @created 2018年12月11日 上午9:56:55
     */
    protected Integer caculatePages(String sql, Object[] params, int pagerow) {
        String rowsql = "select count(*) from (" + sql + ") cnts"; // 查询总行数sql
        int pages = 0; // 总页数
        int rows = (Integer) queryOneColumnForSigetonRow(rowsql, params, Integer.class); // 查询总行数
        LOG.info("rows:" + rows);
        // 判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1
        if (rows % pagerow == 0) {
            pages = rows / pagerow;
        } else {
            pages = rows / pagerow + 1;
        }
        return pages;
    }

    /**
     * 开始对接
     *
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:06:26
     */
    public String run() {
        final String findSql = getFindSql();
        final Object[] params = getFindSqlParams();
        // 计算总页数
        Integer pages = caculatePages(findSql, params, onceSyncRows);
        Integer successQuanties = 0;
        Integer errorQuanties = 0;

        final List<String> errorIds = new ArrayList<>();// 记录错误的组件
        final StringBuffer retError = new StringBuffer();


        doErrorLog(pages, "总页数");
        for (int page = 1; page <= pages; page++) {

            final int finalPage = page;

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        PageList pageList = queryByPageForOracle(findSql, params, finalPage, onceSyncRows);
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> datas = (List<Map<String, Object>>) pageList.getList();
                        for (Map<String, Object> data : datas) {
                            try {
                                handleTargetMapData(setTargetMapData(data));
//                                successQuanties++;
                            } catch (Exception e) {
                                e.printStackTrace();
                                doErrorLog(data, e.getMessage());
//                                errorQuanties++;
//                                errorIds.add(getErrorIdValue(data));
//                                retError.append(StringUtils.format("错误数据的Id：[{}],发生异常：{}<br/>"
//                                        , getErrorIdValue(data), e.getMessage()));
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            });
        }
        if (errorIds.size() > 0) {
            String errorMsg = StringUtils.format("错误的Id集合:{}<br/>", errorIds.toString());
            retError.append(errorMsg);
            doErrorLog(errorMsg, null);
        }
        retError.append(StringUtils.format("查询数据的Sql:{}<br/>", getFindSql()));
        retError.append(StringUtils.format("成功条数:{}，失败条数:{}。", successQuanties, errorQuanties));
        return retError.toString();

    }

    /**
     * 描述 获取错误数据的id值
     *
     * @param data
     * @return
     * @author Fandy Liu
     * @created 2018年12月12日 下午10:53:15
     */
    private String getErrorIdValue(Map<String, Object> data) {
        String ourPkFieldName = getOurPkFieldName() == null ? "ID" : getOurPkFieldName();
        return data.get(ourPkFieldName) == null ? data.get("ID").toString()
                : data.get(ourPkFieldName).toString();
    }


    /**
     * 错误文件记录日志
     *
     * @param data     the data
     * @param errorMsg the error msg
     * @throws IOException
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:07:35
     */
    public void doErrorLog(Object data, String errorMsg) {
        try {
            String modelName = getModelName();// 模块名称
            File logFile = new File(this.errorDatalogs+ File.separator + DateUtil.dateToString(new Date(), "yyyy-MM-dd") + File.separator + modelName + ".log");
            File parent = logFile.getParentFile();
            if (!parent.exists())
                parent.mkdirs();// 不存在创建目录
            if (!logFile.exists())
                logFile.createNewFile();// 不存在创建文件
            String appendMsg = "";
            if (errorMsg == null) {
                appendMsg = data.toString() + "\n";
            } else {
                appendMsg = StringUtils.format("数据:{}发生错误，错误原因:{};\n", data.toString(), errorMsg);

            }
            FileUtils.writeStringToFile(logFile, appendMsg, "UTF-8", true);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

    }

    /**
     * 获取数据的sql
     *
     * @return find sql
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:32:45
     */
    public abstract String getFindSql();

    /**
     * 描述 数据sql的查询参数
     *
     * @return object [ ]
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:33:08
     */
    public abstract Object[] getFindSqlParams();

    /**
     * 获取目标数据的sql
     *
     * @return find sql
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:32:45
     */
    public abstract String getFindTargetSql();

    /**
     * 描述 获取目标数据sql的查询参数
     *
     * @return object [ ]
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:33:08
     * @param data
     */
    public abstract Object[] getFindTargetSqlParams(Map<String, Object> data);

    /**
     * 获取模块名称
     *
     * @return model name
     * @author Fandy Liu
     * @created 2018年12月11日 上午10:33:28
     */
    public abstract String getModelName();

    /**
     * 我们库表的主键字段 默认是ID
     *
     * @return our pk field name
     * @author Fandy Liu
     * @created 2018年12月11日 上午11:25:22
     */
    public abstract String getOurPkFieldName();

    /**
     * 描述
     *
     * @param data the data
     * @return target map data
     * @throws Exception the exception
     * @author Fandy Liu
     * @created 2018年12月11日 上午11:23:13
     */
    public abstract Map<String, Object> setTargetMapData(Map<String, Object> data) throws Exception;

    /**
     * @param data the data
     * @param i the data
     * @author Douglas Lai
     * @date 2020/4/22 10:46
     * @return: void
     */
    protected abstract void insertTargetDataSourceMapData(Map<String, Object> data, String i);

    /**
     * 描述 处理返回值
     *
     * @param data the data
     * @throws Exception the exception
     * @author Tab You
     * @created 2018 /12/12 19:17:56 Handle return code map.
     */
    public void handleTargetMapData(Map<String, Object> data) throws Exception{
        List<Map<String, Object>> list = jdbcTemplate.queryForList(getFindTargetSql(), getFindTargetSqlParams(data));

        if (list == null || list.size() < 1){
            // 查询目标数据库无数据，直接插入
            doErrorLog(data.get(getOurPkFieldName()), "无数据,删除");
            insertTargetDataSourceMapData(data, "D");
//            insertTargetDataSourceMapData(data, "I");
        }else{
            doErrorLog(data.get(getOurPkFieldName()), "有数据, 不删除");
            // 查询目标数据库有数据，将第一条数据改成删除标志后插回,然后再插入新数据
//            insertTargetDataSourceMapData(list.get(0), "D");
//            insertTargetDataSourceMapData(data, "I");
        }
    }

    public String convert2SqlDate(Object o){
        return  (o == null ? "sysdate" : "to_date(" + StringUtils.toSql(o.toString().split("\\.")[0]) + ", 'YYYY-MM-DD HH24:MI:SS')");
    }



}
