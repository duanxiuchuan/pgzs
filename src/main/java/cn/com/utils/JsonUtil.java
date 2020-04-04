package cn.com.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 对象POJO和JSON互转
 *
 * @author LiDaDa
 */
public class JsonUtil {
    /**
     * JSON 转 POJO
     */
    public static <T> T getObject(String pojo, Class<T> tclass) {
        try {
            return JSONObject.parseObject(pojo, tclass);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * POJO 转 JSON
     */
    public static <T> String getJson(T tResponse){
        String pojo = JSONObject.toJSONString(tResponse);
        return pojo;
    }
}
