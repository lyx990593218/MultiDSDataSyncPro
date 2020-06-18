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
 * <P><B> Description:  艾滋病危险人员</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsAidsPersonImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsAidsPersonImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_POP_ADIS_PERSON";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return StringUtils.format(findSqlTpl, getOurPkFieldName(), TABLENAME);
//        return "select * from "+TABLENAME+"_V WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - "+daysRangeEdge+", 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - "+daysRangeEdge+", 'YYYY-MM-DD'))";
//        return "select * from S_PTZH_POP_ADIS_PERSON_V WHERE 1=1 ";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "select * from S_PTZH_POP_ADIS_PERSON_V WHERE 1=1 AND IDENTITY_NUM = ?";
//        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.IDENTITY_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM PINGTAN."  + TABLENAME + " TT WHERE IDENTITY_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("IDENTITY_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除艾滋病危险人员";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "IDENTITY_NUM";
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

                "identity_num,\n" +
                "infect_route_code,\n" +
                "is_crmie_history,\n" +
                "crime_situation,\n" +
                "case_type,\n" +
                "foucs_type,\n" +
                "help_situation,\n" +
                "helper_name,\n" +
                "helper_contact,\n" +
                "receive_situation_code,\n" +
                "receive_org_name,\n" +
                "grid_num,\n" +
                "grid_org_name,\n" +
                "add_time,\n" +
                "update_time,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("IDENTITY_NUM")) + "," +
                StringUtils.toSql(data.get("INFECT_ROUTE_CODE")) + "," +
                StringUtils.toSql(data.get("IS_CRMIE_HISTORY")) + "," +
                StringUtils.toSql(data.get("CRIME_SITUATION")) + "," +
                StringUtils.toSql(data.get("CASE_TYPE")) + "," +
                StringUtils.toSql(data.get("FOUCS_TYPE")) + "," +
                StringUtils.toSql(data.get("HELP_SITUATION")) + "," +
                StringUtils.toSql(data.get("HELPER_NAME")) + "," +
                StringUtils.toSql(data.get("HELPER_CONTACT")) + "," +
                StringUtils.toSql(data.get("RECEIVE_SITUATION_CODE")) + "," +
                StringUtils.toSql(data.get("RECEIVE_ORG_NAME")) + "," +
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
