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
 * <P><B> Description:  网格单元</B></P>
 * Revision Trail: (Date/Author/Description)
 * a Joe Zhou CREAT
 *
 * @author Joe Zhou
 * @version 1.0
 */
@Service
public class DeleteCmpsGridUnitInfoImpl extends DeleteBaseServiceImpl implements IDataSync {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCmpsFlowPopImpl.class);

    /**
     *  省网数据表名
     */
    private static String TABLENAME = "S_PTZH_GRID_UNIT_INFO";

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
        return "SELECT * FROM "+TABLENAME+"_V WHERE 1=1 AND GRID_NUM = ?";
//        return "SELECT T.* FROM (SELECT TT.*, ROW_NUMBER() OVER (PARTITION BY TT.GRID_NUM ORDER BY TT.UPLOAD_TIME DESC) RN FROM PINGTAN."  + TABLENAME + " TT WHERE GRID_NUM = ?) T WHERE 1 = 1 AND T.RN = 1";
    }

    @Override
    public Object[] getFindTargetSqlParams(Map<String, Object> data) {
        return new Object[]{data.get("GRID_NUM")};
    }

    @Override
    public String getModelName() {
        return "删除网格单元";
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
        String insertSql = "insert into PINGTAN." + TABLENAME;

        insertSql += "(" +

                "grid_name,\n" +
                "grid_num,\n" +
                "grid_captain_name,\n" +
                "grid_person_amount,\n" +
                "grid_household_amount,\n" +
                "grid_area,\n" +
                "map_vector_data_file,\n" +
                "divide_date,\n" +
                "draw_unit,\n" +
                "upload_time,\n" +
                "upload_status" +

                ")\n";

        insertSql += "VALUES";

        insertSql += "(" +
                StringUtils.toSql(data.get("GRID_NAME")) + "," +
                StringUtils.toSql(data.get("GRID_NUM")) + "," +
                StringUtils.toSql(data.get("GRID_CAPTAIN_NAME")) + "," +
                StringUtils.toSql(data.get("GRID_PERSON_AMOUNT")) + "," +
                StringUtils.toSql(data.get("GRID_HOUSEHOLD_AMOUNT")) + "," +
                StringUtils.toSql(data.get("GRID_AREA")) + "," +
                StringUtils.toSql(data.get("MAP_VECTOR_DATA_FILE")) + "," +
                convert2SqlDate(data.get("DIVIDE_DATE")) + "," +
                StringUtils.toSql(data.get("DRAW_UNIT")) + "," +

                //                convert2SqlDate(data.get("UPLOAD_TIME")) + ","+
                " sysdate,"+
                StringUtils.toSql(i) +
                ")";

        jdbcTemplate2.execute(insertSql);
    }
}
