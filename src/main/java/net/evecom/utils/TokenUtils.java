/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取token工具类
 * 
 * @author Fandy Liu
 * @created 2018年11月14日 上午10:15:02
 */
@Component
public class TokenUtils {
    /**
     * 
     */
    @Value("${sycBaseUrl}")
    private String syncBaseUrl;
    /**
     * 
     */
    @Value("${clientid}")
    private String clientid;
    /**
     * 
     */
    @Value("${secretkey}")
    private String secretkey;
    /**
     * 缓存accessToken 的Map,map中包含 一个accessToken和 缓存的时间戳
     */
    private Map<String, String> map = new HashMap<String, String>();

    /**
     * 获取 accessToken 已加入缓存机制
     * 
     * @param appid
     * @param appsecret
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        String accessToken = map.get("accessToken");// 从缓存中拿数据
        String expiresTime = map.get("expireTime");// 从缓存中拿数据
        Long nowDate = new Date().getTime();
        if (accessToken != null && expiresTime != null && Long.valueOf(expiresTime) - nowDate > 0) {
            return accessToken;
        } else {
            Map<String, String> info = reqAccessToken();
            return info.get("accessToken");
        }
    }

    /**
     * post请求获取token
     * 
     * @author Fandy Liu
     * @created 2018年12月5日 下午12:02:18
     * @return
     * @throws Exception
     */
    private Map<String, String> reqAccessToken() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientid);
        params.put("client_secret", secretkey);
        // System.out.println(HttpUtils.postHttp(syncBaseUrl+"/oauth/token",params));
        Map<String, Object> ret = JSONUtils.json2Map(HttpUtils.postHttp(syncBaseUrl + "/oauth/token", params));
        if (ret != null && ret.get("error") == null) {
            String accessToken = (String) ret.get("access_token");
            Long expiresIn = Long.valueOf(ret.get("expires_in").toString());
            Long nowDate = new Date().getTime();
            Long expireTime = nowDate + expiresIn * 1000;
            map.put("accessToken", accessToken);
            map.put("expireTime", expireTime.toString());
            map.put("expireIn", expiresIn.toString());
            return map;
        } else {
            throw new Exception("获取token失败失败原因：" + ret.get("error"));
        }
    }
}
