/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 *
 * @author Pellym Huang
 * @created 2018/12/12 22:36
 */
public class JSONUtils {
    /**
     * @description
     * @author Pellym Huang
     * @created 2018/12/12 22:36
     */
    private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * @description
     *
     * @author Pellym Huang
     * @created 2018/12/12 22:38
     */
    public static final <T> List<T> getList(String jsontext, String listStr, Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(listStr);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JSONArray) {
            JSONArray jsonarr = (JSONArray) obj;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < jsonarr.size(); i++) {
                list.add(jsonarr.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }

    /**
     * @param <T>      -> DepartmentBean
     * @param jsontext -> {"department":{"id":"1","name":"生产部"},"password":"admin",
     *                 "username":"admin"}
     *                 -> department
     * @param clazz    -> DepartmentBean
     * @return -> T
     */
    public static final <T> T getObject(String jsontext, String objStr, Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(objStr);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonobj.getObject(objStr, clazz);
        } else {
            logger.info(obj.getClass() + "");
        }

        return null;
    }

    /**
     * @param <T>
     * @param jsontext ->{"department":{"id":"1","name":"生产部"},"password":"admin",
     *                 "username":"admin"}
     * @param clazz    -> UserBean.class
     * @return -> UserBean
     */
    // 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, clazz);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsontext, e);
        }
        return t;
    }

    /**
     * 描述
     *
     * @param object
     * @param prettyFormat
     * @return
     */
    public static final String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    /**
     * @param jsonStr json字符串
     * @param clazz   class名称
     * @return
     * @Description： json字符串转成为List
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     * @param jsonString json字符串
     * @return
     * @Description： json字符串转换成list<Map>
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString, new com.alibaba.fastjson.TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            logger.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * @param jsonStr json字符串
     * @return
     * @Description： json字符串转换为Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }


    /**
     * 获取请求中的json对象
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static JSONObject getJsonData(HttpServletRequest request) throws Exception {
        String resStr = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer("");
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        resStr = sb.toString();
        JSONObject json = JSONObject.parseObject(resStr);
        return json;

    }

    /**
     * 描述
     *
     * @param body
     * @param field
     * @return
     */
    public static String parseString(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null)
                return leaf.asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述
     *
     * @param field
     * @return
     * @author Fandy Liu
     */
    public static Integer parseInteger(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null)
                return leaf.asInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> parseStringList(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null)
                return mapper.convertValue(leaf, new TypeReference<List<String>>() {
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param body
     * @param field
     * @return
     */
    public static Boolean parseBoolean(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null)
                return leaf.asBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Short parseShort(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                Integer value = leaf.asInt();
                return value.shortValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述 json字符转field
     *
     * @param body
     * @param field
     * @return
     */
    public static Byte parseByte(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                Integer value = leaf.asInt();
                return value.byteValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述 获取json字符串中的对象
     *
     * @param body
     * @param field
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String body, String field, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            node = node.get(field);
            return mapper.treeToValue(node, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述
     *
     * @param json
     * @return
     */
    public static Object toNode(String json) {
        if (json == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(json);
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @description
     * @author Pellym Huang
     * @created 2018/12/12 22:29
     */
    public static String xmlToJSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            Map map = iterateElement(root);
            obj.put(root.getName(), map);
            return obj.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * @description
     * @author Pellym Huang
     * @created 2018/12/12 22:30
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Map iterateElement(Element root) {
        List childrenList = root.getChildren();
        Element element = null;
        Map map = new HashMap();
        List list = null;
        for (int i = 0; i < childrenList.size(); i++) {
            list = new ArrayList();
            element = (Element) childrenList.get(i);
            if (element.getChildren().size() > 0) {
                if (root.getChildren(element.getName()).size() > 1) {
                    if (map.containsKey(element.getName())) {
                        list = (List) map.get(element.getName());
                    }
                    list.add(iterateElement(element));
                    map.put(element.getName(), list);
                } else {
                    map.put(element.getName(), iterateElement(element));
                }
            } else {
                if (root.getChildren(element.getName()).size() > 1) {
                    if (map.containsKey(element.getName())) {
                        list = (List) map.get(element.getName());
                    }
                    list.add(element.getTextTrim());
                    map.put(element.getName(), list);
                } else {
                    map.put(element.getName(), element.getTextTrim());
                }
            }
        }

        return map;
    }
}