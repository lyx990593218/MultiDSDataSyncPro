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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <P><B> Description:  网格人员</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsGridPersonInfoImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsFlowPopImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_GRID_PERSON_INFO";

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "SELECT * FROM "+TABLENAME+"_V WHERE 1=1 AND PERSON_NUM = ?";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("PERSON_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除网格人员";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "PERSON_NUM";
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

                "person_num,\n" +
                "cert_code,\n" +
                "cert_num,\n" +
                "grid_person_name,\n" +
                "is_grid_captain,\n" +
                "sex_code,\n" +
                "nation_code,\n" +
                "politics_code,\n" +
                "birthday,\n" +
                "education_code,\n" +
                "contact,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("PERSON_NUM")) + "," +
                StringUtils.toSql(data.get("CERT_CODE")) + "," +
                StringUtils.toSql(data.get("CERT_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_PERSON_NAME")) + "," +
                StringUtils.toSql(data.get("IS_GRID_CAPTAIN")) + "," +
                StringUtils.toSql(data.get("SEX_CODE")) + "," +
                StringUtils.toSql(data.get("NATION_CODE")) + "," +
                StringUtils.toSql(data.get("POLITICS_CODE")) + "," +
                convert2SqlDate(data.get("BIRTHDAY")) + "," +
                StringUtils.toSql(data.get("EDUCATION_CODE")) + "," +
                StringUtils.toSql(data.get("CONTACT")) + "," +

                //                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                " sysdate,"+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
