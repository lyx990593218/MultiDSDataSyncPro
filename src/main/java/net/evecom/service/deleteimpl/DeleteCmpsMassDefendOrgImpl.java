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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <P><B> Description:  群防群治组织</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsMassDefendOrgImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsMassDefendOrgImpl.class);

    /**
     *  省网数据表名
     */
    private final static String TABLENAME = "S_PTZH_GRID_MASS_DEFEND_ORG";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return StringUtils.format(findSqlTpl, getOurPkFieldName(), TABLENAME);
//        return "select * from "+TABLENAME+"_V WHERE 1=1";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "select * from "+TABLENAME+"_V WHERE 1=1 AND MASS_ORG_NAME = ?";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("MASS_ORG_NAME")};
    }

    @Override
    public String getModelName() {
        return "删除群防群治组织";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "MASS_ORG_NAME";
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

                "mass_org_name,\n" +
                "mass_org_type,\n" +
                "mass_org_level_code,\n" +
                "guide_org_name,\n" +
                "mamber_amount,\n" +
                "main_function,\n" +
                "location_address,\n" +
                "principal_contact,\n" +
                "longitude,\n" +
                "latitude,\n" +
                "grid_name,\n" +
                "grid_num,\n" +
                "grid_org_name,\n" +
                "add_time,\n" +
                "update_time,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("MASS_ORG_NAME")) + "," +
                StringUtils.toSql(data.get("MASS_ORG_TYPE")) + "," +
                StringUtils.toSql(data.get("MASS_ORG_LEVEL_CODE")) + "," +
                StringUtils.toSql(data.get("GUIDE_ORG_NAME")) + "," +
                StringUtils.toSql(data.get("MAMBER_AMOUNT")) + "," +
                StringUtils.toSql(data.get("MAIN_FUNCTION")) + "," +
                StringUtils.toSql(data.get("LOCATION_ADDRESS")) + "," +
                StringUtils.toSql(data.get("PRINCIPAL_CONTACT")) + "," +
                StringUtils.toSql(data.get("LONGITUDE")) + "," +
                StringUtils.toSql(data.get("LATITUDE")) + "," +
                StringUtils.toSql(data.get("GRID_NAME")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_ORG_NAME")) + "," +

                convert2SqlDate(data.get("ADD_TIME")) + ","+
                convert2SqlDate(data.get("UPDATE_TIME")) + ","+
                //                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                " sysdate,"+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
