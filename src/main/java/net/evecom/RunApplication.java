/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom;

import net.evecom.service.DataSyncFactory;
import net.evecom.service.DataType;
import net.evecom.service.IDataSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

/**
 * 程序入口
 *
 * @author Fandy Liu
 * @created 2018年11月2日 上午10:15:01
 */
@SpringBootApplication()
public class RunApplication {
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:43
     * @return: null
     */
    private static final Logger LOG = LoggerFactory.getLogger(RunApplication.class);

    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:44
     * @return: null
     */
    @Autowired
    DataSyncFactory dataSyncFactory;

    /**
     * 描述
     *
     * @param args
     * @author Fandy Liu
     * @created 2018年11月2日 上午10:16:28
     */
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
        LOG.info("=======================启动成功====================");
    }


    /**
     * 描述 启动时执行
     *
     * @return
     * @author Fandy Liu
     * @created 2018年11月2日 上午10:16:33
     */
    @Bean
    CommandLineRunner init() {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {
//                IDataSync cmpsPopSync =  dataSyncFactory.get(DataType.cmpsPop);
//                cmpsPopSync.syncDatas();//实有人口

//                IDataSync cmpsLocalPopSync =  dataSyncFactory.get(DataType.cmpsLocalPop);
//                cmpsLocalPopSync.syncDatas();//户籍人口

//                IDataSync cmpsCorrectPopSync =  dataSyncFactory.get(DataType.cmpsCorrectPop);
//                cmpsCorrectPopSync.syncDatas();//社区矫正

//                IDataSync cmpsEmancipistPopSync =  dataSyncFactory.get(DataType.cmpsEmancipistPop);
//                cmpsEmancipistPopSync.syncDatas();//刑满释放

//                IDataSync cmpsDrugPopSync =  dataSyncFactory.get(DataType.cmpsDrugPop);
//                cmpsDrugPopSync.syncDatas();//吸毒人员

//                IDataSync cmpsAdisPopSync =  dataSyncFactory.get(DataType.cmpsAdisPop);
//                cmpsAdisPopSync.syncDatas();//艾滋病危险人员

//                IDataSync cmpsKeyYouthPopSync =  dataSyncFactory.get(DataType.cmpsKeyYouthPop);
//                cmpsKeyYouthPopSync.syncDatas();//重点青少年

//                IDataSync cmpsGridBasicInfoSync =  dataSyncFactory.get(DataType.cmpsGridBasicInfo);
//                cmpsGridBasicInfoSync.syncDatas();//网格基本信息



                //IDataSync orgcompsSync =  dataImportFactory.get(DataType.orgcomps);
                //orgcompsSync.syncDatas();//综治机构同步

                //IDataSync eventStartEvt = dataImportFactory.get(DataType.eventStartEvt);
                //eventStartEvt.syncDatas();//同步事件上报

                //IDataSync eventFlowTask = dataImportFactory.get(DataType.eventFlowTask);
                //eventFlowTask.syncDatas();//同步事件流转

                //IDataSync eventCloseEvt = dataImportFactory.get(DataType.eventCloseEvt);
                //eventCloseEvt.syncDatas();//同步事件结案

//                IDataSync eventEvlEvt = dataImportFactory.get(DataType.eventEvlEvt);
//                eventEvlEvt.syncDatas();//同步事件归档

//                IDataSync roadguardlines =  dataImportFactory.get(DataType.roadguardlines);
//                roadguardlines.syncDatas();//护路护线同步
//
//                IDataSync evtRoadLineCases =  dataImportFactory.get(DataType.evtRoadLineCases);
//                evtRoadLineCases.syncDatas();//线路事件同步
//
//                IDataSync school =  dataImportFactory.get(DataType.school);
//                school.syncDatas();//校园及周边重点人群同步

//                IDataSync evtSchoolCasesImpl = dataImportFactory.get(DataType.evtSchoolCases);
//                evtSchoolCasesImpl.syncDatas();//校园事件同步

//                IDataSync locBuildingsImpl = dataImportFactory.get(DataType.locBuildings);
//                locBuildingsImpl.syncDatas();//楼栋同步

//                IDataSync locRoomsImpl = dataImportFactory.get(DataType.locRooms);
//                locRoomsImpl.syncDatas();//房间同步

//                IDataSync popLeftBehinds = dataImportFactory.get(DataType.popLeftBehinds);
//                popLeftBehinds.syncDatas();//留守人员同步

//                IDataSync evtRentalHouse =  dataImportFactory.get(DataType.evtRentalHouse);
//                evtRentalHouse.syncDatas();//出租屋同步

//                IDataSync populations =  dataImportFactory.get(DataType.populations);
//                populations.syncDatas();//户籍人员同步

//                IDataSync popOverseasPeople =  dataImportFactory.get(DataType.popOverseasPeople);
//                popOverseasPeople.syncDatas();//境外人员同步
//
//                IDataSync popMobilePopulations =  dataImportFactory.get(DataType.popMobilePopulations);
//                popMobilePopulations.syncDatas();//流动人员同步

//                IDataSync orgVideoCenters =  dataImportFactory.get(DataType.orgVideoCenters);
//                orgVideoCenters.syncDatas();//流动人员同步

//                IDataSync mentalPatients = dataSyncFactory.get(DataType.mentalPatients);
//                mentalPatients.syncDatas();//精神障碍人员

//                IDataSync aidsPatients = dataImportFactory.get(DataType.aidsPatients);
//                aidsPatients.syncDatas();//艾滋病人员

//                IDataSync drugPopulation = dataImportFactory.get(DataType.drugPopulation);
//                drugPopulation.syncDatas();//吸毒人员


                //其余模块自行实现
                //。。。。
                /*IDataSync detailTest = dataImportFactory.get(DataType.detailTest);
                detailTest.syncDatas();//流动人员同步*/
            }
        };
    }


}
