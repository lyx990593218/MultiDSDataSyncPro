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
 * <P><B> Description:  矛盾纠纷排查化解</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsEventDisputeResolveImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsEventDisputeResolveImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_EVENT_DISPUTE_RESOLVE";

    @Override
    public String  syncDatas() throws Exception {
        return super.run();
    }
    @Override
    public String getFindSql() {
        return StringUtils.format(findSqlTpl, getOurPkFieldName(), TABLENAME);
//        return "select * from S_PTZH_EVENT_DISPUTE_RESOLVE_V WHERE 1=1";
    }
    @Override
    public Object[] getFindSqlParams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFindTargetSql() {
        return "select * from S_PTZH_EVENT_DISPUTE_RESOLVE_V WHERE 1=1 AND EVENT_NUM = ?";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("EVENT_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除矛盾纠纷排查化解";
    }
    @Override
    public String getOurPkFieldName() {
        // TODO Auto-generated method stub
        return "EVENT_NUM";
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

                "event_num,\n" +
                "event_name,\n" +
                "happen_date,\n" +
                "happen_place_name,\n" +
                "event_size_code,\n" +
                "dispute_event_type,\n" +
                "involved_amount,\n" +
                "event_describe,\n" +
                "involved_unit,\n" +
                "resolve_limit_date,\n" +
                "resolve_way_code,\n" +
                "resolve_org_name,\n" +
                "resolve_person_name,\n" +
                "resolve_person_contact,\n" +
                "is_success,\n" +
                "resolve_situation,\n" +
                "exam_date,\n" +
                "exam_advise,\n" +
                "longitude,\n" +
                "latitude,\n" +
                "competent_dept_code,\n" +
                "competent_dept_name,\n" +
                "event_deal_situation,\n" +
                "grid_num,\n" +
                "grid_org_name,\n" +
                "add_time,\n" +
                "update_time,\n" +
                "upload_time,\n" +
                "upload_status," +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("EVENT_NUM")) + "," +
                StringUtils.toSql(data.get("EVENT_NAME")) + "," +
                convert2SqlDate(data.get("HAPPEN_DATE")) + "," +
                StringUtils.toSql(data.get("HAPPEN_PLACE_NAME")) + "," +
                StringUtils.toSql(data.get("EVENT_SIZE_CODE")) + "," +
                StringUtils.toSql(data.get("DISPUTE_EVENT_TYPE")) + "," +
                StringUtils.toSql(data.get("INVOLVED_AMOUNT")) + "," +
                StringUtils.toSql(data.get("EVENT_DESCRIBE")) + "," +
                StringUtils.toSql(data.get("INVOLVED_UNIT")) + "," +
                convert2SqlDate(data.get("RESOLVE_LIMIT_DATE")) + "," +
                StringUtils.toSql(data.get("RESOLVE_WAY_CODE")) + "," +
                StringUtils.toSql(data.get("RESOLVE_ORG_NAME")) + "," +
                StringUtils.toSql(data.get("RESOLVE_PERSON_NAME")) + "," +
                StringUtils.toSql(data.get("RESOLVE_PERSON_CONTACT")) + "," +
                StringUtils.toSql(data.get("IS_SUCCESS")) + "," +
                StringUtils.toSql(data.get("RESOLVE_SITUATION")) + "," +
                convert2SqlDate(data.get("EXAM_DATE")) + "," +
                StringUtils.toSql(data.get("EXAM_ADVISE")) + "," +
                StringUtils.toSql(data.get("LONGITUDE")) + "," +
                StringUtils.toSql(data.get("LATITUDE")) + "," +
                StringUtils.toSql(data.get("COMPETENT_DEPT_CODE")) + "," +
                StringUtils.toSql(data.get("COMPETENT_DEPT_NAME")) + "," +
                StringUtils.toSql(data.get("EVENT_DEAL_SITUATION")) + "," +
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
