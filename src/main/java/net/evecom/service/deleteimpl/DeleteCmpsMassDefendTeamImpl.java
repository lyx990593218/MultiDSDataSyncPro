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
 * <P><B> Description:  群防群治队伍</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsMassDefendTeamImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsMassDefendTeamImpl.class);

    /**
     *  省网数据表名
     */
    private final static String TABLENAME = "S_PTZH_GRID_MASS_DEFEND_TEAM";

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
        return "select * from "+TABLENAME+"_V WHERE 1=1 AND CERT_NUM = ?";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("CERT_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除群防群治队伍";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
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
        String insertSql = "insert into PINGTAN." + TABLENAME;

        insertSql += "(" +

                "mass_org_name,\n" +
                "full_name,\n" +
                "sex_code,\n" +
                "nation_code,\n" +
                "politics_code,\n" +
                "cert_code,\n" +
                "cert_num,\n" +
                "birthday,\n" +
                "post_name,\n" +
                "major_skill_code,\n" +
                "education_code,\n" +
                "telephone,\n" +
                "fixed_telephone,\n" +
                "add_time,\n" +
                "update_time,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("MASS_ORG_NAME")) + "," +
                StringUtils.toSql(data.get("FULL_NAME")) + "," +
                StringUtils.toSql(data.get("SEX_CODE")) + "," +
                StringUtils.toSql(data.get("NATION_CODE")) + "," +
                StringUtils.toSql(data.get("POLITICS_CODE")) + "," +
                StringUtils.toSql(data.get("CERT_CODE")) + "," +
                StringUtils.toSql(data.get("CERT_NUM")) + "," +
                convert2SqlDate(data.get("BIRTHDAY")) + "," +
                StringUtils.toSql(data.get("POST_NAME")) + "," +
                StringUtils.toSql(data.get("MAJOR_SKILL_CODE")) + "," +
                StringUtils.toSql(data.get("EDUCATION_CODE")) + "," +
                StringUtils.toSql(data.get("TELEPHONE")) + "," +
                StringUtils.toSql(data.get("FIXED_TELEPHONE")) + "," +

                convert2SqlDate(data.get("ADD_TIME")) + ","+
                convert2SqlDate(data.get("UPDATE_TIME")) + ","+
//                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                " sysdate,"+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
