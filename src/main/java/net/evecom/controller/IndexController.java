/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页
 * @author Fandy Liu
 * @created 2018年12月12日 下午5:46:33
 */
@Controller
public class IndexController {
    /**
     * 
     * 描述
     * @author Fandy Liu
     * @created 2018年12月12日 下午11:31:45
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String go2Index(){
        return "index";
    }
    /**
     * 
     * 描述
     * @author Fandy Liu
     * @created 2018年12月12日 下午11:31:49
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/index";
    }
}