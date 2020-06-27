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

    @Value("${taskSwitch.cmps.pop}")
    private String popTaskSwitch;

    @Value("${taskSwitch.cmps.localpop}")
    private String localPopTaskSwitch;

    @Value("${taskSwitch.cmps.flowpop}")
    private String flowPopTaskSwitch;

    @Value("${taskSwitch.cmps.aidsPop}")
    private String aidsPopTaskSwitch;

    @Value("${taskSwitch.cmps.comManageCenter}")
    private String comManageCenterTaskSwitch;

    @Value("${taskSwitch.cmps.correctPerson}")
    private String correctPersonTaskSwitch;

    @Value("${taskSwitch.cmps.drugPop}")
    private String drugPopTaskSwitch;

    @Value("${taskSwitch.cmps.emancipistPop}")
    private String emancipistPopTaskSwitch;

    @Value("${taskSwitch.cmps.eventDisLitigantInfo}")
    private String eventDisLitigantInfoTaskSwitch;

    @Value("${taskSwitch.cmps.eventDisputeResolve}")
    private String eventDisputeResolveTaskSwitch;

    @Value("${taskSwitch.cmps.gridBasicInfo}")
    private String gridBasicInfoTaskSwitch;

    @Value("${taskSwitch.cmps.gridPersonInfo}")
    private String gridPersonInfoTaskSwitch;

    @Value("${taskSwitch.cmps.gridPopJoin}")
    private String gridPopJoinTaskSwitch;

    @Value("${taskSwitch.cmps.gridUnitInfo}")
    private String gridUnitInfoTaskSwitch;

    @Value("${taskSwitch.cmps.keyYouth}")
    private String keyYouthTaskSwitch;

    @Value("${taskSwitch.cmps.massDefendOrg}")
    private String massDefendOrgTaskSwitch;

    @Value("${taskSwitch.cmps.massDefendTeam}")
    private String massDefendTeamTaskSwitch;

    @Value("${taskSwitch.cmps.mentalPatient}")
    private String mentalPatientTaskSwitch;



    @Value("${taskSwitch.delcmps.pop}")
    private String popDelTaskSwitch;

    @Value("${taskSwitch.delcmps.localpop}")
    private String localPopDelTaskSwitch;

    @Value("${taskSwitch.delcmps.flowpop}")
    private String flowPopDelTaskSwitch;

    @Value("${taskSwitch.delcmps.aidsPop}")
    private String aidsPopDelTaskSwitch;

    @Value("${taskSwitch.delcmps.comManageCenter}")
    private String comManageCenterDelTaskSwitch;

    @Value("${taskSwitch.delcmps.correctPerson}")
    private String correctPersonDelTaskSwitch;

    @Value("${taskSwitch.delcmps.drugPop}")
    private String drugPopDelTaskSwitch;

    @Value("${taskSwitch.delcmps.emancipistPop}")
    private String emancipistPopDelTaskSwitch;

    @Value("${taskSwitch.delcmps.eventDisLitigantInfo}")
    private String eventDisLitigantInfoDelTaskSwitch;

    @Value("${taskSwitch.delcmps.eventDisputeResolve}")
    private String eventDisputeResolveDelTaskSwitch;

    @Value("${taskSwitch.delcmps.gridBasicInfo}")
    private String gridBasicInfoDelTaskSwitch;

    @Value("${taskSwitch.delcmps.gridPersonInfo}")
    private String gridPersonInfoDelTaskSwitch;

    @Value("${taskSwitch.delcmps.gridPopJoin}")
    private String gridPopJoinDelTaskSwitch;

    @Value("${taskSwitch.delcmps.gridUnitInfo}")
    private String gridUnitInfoDelTaskSwitch;

    @Value("${taskSwitch.delcmps.keyYouth}")
    private String keyYouthDelTaskSwitch;

    @Value("${taskSwitch.delcmps.massDefendOrg}")
    private String massDefendOrgDelTaskSwitch;

    @Value("${taskSwitch.delcmps.massDefendTeam}")
    private String massDefendTeamDelTaskSwitch;

    @Value("${taskSwitch.delcmps.mentalPatient}")
    private String mentalPatientDelTaskSwitch;

    @Scheduled(cron = "${cron.cmps.pop}")
    public void syncPopByCron() throws Exception {
        if ("close".equals(popTaskSwitch)){
            return;
        }
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
        if ("close".equals(localPopTaskSwitch)){
            return;
        }

        LOG.debug("户籍人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsLocalPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("户籍人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.flowpop}")
    public void syncFlowPopByCron() throws Exception {
        if ("close".equals(flowPopTaskSwitch)){
            return;
        }

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
        if ("close".equals(aidsPopTaskSwitch)){
            return;
        }

        LOG.debug("艾滋病人定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsAidsPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("艾滋病人定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.comManageCenter}")
    public void syncComManageCenterByCron() throws Exception {
        if ("close".equals(comManageCenterTaskSwitch)){
            return;
        }

        LOG.debug("综治中心定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsComManageCnter);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("综治中心定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.correctPerson}")
    public void syncCorrectPersonByCron() throws Exception {
        if ("close".equals(correctPersonTaskSwitch)){
            return;
        }

        LOG.debug("社区矫正人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsCorrectPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("社区矫正人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.drugPop}")
    public void syncDrugPopByCron() throws Exception {
        if ("close".equals(drugPopTaskSwitch)){
            return;
        }

        LOG.debug("吸毒人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsDrugPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("吸毒人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.emancipistPop}")
    public void syncEmancipistPopByCron() throws Exception {
        if ("close".equals(emancipistPopTaskSwitch)){
            return;
        }

        LOG.debug("刑满释放人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsEmancipistPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("刑满释放人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.eventDisLitigantInfo}")
    public void syncEventDisLitigantInfoByCron() throws Exception {
        if ("close".equals(eventDisLitigantInfoTaskSwitch)){
            return;
        }

        LOG.debug("矛盾纠纷排查化解主要当事人信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsEventDisLitigantInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("矛盾纠纷排查化解主要当事人信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.eventDisputeResolve}")
    public void syncEventDisputeResolveByCron() throws Exception {
        if ("close".equals(eventDisputeResolveTaskSwitch)){
            return;
        }

        LOG.debug("矛盾纠纷排查化解定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsEventDisputeResolve);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("矛盾纠纷排查化解定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.cmps.gridBasicInfo}")
    public void syncGridBasicInfoByCron() throws Exception {
        if ("close".equals(gridBasicInfoTaskSwitch)){
            return;
        }

        LOG.debug("网格基本信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridBasicInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格基本信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.gridPersonInfo}")
    public void syncGridPersonInfoByCron() throws Exception {
        if ("close".equals(gridPersonInfoTaskSwitch)){
            return;
        }

        LOG.debug("网格人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridPersonInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.gridPopJoin}")
    public void syncGridPopJoinByCron() throws Exception {
        if ("close".equals(gridPopJoinTaskSwitch)){
            return;
        }

        LOG.debug("网格单元与网格人员关联关系定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridPopJoin);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格单元与网格人员关联关系定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.gridUnitInfo}")
    public void syncGridUnitInfoByCron() throws Exception {
        if ("close".equals(gridUnitInfoTaskSwitch)){
            return;
        }

        LOG.debug("网格单元定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsGridUnitInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("网格单元定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.keyYouth}")
    public void syncKeyYouthByCron() throws Exception {
        if ("close".equals(keyYouthTaskSwitch)){
            return;
        }

        LOG.debug("重点青少年定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsKeyYouth);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("重点青少年定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.cmps.massDefendOrg}")
    public void syncMassDefendOrgByCron() throws Exception {
        if ("close".equals(massDefendOrgTaskSwitch)){
            return;
        }

        LOG.debug("群防群治组织定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsMassDefendOrg);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("群防群治组织定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.cmps.massDefendTeam}")
    public void syncMassDefendTeamByCron() throws Exception {
        if ("close".equals(massDefendTeamTaskSwitch)){
            return;
        }

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
        if ("close".equals(mentalPatientTaskSwitch)){
            return;
        }

        LOG.debug("肇事肇祸等严重精神障碍患者定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.cmpsMentalPatient);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("肇事肇祸等严重精神障碍患者定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }








    @Scheduled(cron = "${cron.delcmps.pop}")
    public void syncDelPopByCron() throws Exception {
        if ("close".equals(popDelTaskSwitch)){
            return;
        }

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
        if ("close".equals(localPopDelTaskSwitch)){
            return;
        }

        LOG.debug("删除户籍人口定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsLocalPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除户籍人口定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.flowpop}")
    public void syncDelFlowPopByCron() throws Exception {
        if ("close".equals(flowPopDelTaskSwitch)){
            return;
        }

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
        if ("close".equals(aidsPopDelTaskSwitch)){
            return;
        }

        LOG.debug("删除艾滋病人定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsAidsPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除艾滋病人定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.comManageCenter}")
    public void syncDelComManageCenterByCron() throws Exception {
        if ("close".equals(comManageCenterDelTaskSwitch)){
            return;
        }

        LOG.debug("删除综治中心定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsComManageCnter);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除综治中心定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.correctPerson}")
    public void syncDelCorrectPersonByCron() throws Exception {
        if ("close".equals(correctPersonDelTaskSwitch)){
            return;
        }

        LOG.debug("删除社区矫正人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsCorrectPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除社区矫正人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.drugPop}")
    public void syncDelDrugPopByCron() throws Exception {
        if ("close".equals(drugPopDelTaskSwitch)){
            return;
        }

        LOG.debug("删除吸毒人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsDrugPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除吸毒人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.emancipistPop}")
    public void syncDelEmancipistPopByCron() throws Exception {
        if ("close".equals(emancipistPopDelTaskSwitch)){
            return;
        }

        LOG.debug("删除刑满释放人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsEmancipistPop);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除刑满释放人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.eventDisLitigantInfo}")
    public void syncDelEventDisLitigantInfoByCron() throws Exception {
        if ("close".equals(eventDisLitigantInfoDelTaskSwitch)){
            return;
        }

        LOG.debug("删除矛盾纠纷排查化解主要当事人信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsEventDisLitigantInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除矛盾纠纷排查化解主要当事人信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.eventDisputeResolve}")
    public void syncDelEventDisputeResolveByCron() throws Exception {
        if ("close".equals(eventDisputeResolveDelTaskSwitch)){
            return;
        }

        LOG.debug("删除矛盾纠纷排查化解定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsEventDisputeResolve);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除矛盾纠纷排查化解定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.delcmps.gridBasicInfo}")
    public void syncDelGridBasicInfoByCron() throws Exception {
        if ("close".equals(gridBasicInfoDelTaskSwitch)){
            return;
        }

        LOG.debug("删除网格基本信息定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridBasicInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格基本信息定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.gridPersonInfo}")
    public void syncDelGridPersonInfoByCron() throws Exception {
        if ("close".equals(gridPersonInfoDelTaskSwitch)){
            return;
        }

        LOG.debug("删除网格人员定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridPersonInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格人员定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.gridPopJoin}")
    public void syncDelGridPopJoinByCron() throws Exception {
        if ("close".equals(gridPopJoinDelTaskSwitch)){
            return;
        }

        LOG.debug("删除网格单元与网格人员关联关系定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridPopJoin);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格单元与网格人员关联关系定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.gridUnitInfo}")
    public void syncDelGridUnitInfoByCron() throws Exception {
        if ("close".equals(gridUnitInfoDelTaskSwitch)){
            return;
        }

        LOG.debug("删除网格单元定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsGridUnitInfo);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除网格单元定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.keyYouth}")
    public void syncDelKeyYouthByCron() throws Exception {
        if ("close".equals(keyYouthDelTaskSwitch)){
            return;
        }

        LOG.debug("删除重点青少年定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsKeyYouth);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除重点青少年定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }



    @Scheduled(cron = "${cron.delcmps.massDefendOrg}")
    public void syncDelMassDefendOrgByCron() throws Exception {
        if ("close".equals(massDefendOrgDelTaskSwitch)){
            return;
        }

        LOG.debug("删除群防群治组织定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsMassDefendOrg);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除群防群治组织定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

    @Scheduled(cron = "${cron.delcmps.massDefendTeam}")
    public void syncDelMassDefendTeamByCron() throws Exception {
        if ("close".equals(massDefendTeamDelTaskSwitch)){
            return;
        }

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
        if ("close".equals(mentalPatientDelTaskSwitch)){
            return;
        }

        LOG.debug("删除肇事肇祸等严重精神障碍患者定时任务开始执行->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));

        IDataSync iDataSync = dataImportFactory.get(DataType.deleteCmpsMentalPatient);
        if (iDataSync != null){
            iDataSync.syncDatas();
        }
        LOG.debug("删除肇事肇祸等严重精神障碍患者定时任务执行结束->执行时间："+DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    }

}
