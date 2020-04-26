/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.utils.date;

/**
 * 描述
 * 
 * @author Fandy Liu
 * @created 2015年6月1日 下午6:17:50
 */
public enum DateStyle {
    /****/
    YYYY_MM("yyyy-MM", false),
    /****/
    YYYYMM("yyyyMM", false),
    /****/
    YYYYMMDD("yyyyMMdd", false),
    /****/
    YYYYMD("yyyy.M.d", false),
    /****/
    HHmmssS("HHmmssS", false),
    /****/
    YYYYMMDDHHMMSS("yyyyMMHHmmss", false),
    /****/
    YYYY_MM_DD("yyyy-MM-dd", false),
    /****/
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", false),
    /****/
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", false),
    /****/
    YYYY_MM_EN("yyyy/MM", false),
    /****/
    YYYY_MM_DD_EN("yyyy/MM/dd", false),
    /****/
    YYYY_M_DD_EN("yyyy/M/dd", false),
    /****/
    YYYY_M_D_EN("yyyy/M/d", false),
    /****/
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm", false),
    /****/
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss", false),
    /****/
    YYYY_MM_CN("yyyy年MM月", false),
    /****/
    YYYY_MM_DD_CN("yyyy年MM月dd日", false),
    /****/
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm", false),
    /****/
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss", false),
    /****/
    HH_MM("HH:mm", true),
    /****/
    HH_MM_SS("HH:mm:ss", true),
    /****/
    MM_DD("MM-dd", true),
    /****/
    MM_DD_HH_MM("MM-dd HH:mm", true),
    /****/
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", true),
    /****/
    MM_DD_EN("MM/dd", true),
    /****/
    MM_DD_HH_MM_EN("MM/dd HH:mm", true),
    /****/
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss", true),
    /****/
    MM_DD_CN("MM月dd日", true),
    /****/
    MM_DD_HH_MM_CN("MM月dd日 HH:mm", true),
    /****/
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss", true);
    /****/
    private String value;
    /****/
    private boolean isShowOnly;

    /**
     * 描述
     * 
     * @author Fandy Liu
     * @created 2015年6月1日 下午6:19:09
     * @param value
     * @param isShowOnly
     */
    DateStyle(String value, boolean isShowOnly) {
        this.value = value;
        this.isShowOnly = isShowOnly;
    }

    /**
     * 
     * 描述
     * @created 2015-12-24 下午5:15:52
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * 描述
     * @created 2015-12-24 下午5:16:06
     * @return
     */
    public boolean isShowOnly() {
        return isShowOnly;
    }
}
