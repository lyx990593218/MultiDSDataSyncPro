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
 * 实有人口
 * @author Fandy Liu
 * @created 2018年11月19日 上午10:34:51
 */
@Service
public class DeleteCmpsPopImpl extends DeleteBaseServiceImpl implements IDataSync{

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsPopImpl.class);

    /**
     *  省网数据表名
     */
    private final static String TABLENAME = "S_PTZH_POP_REAL_PERSON";

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
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT * FROM "+TABLENAME+" WHERE 1=1 AND CERT_NUM = ? ";
    }
    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("CERT_NUM")};
    }
    @Override
    public String getModelName() {
       return "删除实有人口";
    }
    @Override
    public String getOurPkFieldName() {
        return "CERT_NUM";
    }
    @Override
    public Map<String, Object> setTargetMapData(Map<String, Object> data) throws Exception {
        Map<String,Object> params = new HashMap<>();

        params = data;

        return params;
    }

    @Override
    protected void insertTargetDataSourceMapData(Map<String, Object> data, String i) {
        String insertSql = "insert into pingtan."+TABLENAME;
        insertSql += "(CERT_CODE\n" +
                ",CERT_NUM\n" +
                ",CHINESE_NAME\n" +
                ",SEX_CODE\n" +
                ",BIRTHDAY\n" +
                ",NATION_CODE\n" +
                ",NATIVE_PLACE_CODE\n" +
                ",GRID_NUM\n" +
                ",MARITAL_STATUS\n" +
                ",POLITICS_CODE\n" +
                ",EDUCATION_CODE\n" +
                ",DOMICILE_CODE\n" +
                ",DOMICILE_ADDRESS\n" +
                ",REAL_POP_MANAGE_TYPE\n" +
                ",FOREIGN_NAME\n" +
                ",USED_NAME\n" +
                ",RELIGIOUS_BELIEF_CODE\n" +
                ",CONTACT\n" +
                ",PROFESSION_TYPE\n" +
                ",PROFESSION_NAME\n" +
                ",SERVICE_PLACE_NAME\n" +
                ",RESIDENCE_CODE\n" +
                ",RESIDENCE_ADDRESS\n" +
                ",RESIDENCE_TYPE\n" +
                ",POP_CANCEL_TYPE\n" +
                ",CANCEL_DATE\n" +
                ",UPDATE_TIME\n" +
                ",HOUSEHOLD_NUM\n" +
                ",ADD_TIME\n" +
                ",WORK_UNIT_NUM\n" +
                ",WORK_UNIT_NAME\n" +
                ",POSITION\n" +
                ",PHOTO_NAME\n" +
                ",CERT_FRONT_PHOTH_NAME\n" +
                ",CERT_OPPOSITE_PHOTO_NAME\n" +
                ",IDENTITY_ISSUE_AGENCY\n" +
                ",IDENTITY_VALID_PERIOD\n" +
                ",MILITARY_SERVICE_STATUS\n" +
                ",EMPLOYMENT_STATUS\n" +
                ",EMP_UNEMP_CERT_NUM\n" +
                ",UPLOAD_TIME\n" +
                ",UPLOAD_STATUS)";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("CERT_CODE"))+
                "," +
                StringUtils.toSql(data.get("CERT_NUM"))+
                "," +
                StringUtils.toSql(data.get("CHINESE_NAME"))+
                "," +
                StringUtils.toSql(data.get("SEX_CODE"))+

                "," +
                convert2SqlDate(data.get("BIRTHDAY"))+
                "," +
                StringUtils.toSql(data.get("NATION_CODE"))+
                "," +
                StringUtils.toSql(data.get("NATIVE_PLACE_CODE"))+
                "," +
                StringUtils.toSql(data.get("GRID_NUM"))+
                "," +
                StringUtils.toSql(data.get("MARITAL_STATUS"))+
                "," +
                StringUtils.toSql(data.get("POLITICS_CODE"))+
                "," +
                StringUtils.toSql(data.get("EDUCATION_CODE"))+
                "," +
                StringUtils.toSql(data.get("DOMICILE_CODE"))+
                "," +
                StringUtils.toSql(data.get("DOMICILE_ADDRESS"))+
                "," +
                StringUtils.toSql(data.get("REAL_POP_MANAGE_TYPE"))+
                "," +
                StringUtils.toSql(data.get("FOREIGN_NAME"))+
                "," +
                StringUtils.toSql(data.get("USED_NAME"))+
                "," +
                StringUtils.toSql(data.get("RELIGIOUS_BELIEF_CODE"))+
                "," +
                StringUtils.toSql(data.get("CONTACT"))+
                "," +
                StringUtils.toSql(data.get("PROFESSION_TYPE"))+
                "," +
                StringUtils.toSql(data.get("PROFESSION_NAME"))+
                "," +
                StringUtils.toSql(data.get("SERVICE_PLACE_NAME"))+
                "," +
                StringUtils.toSql(data.get("RESIDENCE_CODE"))+
                "," +
                StringUtils.toSql(data.get("RESIDENCE_ADDRESS"))+
                "," +
                StringUtils.toSql(data.get("RESIDENCE_TYPE"))+
                "," +
                StringUtils.toSql(data.get("POP_CANCEL_TYPE"))+
                "," +
                StringUtils.toSql(data.get("CANCEL_DATE"))+
                "," +
                convert2SqlDate(data.get("UPDATE_TIME"))+
                "," +
                StringUtils.toSql(data.get("HOUSEHOLD_NUM"))+
                "," +
                convert2SqlDate(data.get("ADD_TIME"))+
                "," +
                StringUtils.toSql(data.get("WORK_UNIT_NUM"))+
                "," +
                StringUtils.toSql(data.get("WORK_UNIT_NAME"))+
                "," +
                StringUtils.toSql(data.get("POSITION"))+
                "," +
                StringUtils.toSql(data.get("PHOTO_NAME"))+
                "," +
                StringUtils.toSql(data.get("CERT_FRONT_PHOTH_NAME"))+
                "," +
                StringUtils.toSql(data.get("CERT_OPPOSITE_PHOTO_NAME"))+
                "," +
                StringUtils.toSql(data.get("IDENTITY_ISSUE_AGENCY"))+
                "," +
                StringUtils.toSql(data.get("IDENTITY_VALID_PERIOD"))+
                "," +
                StringUtils.toSql(data.get("MILITARY_SERVICE_STATUS"))+
                "," +
                StringUtils.toSql(data.get("EMPLOYMENT_STATUS"))+
                "," +
                StringUtils.toSql(data.get("EMP_UNEMP_CERT_NUM"))+

                ", sysdate" +
//                convert2SqlDate(data.get("UPLOAD_TIME"))+
                "," +
//                StringUtils.toSql(data.get("UPLOAD_STATUS"))+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
