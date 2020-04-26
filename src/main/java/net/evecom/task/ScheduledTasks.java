/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.evecom.service.DataSyncFactory;
import net.evecom.service.DataType;
import net.evecom.service.IDataSync;
import net.evecom.utils.date.DateStyle;
import net.evecom.utils.date.DateUtil;
/**
 * 数据同步定时类
 * 
 * @author Fandy Liu
 * @created 2018年11月14日 上午11:08:53
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {
    /**
     * The constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
    /**
     * 
     */
    @Autowired
    DataSyncFactory dataImportFactory;

    /**
     *
     * @author Fandy Liu
     * @throws Exception 
     * @created 2018年11月14日 上午11:08:53
     */
    @Scheduled(cron = "${cron.cmps.pop}")
    public void syncPopByCron() throws Exception {
        LOG.debug("实有人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
        /*for(DataType dataType:DataType.values()){
            // 获取到实现的接口
            IDataSync iDataSync = dataImportFactory.get(dataType);
            if (iDataSync != null){
                iDataSync.syncDatas();
            }
        }*/

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("实有人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }


    @Scheduled(cron = "${cron.cmps.localpop}")
    public void syncLocalPopByCron() throws Exception {
        LOG.debug("户籍人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsLocalPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("户籍人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.flowpop}")
    public void syncFlowPopByCron() throws Exception {
        LOG.debug("流动人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsFlowPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("流动人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.drugpop}")
    public void syncDrugPopByCron() throws Exception {
        LOG.debug("吸毒人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsDrugPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("吸毒人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }
    
}
