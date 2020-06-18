/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.service.deleteimpl;

import net.evecom.service.DeleteBaseServiceImpl;
import net.evecom.service.IDataSync;
import net.evecom.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <P><B> Description:  网格单元与网格人员关联关系</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsGridPopJoinImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsGridPopJoinImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_GRID_POP_JOIN";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return StringUtils.format(findSqlTpl, "GRID_NUM, TT.PERSON_NUM", TABLENAME);
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "select * from "+TABLENAME+"_V WHERE 1=1 AND GRID_NUM = ? AND PERSON_NUM = ?";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("GRID_NUM"),data.get("PERSON_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除网格单元与网格人员关联关系";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "GRID_NUM";
    }
    @Override
    public Map<String, Object> setTargetMapData(Map<String, Object> data) throws Exception {
        Map<String,Object> params = new HashMap<>();

        params = data;

        return params;
    }

    @Override
    protected void insertTargetDataSourceMapData(Map<String, Object> data, String i) {
        String insertSql = "insert into PINGTAN." + TABLENAME;

        insertSql += "(" +

                "person_num,\n" +
                "grid_num,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("PERSON_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +

                //                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                " sysdate,"+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
