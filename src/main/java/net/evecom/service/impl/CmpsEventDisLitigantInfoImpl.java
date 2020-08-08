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
 * <P><B> Description:  矛盾纠纷排查化解主要当事人</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class CmpsEventDisLitigantInfoImpl extends BaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsEventDisLitigantInfoImpl.class);

    @Value("${daysRangeEdge.eventDisLitigantInfo}")
    private String daysRangeEdge;

    @Value("${sysDebug.eventDisLitigantInfo}")
    private boolean sysDebug;
    /**
     *  省网数据表名
     */
    private static String TABLENAME = "PINGTAN.S_PTZH_EVENT_DIS_LITIGANT_INFO";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        if (sysDebug){
            return "select * from S_PTZH_EDLI_V WHERE 1=1";
        }else {
            return "select * from S_PTZH_EDLI_V WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - " + daysRangeEdge + ", 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - " + daysRangeEdge + ", 'YYYY-MM-DD'))";
        }
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.LITIGANT_CERT_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM "  + TABLENAME + " TT WHERE LITIGANT_CERT_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("LITIGANT_CERT_NUM")};
    }

    @Override
    public String getModelName() {
        return "矛盾纠纷排查化解主要当事人";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "LITIGANT_CERT_NUM";
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

                "event_num,\n" +
                "litigant_cert_code,\n" +
                "litigant_cert_num,\n" +
                "litigant_name,\n" +
                "litigant_sex_code,\n" +
                "litigant_nation_code,\n" +
                "litigant_education_code,\n" +
                "litigant_person_type,\n" +
                "litigant_address,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("EVENT_NUM")) + "," +
                StringUtils.toSql(data.get("LITIGANT_CERT_CODE")) + "," +
                StringUtils.toSql(data.get("LITIGANT_CERT_NUM")) + "," +
                StringUtils.toSql(data.get("LITIGANT_NAME")) + "," +
                StringUtils.toSql(data.get("LITIGANT_SEX_CODE")) + "," +
                StringUtils.toSql(data.get("LITIGANT_NATION_CODE")) + "," +
                StringUtils.toSql(data.get("LITIGANT_EDUCATION_CODE")) + "," +
                StringUtils.toSql(data.get("LITIGANT_PERSON_TYPE")) + "," +
                StringUtils.toSql(data.get("LITIGANT_ADDRESS")) + "," +

                convert2SqlDate(data.get("UPLOAD_TIME")) + "," +
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
