package com.valueservice.djs.db.utils;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.util.StringUtil;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.TypeReference;
import com.meidusa.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;


public class JsonLibUtils {

	
    private JsonLibUtils() {}

    /**
     * 把JSON String 转换成 Map
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> jsonToMapOld(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        Map<String, Object> extMap = JSON.parseObject(jsonStr,
            new TypeReference<Map<String, Object>>() {
            });
        
        return extMap;
    }
    /**
     * 把JSON String 转换成 Map
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, ?> jsonToMap(String jsonStr) {
    	Map<String, ?> extMap = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			extMap = objectMapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return extMap;
    }
    
    /**
     * 把JSON String 转换成 Map
     * @param jsonStr
     * @return
     */
    public static Map<String, String> jsonToMapStrOld(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        Map<String, String> extMap = JSON.parseObject(jsonStr,
            new TypeReference<Map<String, String>>() {
            });
        return extMap;
    }
    /**
     * 把JSON String 转换成 Map
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMapStr(String jsonStr) {
    	if (StringUtils.isEmpty(jsonStr)) {
    		return null;
    	}
    	Map<String, String> extMap = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			extMap = objectMapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return extMap;
    }

    /**
     * 把 Map 转换成 JSON String
     * @param map
     * @return
     */
    public static String mapToJsonOld(Map<String, Object> map) {
        if(map == null || map.isEmpty()){
            return null;
        }
        return JSON.toJSONString(map, SerializerFeature.UseISO8601DateFormat);
    }
    /**
     * 把 Map 转换成 JSON String
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, ?> map) {
    	String json = null;
    	try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			json = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return json;
    }

    /**
     * 把Object 转换成JSON String
     * @param obj
     * @return
     */
    public static String objToJsonOld(Object obj) {
    	if(obj == null){
    		return null;
    	}
    	return JSON.toJSONString(obj, SerializerFeature.UseISO8601DateFormat);
    }
    
    /**
     * 把Object 转换成JSON String
     * @param obj
     * @return
     */
    public static String objToJson(Object obj) {
    	String json = null;
    	try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			//对象空值不参与序列化  对map  无效
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return json;
    }
    
    /**
     * 把json Sting 转换成object
     * @param jsonString 
     * @param cls
     * @return
     */
	public static <T> T jsonToObjOld(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = (T) JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
    
    /**
     * 把json Sting 转换成object
     * @param jsonString 
     * @param cls
     * @return
     */
    public static <T> T jsonToObj(String jsonString, Class<T> cls) {
    	T t = null;
    	try {
			ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); 
	    	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			t = objectMapper.readValue(jsonString, cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return t;
    }
}
