/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.enums;

/**
 * @author Pellym Huang
 * @description 事件类型
 * @created 2018/12/6 22:44
 */
public enum CaseType {


    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D01("02", "212"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D02("03", "209"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D03("04", "201"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D04("06", "206"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D05("07", "203"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D06("08", "205"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D07("09", "208"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D08("99", "202"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D09("99", "204"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D010("99", "207"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D011("99", "210"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D012("99", "211"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D013("99", "213"),
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    D014("99", "214");


    /**
     * 我们库里的字典值
     */
    private String myCode;
    /**
     * 对接平台的字典值
     */
    private String targetCode;

    // 构造方法
    private CaseType(String targetCode, String myCode) {
        this.myCode = myCode;
        this.targetCode = targetCode;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     * @author Fandy Liu
     * @created 2018年11月19日 上午11:35:58
     */
    public static String getTarget(String myCode) {
        for (CaseType c : CaseType.values()) {
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
