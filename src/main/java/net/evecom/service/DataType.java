/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.service;

/**
 * 数据类型 对应要对接的数据同步实现类
 * @author Fandy Liu
 * @created 2018年11月14日 上午10:07:09
 */
public enum DataType {

    /**
     * 实有人员
     */
    cmpsPop("cmpsPopImpl"),

    /**
     * 户籍人员
     */
    cmpsLocalPop("cmpsLocalPopImpl"),

    /**
     * 流动人员
     */
    cmpsFlowPop("cmpsFlowPopImpl"),

    /**
     * 刑满释放人员
     */
    cmpsEmancipistPop("cmpsEmancipistPopImpl"),

    /**
     * 吸毒人员
     */
    cmpsDrugPop("cmpsDrugPopImpl"),

    /**
     * 艾滋病危险人员
     */
    cmpsAdisPop("cmpsAdisPopImpl"),

    /**
     * 重点青少年
     */
    cmpsKeyYouthPop("cmpsKeyYouthPopImpl"),

    /**
     * 网格基本信息
     */
    cmpsGridBasicInfo("cmpsGridBasicInfoImpl"),

    /**
     * 社区矫正
     */
    cmpsCorrectPop("cmpsCorrectPopImpl");
    
    
    /**
     * 实例名称
     */
    private String instanceName;
    
    DataType(String instanceName){
        this.instanceName=instanceName;
    }
    
    public String getInstanceName() {
        return instanceName;
    }
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    /**
     * 获取接口实现类
     * @author Fandy Liu
     * @created 2018年12月8日 下午2:44:02
     * @param name
     * @return
     */
    public static DataType getInterfaceName(String name){
        for (DataType type : DataType.values()) {
            if (type.getInstanceName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
