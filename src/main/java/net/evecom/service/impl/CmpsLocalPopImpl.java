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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 *  户籍人口实现
 * @author Fandy Liu
 * @created 2018年11月19日 上午10:34:51
 */
@Service
public class CmpsLocalPopImpl extends BaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsLocalPopImpl.class);

    @Value("${daysRangeEdge.localpop}")
    private String daysRangeEdge;

    @Value("${sysDebug.localpop}")
    private boolean sysDebug;

    /**
     *  省网数据表名
     */
    private final static String TABLENAME = "S_PTZH_POP_LOCAL_REGIST_POP";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        if (sysDebug){
            return "select * from S_PTZH_POP_LOCAL_REGIST_POP WHERE 1=1";
        }else {
            return "select * from "+TABLENAME+" WHERE 1=1 AND (TO_CHAR(ADD_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - "+daysRangeEdge+", 'YYYY-MM-DD') OR TO_CHAR(UPDATE_TIME, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE - "+daysRangeEdge+", 'YYYY-MM-DD'))";
        }
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.IDENTITY_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM PINGTAN."+TABLENAME+" TT WHERE IDENTITY_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("IDENTITY_NUM")};
    }

    @Override
    public String getModelName() {
       return "户籍人口";
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
        String insertSql = "insert into pingtan."+TABLENAME;
        insertSql += "(IDENTITY_NUM\n" +
                ",PERSON_HOUSE_CONSIST_CODE\n" +
                ",HOUSEHOLDER_IDENTITY_NUM\n" +
                ",HOUSEHOLDER_NAME\n" +
                ",HOUSEHOLDER_RELATION_CODE\n" +
                ",HOUSEHOLDER_CONTACT\n" +
                ",GRID_NUM\n" +
                ",GRID_ORG_NAME\n" +
                ",ADD_TIME\n" +
                ",UPDATE_TIME\n" +
                ",UPLOAD_TIME\n" +
                ",UPLOAD_STATUS)";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("IDENTITY_NUM"))+
                "," +
                StringUtils.toSql(data.get("PERSON_HOUSE_CONSIST_CODE"))+
                "," +
                StringUtils.toSql(data.get("HOUSEHOLDER_IDENTITY_NUM"))+
                "," +
                StringUtils.toSql(data.get("HOUSEHOLDER_NAME"))+
                "," +
                StringUtils.toSql(data.get("HOUSEHOLDER_RELATION_CODE"))+
                "," +
                StringUtils.toSql(data.get("HOUSEHOLDER_CONTACT"))+
                "," +
                StringUtils.toSql(data.get("GRID_NUM"))+
                "," +
                StringUtils.toSql(data.get("GRID_ORG_NAME"))+
                "," +
                convert2SqlDate(data.get("ADD_TIME"))+
                "," +
                convert2SqlDate(data.get("UPDATE_TIME"))+
                "," +
                convert2SqlDate(data.get("UPLOAD_TIME"))+
                "," +
                StringUtils.toSql(i)+
                ")";

        jdbcTemplate2.execute(insertSql);
    }


}
