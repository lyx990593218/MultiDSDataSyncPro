/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 *
 * @author Pellym Huang
 * @created 2018/12/8 14:07
 */
public class MapUtil {
    /**
     * map
     */
    Map<String,String> map;
    /**
     * data数据
     */
    Map<String,Object> data;
    /**
     * gridCache
     */
    GridCache gridCache;
    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/8 14:07
     */
    public MapUtil(Map<String,Object> data,GridCache gridCache) {
        this.map = new HashMap<>();
        this.data = data;
        this.gridCache = gridCache;
    }
    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/8 14:07
     */
    public void addParam(String keyStr, Class clazz) throws Exception {
        if (this.data.get(keyStr) != null && this.data.get(keyStr).toString() != ""
                && this.data.get(keyStr).toString() != "null") {
            if (clazz == GridCache.class){
                if (keyStr == "gridId") {
                    this.map.put(keyStr,this.gridCache.getTargetGridId(this.data.get(keyStr.toUpperCase()).toString()));
                } else {
                    this.map.put(keyStr,this.gridCache.getTargetRegionCode(this.data.get(keyStr.toUpperCase()).toString()));
                }
            } else if (clazz != null) {
                //clazz.getMethod()
                Method getTarget = clazz.getMethod("getTarget",String.class);
                String theirCode = getTarget
                        .invoke(clazz,this.data.get(keyStr.toUpperCase()).toString()).toString();
                this.map.put(keyStr,theirCode);
            } else {
                this.map.put(keyStr,StringUtils.objToString(data.get(keyStr.toUpperCase())));
            }
        }
    }
    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/8 14:08
     */
    public Map<String, String> getMap() {
        return map;
    }
    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/8 14:08
     */
    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/8 14:08
     */
    public Map<String, Object> getData() {
        return data;
    }
    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/8 14:08
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}

