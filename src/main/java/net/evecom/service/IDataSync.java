/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 描述 文件预览处理接口
 * 
 * @author Fandy Liu
 * @created 2018年11月14日 上午11:09:50
 */
public interface IDataSync {

    /**
     * 
     * 描述
     * @author Fandy Liu
     * @created 2019年1月4日 上午11:58:09
     * @return  返回对接后返回的信息
     * @throws Exception
     */
    @Transactional
    String syncDatas() throws Exception;

}
