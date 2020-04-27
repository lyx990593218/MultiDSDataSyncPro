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
 * <P><B> Description:  肇事肇祸等严重精神障碍患者</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class CmpsMentalPatientImpl extends BaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsFlowPopImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "PINGTAN.S_PTZH_POP_MENTAL_PATIENT";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
//        return "select * from S_PTZH_POP_CORRECT_PERSON_V WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD'))";
        return "select * from S_PTZH_POP_CORRECT_PERSON_V WHERE 1=1";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.IDENTITY_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM "  + TABLENAME + " TT WHERE IDENTITY_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("IDENTITY_NUM")};
    }

    @Override
    public String getModelName() {
        return "肇事肇祸等严重精神障碍患者";
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
        String insertSql = "insert into " + TABLENAME;

        insertSql += "(" +

                "identity_num,\n" +
                "family_contact,\n" +
                "family_economic_code,\n" +
                "is_in_low,\n" +
                "guardian_identity_num,\n" +
                "guardian_name,\n" +
                "guardian_contact,\n" +
                "first_onset_date,\n" +
                "now_diagnose_type,\n" +
                "is_trouble_history,\n" +
                "trouble_times,\n" +
                "last_trouble_date,\n" +
                "first_diagnose_grade,\n" +
                "risk_assess_level_code,\n" +
                "treat_situation_code,\n" +
                "hospital_name,\n" +
                "hospital_reason_code,\n" +
                "recovery_org_name,\n" +
                "partake_manager_code,\n" +
                "help_situation_code,\n" +
                "recent_visit_time,\n" +
                "next_visit_date,\n" +
                "is_dead,\n" +
                "rep_repair_situation,\n" +
                "add_time,\n" +
                "update_time,\n" +
                "grid_num,\n" +
                "grid_org_name,\n" +
                "note,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("IDENTITY_NUM")) + "," +
                StringUtils.toSql(data.get("FAMILY_CONTACT")) + "," +
                StringUtils.toSql(data.get("FAMILY_ECONOMIC_CODE")) + "," +
                StringUtils.toSql(data.get("IS_IN_LOW")) + "," +
                StringUtils.toSql(data.get("GUARDIAN_IDENTITY_NUM")) + "," +
                StringUtils.toSql(data.get("GUARDIAN_NAME")) + "," +
                StringUtils.toSql(data.get("GUARDIAN_CONTACT")) + "," +
                convert2SqlDate(data.get("FIRST_ONSET_DATE")) + "," +
                StringUtils.toSql(data.get("NOW_DIAGNOSE_TYPE")) + "," +
                StringUtils.toSql(data.get("IS_TROUBLE_HISTORY")) + "," +
                StringUtils.toSql(data.get("TROUBLE_TIMES")) + "," +
                convert2SqlDate(data.get("LAST_TROUBLE_DATE")) + "," +
                StringUtils.toSql(data.get("FIRST_DIAGNOSE_GRADE")) + "," +
                StringUtils.toSql(data.get("RISK_ASSESS_LEVEL_CODE")) + "," +
                StringUtils.toSql(data.get("TREAT_SITUATION_CODE")) + "," +
                StringUtils.toSql(data.get("HOSPITAL_NAME")) + "," +
                StringUtils.toSql(data.get("HOSPITAL_REASON_CODE")) + "," +
                StringUtils.toSql(data.get("RECOVERY_ORG_NAME")) + "," +
                StringUtils.toSql(data.get("PARTAKE_MANAGER_CODE")) + "," +
                StringUtils.toSql(data.get("HELP_SITUATION_CODE")) + "," +
                StringUtils.toSql(data.get("RECENT_VISIT_TIME")) + "," +
                convert2SqlDate(data.get("NEXT_VISIT_DATE")) + "," +
                StringUtils.toSql(data.get("IS_DEAD")) + "," +
                StringUtils.toSql(data.get("REP_REPAIR_SITUATION")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_ORG_NAME")) + "," +
                StringUtils.toSql(data.get("NOTE")) + "," +

                convert2SqlDate(data.get("ADD_TIME")) + ","+
                convert2SqlDate(data.get("UPDATE_TIME")) + ","+
                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
