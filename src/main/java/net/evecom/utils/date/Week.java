/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.utils.date;

/**
 * 描述
 * @author Fandy Liu
 * @created 2015年6月1日 下午6:17:20
 */
public enum Week {
    /****/
    MONDAY("星期一", "Monday", "Mon.", 1),
    /****/
    TUESDAY("星期二", "Tuesday", "Tues.", 2),
    /****/
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
    /****/
    THURSDAY("星期四", "Thursday", "Thur.", 4),
    /****/
    FRIDAY("星期五", "Friday", "Fri.", 5),
    /****/
    SATURDAY("星期六", "Saturday", "Sat.", 6),
    /****/
    SUNDAY("星期日", "Sunday", "Sun.", 7);
    /****/
    String name_cn;
    /****/
    String name_en;
    /****/
    String name_enShort;
    /****/
    int number;

    /**
     * 构造函数
     * 
     * @author Fandy Liu
     * @created 2015年6月1日 下午6:16:55
     * @param name_cn
     * @param name_en
     * @param name_enShort
     * @param number
     */
    Week(String name_cn, String name_en, String name_enShort, int number) {
        this.name_cn = name_cn;
        this.name_en = name_en;
        this.name_enShort = name_enShort;
        this.number = number;
    }

    public String getChineseName() {
        return name_cn;
    }

    public String getName() {
        return name_en;
    }

    public String getShortName() {
        return name_enShort;
    }

    public int getNumber() {
        return number;
    }
}
