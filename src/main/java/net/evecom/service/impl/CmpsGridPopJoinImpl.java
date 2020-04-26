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
 * <P><B> Description:  网格单元与网格人员关联关系</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class CmpsGridPopJoinImpl extends BaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmpsFlowPopImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "PINGTAN.S_PTZH_GRID_POP_JOIN";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return "select * from S_PTZH_GRID_POP_JOIN_V WHERE 1=1";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.GRID_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM "  + TABLENAME + " TT WHERE GRID_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("GRID_NUM")};
    }

    @Override
    public String getModelName() {
        return "网格单元与网格人员关联关系";
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
        String insertSql = "insert into " + TABLENAME;

        insertSql += "(" +

                "person_num,\n" +
                "grid_num,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("PERSON_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +

                (StringUtils.toSql(data.get("UPLOAD_TIME")) == null ? "sysdate" : "to_date(" + StringUtils.toSql(data.get("UPLOAD_TIME")).replace(".0", "") + ", 'YYYY-MM-DD HH24:MI:SS')") + "," +
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}