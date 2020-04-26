/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import com.github.crab2died.ExcelUtils;

import net.evecom.utils.GridCache;

/**
 * 
 * 描述
 * 
 * @author Fandy Liu
 * @created 2018年12月12日 下午11:28:12
 */
//@Configuration
public class GridCacheConfig {
    /**
     * jdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;
    /**
     * gridPath
     */
    @Value("${gridPath}")
    private String gridPath;

    /**
     * 
     * 描述
     * 
     * @author Fandy Liu
     * @created 2018年12月12日 下午11:28:16
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    @Bean(name = "gridCache")
    public GridCache gridCacheBean() throws InvalidFormatException, IOException {
        GridCache gridCache = new GridCache();
        Map<String, String> gridCodeMap = new TreeMap<String, String>();
        Map<String, String> gridIdMap = new TreeMap<String, String>();
        String sql = "select * from CMPS_LOC_GRIDS";
        List<Map<String, Object>> myGrids = jdbcTemplate.queryForList(sql);
        List<List<String>> targetGrids = ExcelUtils.getInstance().readExcel2List(gridPath, 1);
        for (Map<String, Object> grid : myGrids) {
            String gridName = (String) grid.get("NAME");
            String gridCode = (String) grid.get("CODE");
            for (List<String> targetGrid : targetGrids) {
                String targetGridName = targetGrid.get(3);
                String targetGridCode = targetGrid.get(1).replace("'", "");
                String targetGridId = targetGrid.get(0);
                if (gridName.indexOf(targetGridName) > -1) {
                    gridCodeMap.put(gridCode, targetGridCode);
                    gridIdMap.put(gridCode, targetGridId);
                    break;
                }
            }
        }
        gridCache.setGridCodeMap(gridCodeMap);
        gridCache.setGridIdMap(gridIdMap);
        // System.out.println(gridCache.getGridCodeMap());
        return gridCache;
    }

}
