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

    /**
     *
     * @author Fandy Liu
     * @throws Exception 
     * @created 2018年11月14日 上午11:08:53
     */
    @Scheduled(cron = "${cron.cmps.aidsPop}")
    public void syncAidsPopByCron() throws Exception {
        LOG.debug("艾滋病人定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsAidsPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("艾滋病人定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.comManageCenter}")
    public void syncComManageCenterByCron() throws Exception {
        LOG.debug("综治中心定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsComManageCnter);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("综治中心定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.correctPerson}")
    public void syncCorrectPersonByCron() throws Exception {
        LOG.debug("社区矫正人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsCorrectPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("社区矫正人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.drugPop}")
    public void syncDrugPopByCron() throws Exception {
        LOG.debug("吸毒人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsDrugPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("吸毒人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.emancipistPop}")
    public void syncEmancipistPopByCron() throws Exception {
        LOG.debug("刑满释放人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsEmancipistPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("刑满释放人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.eventDisLitigantInfo}")
    public void syncEventDisLitigantInfoByCron() throws Exception {
        LOG.debug("矛盾纠纷排查化解主要当事人信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsEventDisLitigantInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("矛盾纠纷排查化解主要当事人信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.eventDisputeResolve}")
    public void syncEventDisputeResolveByCron() throws Exception {
        LOG.debug("矛盾纠纷排查化解定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsEventDisputeResolve);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("矛盾纠纷排查化解定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.cmps.gridBasicInfo}")
    public void syncGridBasicInfoByCron() throws Exception {
        LOG.debug("网格基本信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridBasicInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格基本信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.gridPersonInfo}")
    public void syncGridPersonInfoByCron() throws Exception {
        LOG.debug("网格人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridPersonInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.gridPopJoin}")
    public void syncGridPopJoinByCron() throws Exception {
        LOG.debug("网格单元与网格人员关联关系定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridPopJoin);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格单元与网格人员关联关系定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.gridUnitInfo}")
    public void syncGridUnitInfoByCron() throws Exception {
        LOG.debug("网格单元定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridUnitInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格单元定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.keyYouth}")
    public void syncKeyYouthByCron() throws Exception {
        LOG.debug("重点青少年定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsKeyYouth);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("重点青少年定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.cmps.massDefendOrg}")
    public void syncMassDefendOrgByCron() throws Exception {
        LOG.debug("群防群治组织定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsMassDefendOrg);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("群防群治组织定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.massDefendTeam}")
    public void syncMassDefendTeamByCron() throws Exception {
        LOG.debug("群防群治队伍定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsMassDefendTeam);
        if (iDataSync != null){
            iDataSync.syncDatas();
        } else {
            LOG.debug("未调用到类");
        }
        LOG.debug("群防群治队伍定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.mentalPatient}")
    public void syncMentalPatientByCron() throws Exception {
        LOG.debug("肇事肇祸等严重精神障碍患者定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsMentalPatient);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("肇事肇祸等严重精神障碍患者定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }








    @Scheduled(cron = "${cron.delcmps.pop}")
    public void syncDelPopByCron() throws Exception {
        LOG.debug("删除实有人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
        /*for(DataType dataType:DataType.values()){
            // 获取到实现的接口
            IDataSync iDataSync = dataImportFactory.get(dataType);
            if (iDataSync != null){
                iDataSync.syncDatas();
            }
        }*/

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除实有人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }


    @Scheduled(cron = "${cron.delcmps.localpop}")
    public void syncDelLocalPopByCron() throws Exception {
        LOG.debug("删除户籍人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsLocalPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除户籍人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.flowpop}")
    public void syncDelFlowPopByCron() throws Exception {
        LOG.debug("删除流动人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsFlowPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除流动人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    /**
     *
     * @author Fandy Liu
     * @throws Exception
     * @created 2018年11月14日 上午11:08:53
     */
    @Scheduled(cron = "${cron.delcmps.aidsPop}")
    public void syncDelAidsPopByCron() throws Exception {
        LOG.debug("删除艾滋病人定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsAidsPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除艾滋病人定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.comManageCenter}")
    public void syncDelComManageCenterByCron() throws Exception {
        LOG.debug("删除综治中心定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsComManageCnter);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除综治中心定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.correctPerson}")
    public void syncDelCorrectPersonByCron() throws Exception {
        LOG.debug("删除社区矫正人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsCorrectPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除社区矫正人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.drugPop}")
    public void syncDelDrugPopByCron() throws Exception {
        LOG.debug("删除吸毒人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsDrugPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除吸毒人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.emancipistPop}")
    public void syncDelEmancipistPopByCron() throws Exception {
        LOG.debug("删除刑满释放人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsEmancipistPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除刑满释放人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.eventDisLitigantInfo}")
    public void syncDelEventDisLitigantInfoByCron() throws Exception {
        LOG.debug("删除矛盾纠纷排查化解主要当事人信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsEventDisLitigantInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除矛盾纠纷排查化解主要当事人信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.eventDisputeResolve}")
    public void syncDelEventDisputeResolveByCron() throws Exception {
        LOG.debug("删除矛盾纠纷排查化解定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsEventDisputeResolve);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除矛盾纠纷排查化解定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.delcmps.gridBasicInfo}")
    public void syncDelGridBasicInfoByCron() throws Exception {
        LOG.debug("删除网格基本信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridBasicInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格基本信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.gridPersonInfo}")
    public void syncDelGridPersonInfoByCron() throws Exception {
        LOG.debug("删除网格人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridPersonInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.gridPopJoin}")
    public void syncDelGridPopJoinByCron() throws Exception {
        LOG.debug("删除网格单元与网格人员关联关系定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridPopJoin);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格单元与网格人员关联关系定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.gridUnitInfo}")
    public void syncDelGridUnitInfoByCron() throws Exception {
        LOG.debug("删除网格单元定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridUnitInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格单元定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.keyYouth}")
    public void syncDelKeyYouthByCron() throws Exception {
        LOG.debug("删除重点青少年定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsKeyYouth);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除重点青少年定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.delcmps.massDefendOrg}")
    public void syncDelMassDefendOrgByCron() throws Exception {
        LOG.debug("删除群防群治组织定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsMassDefendOrg);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除群防群治组织定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.massDefendTeam}")
    public void syncDelMassDefendTeamByCron() throws Exception {
        LOG.debug("删除群防群治队伍定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsMassDefendTeam);
        if (iDataSync != null){
            iDataSync.syncDatas();
        } else {
            LOG.debug("删除未调用到类");
        }
        LOG.debug("删除群防群治队伍定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.mentalPatient}")
    public void syncDelMentalPatientByCron() throws Exception {
        LOG.debug("删除肇事肇祸等严重精神障碍患者定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsMentalPatient);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除肇事肇祸等严重精神障碍患者定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

}
