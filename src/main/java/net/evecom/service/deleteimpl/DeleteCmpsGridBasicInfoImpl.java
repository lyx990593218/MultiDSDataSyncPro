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
 * <P><B> Description:  网格基本信息</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsGridBasicInfoImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsGridBasicInfoImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_GRID_GRID_BASIC_INFO";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return StringUtils.format(findSqlTpl, getOurPkFieldName(), TABLENAME);
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT * FROM "+TABLENAME+"_V WHERE 1=1 AND GRID_NUM = ?";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("GRID_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除网格基本信息";
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

                "grid_name,\n" +
                "grid_num,\n" +
                "grid_type,\n" +
                "business_dept,\n" +
                "street_code,\n" +
                "street_name,\n" +
                "village_code,\n" +
                "village_name,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("GRID_NAME")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_TYPE")) + "," +
                StringUtils.toSql(data.get("BUSINESS_DEPT")) + "," +
                StringUtils.toSql(data.get("STREET_CODE")) + "," +
                StringUtils.toSql(data.get("STREET_NAME")) + "," +
                StringUtils.toSql(data.get("VILLAGE_CODE")) + "," +
                StringUtils.toSql(data.get("VILLAGE_NAME")) + "," +

                //                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                " sysdate,"+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
