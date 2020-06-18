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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <P><B> Description:  社区矫正人员</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class CmpsCorrectPersonImpl extends BaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsCorrectPersonImpl.class);

    @Value("${daysRangeEdge.correctPerson}")
    private String daysRangeEdge;

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_POP_CORRECT_PERSON";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return "select * from "+TABLENAME+"_V WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - "+daysRangeEdge+", 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - "+daysRangeEdge+", 'YYYY-MM-DD'))";
//        return "select * from S_PTZH_POP_CORRECT_PERSON_V WHERE 1=1";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.IDENTITY_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM PINGTAN."  + TABLENAME + " TT WHERE IDENTITY_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("IDENTITY_NUM")};
    }

    @Override
    public String getModelName() {
        return "社区矫正人员";
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
                "correct_person_num,\n" +
                "old_detain_place,\n" +
                "correct_type,\n" +
                "case_type,\n" +
                "charge_name,\n" +
                "original_sentence,\n" +
                "sentence_start_date,\n" +
                "sentence_end_date,\n" +
                "correct_start_date,\n" +
                "correct_end_date,\n" +
                "receive_way_code,\n" +
                "four_history_code,\n" +
                "is_recidivist,\n" +
                "three_involve_code,\n" +
                "is_create_correct_group,\n" +
                "correct_group_form_type,\n" +
                "correct_release_type,\n" +
                "is_takeoff,\n" +
                "takeoff_reason,\n" +
                "inspect_takeoff_desc,\n" +
                "takeoff_correction_desc,\n" +
                "is_leaky,\n" +
                "leaky_reason,\n" +
                "inspect_leaky_desc,\n" +
                "leaky_correction_desc,\n" +
                "reward_punishment_desc,\n" +
                "execution_change_desc,\n" +
                "is_recrime,\n" +
                "recrime_name,\n" +
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
                StringUtils.toSql(data.get("CORRECT_PERSON_NUM")) + "," +
                StringUtils.toSql(data.get("OLD_DETAIN_PLACE")) + "," +
                StringUtils.toSql(data.get("CORRECT_TYPE")) + "," +
                StringUtils.toSql(data.get("CASE_TYPE")) + "," +
                StringUtils.toSql(data.get("CHARGE_NAME")) + "," +
                StringUtils.toSql(data.get("ORIGINAL_SENTENCE")) + "," +
                convert2SqlDate(data.get("SENTENCE_START_DATE")) + "," +
                convert2SqlDate(data.get("SENTENCE_END_DATE")) + "," +
                convert2SqlDate(data.get("CORRECT_START_DATE")) + "," +
                convert2SqlDate(data.get("CORRECT_END_DATE")) + "," +
                StringUtils.toSql(data.get("RECEIVE_WAY_CODE")) + "," +
                StringUtils.toSql(data.get("FOUR_HISTORY_CODE")) + "," +
                StringUtils.toSql(data.get("IS_RECIDIVIST")) + "," +
                StringUtils.toSql(data.get("THREE_INVOLVE_CODE")) + "," +
                StringUtils.toSql(data.get("IS_CREATE_CORRECT_GROUP")) + "," +
                StringUtils.toSql(data.get("CORRECT_GROUP_FORM_TYPE")) + "," +
                StringUtils.toSql(data.get("CORRECT_RELEASE_TYPE")) + "," +
                StringUtils.toSql(data.get("IS_TAKEOFF")) + "," +
                StringUtils.toSql(data.get("TAKEOFF_REASON")) + "," +
                StringUtils.toSql(data.get("INSPECT_TAKEOFF_DESC")) + "," +
                StringUtils.toSql(data.get("TAKEOFF_CORRECTION_DESC")) + "," +
                StringUtils.toSql(data.get("IS_LEAKY")) + "," +
                StringUtils.toSql(data.get("LEAKY_REASON")) + "," +
                StringUtils.toSql(data.get("INSPECT_LEAKY_DESC")) + "," +
                StringUtils.toSql(data.get("LEAKY_CORRECTION_DESC")) + "," +
                StringUtils.toSql(data.get("REWARD_PUNISHMENT_DESC")) + "," +
                StringUtils.toSql(data.get("EXECUTION_CHANGE_DESC")) + "," +
                StringUtils.toSql(data.get("IS_RECRIME")) + "," +
                StringUtils.toSql(data.get("RECRIME_NAME")) + "," +
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
