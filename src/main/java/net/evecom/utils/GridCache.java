/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 获取网格缓存
 * 
 * @author Fandy Liu
 * @created 2018年11月14日 上午10:15:02
 */
public class GridCache {
    /**
     * 
     */
    private static final Logger LOG = LoggerFactory.getLogger(GridCache.class);
    /**
     * 区域编码code
     */
    private Map<String, String> gridCodeMap;
    /**
     * 网格id
     */
    private Map<String, String> gridIdMap;

    public Map<String, String> getGridCodeMap() {
        return gridCodeMap;
    }

    public void setGridCodeMap(Map<String, String> gridCodeMap) {
        this.gridCodeMap = gridCodeMap;
    }

    public Map<String, String> getGridIdMap() {
        return gridIdMap;
    }

    public void setGridIdMap(Map<String, String> gridIdMap) {
        this.gridIdMap = gridIdMap;
    }

    /**
     * 
     * 描述
     * 
     * @author Fandy Liu
     * @created 2018年12月7日 下午7:27:04
     * @param ourGridCode
     * @return
     */
    public String getTargetGridId(String ourGridCode) {
        try {
            if (ourGridCode != null && ourGridCode.length() > 9 /*&& ourGridCode.length() <= 12*/) {
                ourGridCode = ourGridCode.substring(0, 9);
            }
            String theirGridId = gridIdMap.get(ourGridCode);
            return theirGridId == null ? "199940" : theirGridId;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "199940";

    }

    /**
     * 
     * 描述
     * 
     * @author Fandy Liu
     * @created 2018年12月7日 下午7:27:04
     * @param ourGridCode
     * @return
     */
    public String getTargetRegionCode(String ourGridCode) {
        try {
            if (ourGridCode != null && ourGridCode.length() > 9/* && ourGridCode.length() <= 12*/) 
                ourGridCode = ourGridCode.substring(0, 9);
            
            String gridCode = gridCodeMap.get(ourGridCode);
            return gridCode == null ? "350402" : gridCode;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "350402";

    }

}
