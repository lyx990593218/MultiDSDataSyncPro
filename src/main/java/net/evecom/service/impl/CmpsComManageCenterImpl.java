/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.service.impl;
import net.evecom.service.BaseServiceImpl;
import net.evecom.service.IDataSync;
import net.evecom.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <P><B> Description:  吸毒人员</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class CmpsComManageCenterImpl extends BaseServiceImpl implements IDataSync{

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsFlowPopImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "PINGTAN.S_PTZH_GRID_COM_MANAGE_CENTER";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
//        return "select * from S_PTZH_GCMC_V WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD'))";
        return "select * from S_PTZH_GCMC_V WHERE 1=1";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.CENTER_NAME ORDER BY TT.UPLOAD_TIME DESC) RN FROM "  + TABLENAME + " TT WHERE CENTER_NAME = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("CENTER_NAME")};
    }

    @Override
    public String getModelName() {
        return "综治中心";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "CENTER_NAME";
    }
    @Override
    public Map<String, Object> setTargetMapData(Map<String, Object> data) throws Exception {
        Map<String,Object> params = new HashMap<>();

        params = data;

        return params;
    }

    @Override
    protected void insertTargetDataSourceMapData(Map<String, Object> data, String i) {
        String insertSql = "insert into " + TABLENAME;

        insertSql += "(" +

                "center_name,\n" +
                "center_contact,\n" +
                "center_level_code,\n" +
                "principal_name,\n" +
                "principal_contact,\n" +
                "form_unit,\n" +
                "location_code,\n" +
                "location_address,\n" +
                "longitude,\n" +
                "latitude,\n" +
                "grid_type,\n" +
                "business_dept,\n" +
                "grid_num,\n" +
                "grid_org_name,\n" +
                "add_time,\n" +
                "update_time,\n" +
                "upload_time,\n" +
                "upload_status" +


                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("CENTER_NAME")) + "," +
                StringUtils.toSql(data.get("CENTER_CONTACT")) + "," +
                StringUtils.toSql(data.get("CENTER_LEVEL_CODE")) + "," +
                StringUtils.toSql(data.get("PRINCIPAL_NAME")) + "," +
                StringUtils.toSql(data.get("PRINCIPAL_CONTACT")) + "," +
                StringUtils.toSql(data.get("FORM_UNIT")) + "," +
                StringUtils.toSql(data.get("LOCATION_CODE")) + "," +
                StringUtils.toSql(data.get("LOCATION_ADDRESS")) + "," +
                StringUtils.toSql(data.get("LONGITUDE")) + "," +
                StringUtils.toSql(data.get("LATITUDE")) + "," +
                StringUtils.toSql(data.get("GRID_TYPE")) + "," +
                StringUtils.toSql(data.get("BUSINESS_DEPT")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_ORG_NAME")) + "," +

                convert2SqlDate(data.get("ADD_TIME")) + ","+
                convert2SqlDate(data.get("UPDATE_TIME")) + ","+
                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
