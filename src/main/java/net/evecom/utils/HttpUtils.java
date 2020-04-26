/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 描述
 * 
 * @author Fandy Liu
 * @created 2018年12月10日 下午3:17:06
 */
public class HttpUtils {
    /**
     * 
     */
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * in milliseconds
     */
    private final static int CONNECT_TIMEOUT = 5000; //
    /**
     * 
     */
    private final static String DEFAULT_ENCODING = "UTF-8";

    /**
     * 描述
     * 
     * @author Fandy Liu
     * @created 2018年12月10日 下午3:17:28
     * @param urlStr
     * @param data
     * @return
     * @throws IOException
     */
    public static String postData(String urlStr, String data) throws IOException {
        return postData(urlStr, data, null);
    }

    /**
     * 
     * 描述
     * 
     * @author Fandy Liu
     * @created 2018年12月10日 下午3:17:33
     * @param urlStr
     * @param data
     * @param contentType
     * @return
     * @throws IOException
     */
    public static String postData(String urlStr, String data, String contentType) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            if (contentType != null)
                conn.setRequestProperty("content-type", contentType);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if (data == null) {
                writer.write("");
            } else {
                writer.write(data);
            }
            writer.flush();
            writer.close();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                LOG.error(e.toString());
            }
        }
    }

    /**
     * 描述
     * 
     * @author Fandy Liu
     * @created 2018年12月10日 下午3:17:39
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String postHttp(String url, Map<String, String> map) throws Exception {
        HttpClient httpclient = null;
        PostMethod post = null;
        SimpleHttpConnectionManager simpleHttpConnectionManager = null;
        String info = null;
        try {
            httpclient = new HttpClient();
            post = new PostMethod(url);
            // 设置编码方式
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
            // 添加参数
            if (map != null) {
                StringBuffer sb = new StringBuffer();
                int count = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (count == 0) {
                        sb.append("?");
                    } else {
                        sb.append("&");
                    }
                    count++;
                    sb.append(key + "=" + entry.getValue());
                    post.addParameter(key, entry.getValue()==null?"":entry.getValue());
                }
                LOG.info("请求的url：{}", url + sb.toString());
            }
            // 执行
            httpclient.executeMethod(post);
            info = IOUtils.toString(post.getResponseBodyAsStream(), "UTF-8");
            // 接口返回信息
            LOG.info(info);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            // 关闭连接，释放资源
            if (post != null) {
                post.releaseConnection();
            }
            if (httpclient != null) {
                simpleHttpConnectionManager = ((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager());
                if (simpleHttpConnectionManager != null) {
                     simpleHttpConnectionManager.shutdown();
                }
            }
        }
        return info;
    }

    /**
     * 处理get请求
     * 
     * @param url
     *            请求地址 如
     *            http://localhost:9090/base_rpc/basicData/getInvoice?t=
     *            1507513445960&invoiceId=039D906D07C74306B635DD89F87584CD
     *            &token=bd302857fbd4a01af7401fe229d43918
     * @return
     * @throws Exception
     */
    public static String getHttp(String url) throws Exception {
        HttpClient httpClient = null;
        GetMethod get = null;
        SimpleHttpConnectionManager simpleHttpConnectionManager = null;
        String info = null;

        try {
            httpClient = new HttpClient();
            get = new GetMethod(url);
            // 执行
            httpClient.executeMethod(get);
            // 接口返回信息
            info =IOUtils.toString(get.getResponseBodyAsStream(), "UTF-8");
            LOG.info("接口【" + url + "】 返回:" + info);
        } catch (Exception e) {
            LOG.info("调用接口【" + url + "】  出错:" + e);
            throw e;
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (httpClient != null) {
                simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient.getHttpConnectionManager();
                if (simpleHttpConnectionManager != null) {
                    simpleHttpConnectionManager.shutdown();
                }
            }
        }
        return info;
    }
    
    

}
