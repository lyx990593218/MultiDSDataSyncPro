/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.service;

import net.evecom.utils.StrFormatter;
import net.evecom.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 文件
 * @author Fandy Liu
 * @created 2018年11月14日 上午11:09:50
 */
@Service
public class AttachService {
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:36
     * @return: null
     */
    @Value("${downloadDomain}")
    private String downloadDomain;
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:36
     * @return: null
     */
    @Value("${cmpsDomain}")
    private String cmpsDomain;
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    @Value("${cmpsDomain2}")
    private String cmpsDomain2;
    /**
     *
     * @author Douglas Lai
     * @date 2019/11/27 9:37
     * @return: null
     */
    @Autowired
    JdbcTemplate jdbcTemplate;
    //private static final String queryByIdsSql = "select * from {} where id in ({})";
    /**
     * 
     * 根据ids查找附件的urls
     * @author Fandy Liu
     * @created 2018年12月8日 下午2:08:18
     * @param ids
     * @return
     */
    public  String queryUrlsByIds(String ids,String... type){
        String queryByIdsSql = "select * from {} where id in ({})";
        String selectUrl = cmpsDomain ;
        String tbName = "CMPS_COMM_PHOTOS";
        if(type.length>0&&StringUtils.isNotEmpty(type[0])){//另外的一个附件视图
            selectUrl = cmpsDomain2;
            tbName = "sys_attachment_emnh_v";
        }
        List<Map<String,Object>> attachs = jdbcTemplate
                .queryForList(StrFormatter.format(queryByIdsSql,tbName,StringUtils.toSql(ids)));
        StringBuffer retSb = new StringBuffer();
        for(Map<String,Object> attach:attachs){
            retSb.append(",");
            retSb.append(selectUrl+"/"+((String)attach.get("PHOTO_URL")).replaceAll("\\\\","/"));
        }
        if(retSb.length()>0){
            retSb.deleteCharAt(0);
        }
        return retSb.toString();
        
        
    }

    /**
     *
     * 根据ids查找附件的urls Map
     * @author Douglas Lai
     * @created 2019年11月24日 下午2:08:18
     * @param ids
     * @return
     */
    public  List<Map<String, Object>> queryUrlsLMByIds(String ids,String... type){
        String queryByIdsSql = "select * from {} where id in ({})";
        String selectUrl = cmpsDomain ;
        String downloadUrl = downloadDomain ;
        String tbName = "CMPS_COMM_PHOTOS";
        if(type.length>0&&StringUtils.isNotEmpty(type[0])){//另外的一个附件视图
            selectUrl = cmpsDomain2;
            tbName = "sys_attachment_emnh_v";
        }
        List<Map<String,Object>> attachs = jdbcTemplate
                .queryForList(StrFormatter.format(queryByIdsSql,tbName,StringUtils.toSql(ids)));
//        StringBuffer retSb = new StringBuffer();
        List<Map<String,Object>> rem = new ArrayList<>();
        for(Map<String,Object> attach:attachs){
//            retSb.append(",");
//            retSb.append(selectUrl+"/"+((String)attach.get("PHOTO_URL")).replaceAll("\\\\","/"));
            attach.put("DOWNLOAD_URL", downloadUrl+"/"+((String)attach.get("PHOTO_URL")).replaceAll("\\\\","/"));
            attach.put("PHOTO_URL", selectUrl+"/"+((String)attach.get("PHOTO_URL")).replaceAll("\\\\","/"));
            rem.add(attach);
        }
//        if(retSb.length()>0){
//            retSb.deleteCharAt(0);
//        }
        return rem;
    }

}
