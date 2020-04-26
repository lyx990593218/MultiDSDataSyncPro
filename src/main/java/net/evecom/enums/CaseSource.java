/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.enums;
/**
 * @description 事件来源
 *
 * @author Pellym Huang
 * @created 2018/12/6 22:44
 */
public enum CaseSource {
    /**
     *
     */
    D01("07", "1"),
    /**
     *
     */
    D02("01", "2"),
    /**
     *
     */
    D03("11", "3");
    /**
     *  我们库里的字典值
     */
    private String myCode;
    /**
     *  对接平台的字典值
     */
    private String targetCode;

    // 构造方法
    private CaseSource(String targetCode,String myCode) {
        this.myCode = myCode;
        this.targetCode = targetCode;
    }
    
    /**
     *  普通方法
     * @author Fandy Liu
     * @created 2018年11月19日 上午11:35:58
     * @param code
     * @return
     */
    public static String getTarget(String myCode) {
        for (CaseSource c : CaseSource.values()) {
            if (c.getMyCode().equals(myCode)) {
                return c.targetCode;
            }
        }
        return "";
    }

    public String getMyCode() {
        return myCode;
    }

    public void setMyCode(String myCode) {
        this.myCode = myCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
   
}
