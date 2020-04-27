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
 * <P><B> Description:  刑满释放人员</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class CmpsEmancipistPopImpl extends BaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsEmancipistPopImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "PINGTAN.S_PTZH_POP_EMANCIPIST";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
//        return "select * from S_PTZH_POP_EMANCIPIST_V WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD'))";
        return "select * from S_PTZH_POP_EMANCIPIST_V WHERE 1=1";
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
        return "刑满释放人员";
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
                "is_recidivist,\n" +
                "original_charge_code,\n" +
                "original_sentence,\n" +
                "prison_place,\n" +
                "release_date,\n" +
                "risk_assess_type,\n" +
                "join_date,\n" +
                "join_situation_code,\n" +
                "settle_date,\n" +
                "settle_situation_code,\n" +
                "unsettle_reason,\n" +
                "help_situation_code,\n" +
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
                StringUtils.toSql(data.get("IS_RECIDIVIST")) + "," +
                StringUtils.toSql(data.get("ORIGINAL_CHARGE_CODE")) + "," +
                StringUtils.toSql(data.get("ORIGINAL_SENTENCE")) + "," +
                StringUtils.toSql(data.get("PRISON_PLACE")) + "," +
                convert2SqlDate(data.get("RELEASE_DATE")) + "," +
                StringUtils.toSql(data.get("RISK_ASSESS_TYPE")) + "," +
                convert2SqlDate(data.get("JOIN_DATE")) + "," +
                StringUtils.toSql(data.get("JOIN_SITUATION_CODE")) + "," +
                convert2SqlDate(data.get("SETTLE_DATE")) + "," +
                StringUtils.toSql(data.get("SETTLE_SITUATION_CODE")) + "," +
                StringUtils.toSql(data.get("UNSETTLE_REASON")) + "," +
                StringUtils.toSql(data.get("HELP_SITUATION_CODE")) + "," +
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
