/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.controller;

import com.google.common.collect.ImmutableMap;
import net.evecom.service.DataSyncFactory;
import net.evecom.service.DataType;
import net.evecom.service.IDataSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据同步Controller
 * 
 * @author Fandy Liu
 * @created 2018年11月14日 上午11:08:53
 */
@Controller
public class DataSyncController {
    /**
     * 
     */
    @Autowired
    DataSyncFactory dataSyncFactory;

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "syncData", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object syncData(String dataTypes, HttpServletRequest req) throws Exception {
        if (dataTypes == null) {
            throw new Exception("请传递要对接模块的参数");
        }
        StringBuffer retSb = new StringBuffer();
        for (String dataType : dataTypes.split(",")) {
            // 获取到实现的接口
            IDataSync iDataSync = dataSyncFactory.get(DataType.getInterfaceName(dataType));
            retSb.append(iDataSync.syncDatas());// 调用接口
            retSb.append("<br/><br/><br/><br/><br/><br/><br/>");
        }
        return ImmutableMap.<String, String> builder().put("errorMsg", retSb.toString()).build();
    }
}
