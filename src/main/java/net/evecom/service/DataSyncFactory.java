/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Fandy Liu
 * @created 2018年11月14日 上午10:16:51
 */
@Service
public class DataSyncFactory {
    /**
     * spring 环境
     */
    @Autowired
    ApplicationContext context;
    /**
     * 
     * 描述 获取数据导入接口实现类
     * @author Fandy Liu
     * @created 2018年12月14日 下午3:36:56
     * @param dataType
     * @return
     */
    public IDataSync get(DataType dataType) {
        Map<String, IDataSync> dataImportMap = context.getBeansOfType(IDataSync.class);
        return dataImportMap.get(dataType.getInstanceName());
    }
}
