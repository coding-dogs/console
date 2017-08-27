package com.easyorder.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GSONUtils {
	private static final Gson GSON = new Gson();

	private GSONUtils() {

	}

	public static String toJSON(Object object) {
		return GSON.toJson(object);
	}

	public static <T> T fromJson(String json, Class<T> clz) {
		T t = null;
		if(GSON != null) {
			t = GSON.fromJson(json, clz);
		}
		return t;
	} 

	/**
	 * json字符串转集合
	 * @param jsonList json字符串
	 * @param clz 泛型类型
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonList, Class<T> cls) {
		if(!StringUtils.hasText(jsonList)) {
			return null;
		}
		List<T> list = new ArrayList<T>();  
		if (GSON != null) {  
			JsonArray array = new JsonParser().parse(jsonList).getAsJsonArray();
			for(final JsonElement elem : array){  
        list.add(GSON.fromJson(elem, cls));  
    }  
		}  
		return list;  
	}

	/**
	 * json字符串转Map
	 * @param gsonString json字符串
	 * @return
	 */
	public static <T> Map<String, T> jsonToMap(String gsonString) {  
		Map<String, T> map = null;  
		if (GSON != null) {  
			map = GSON.fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());  
		}  
		return map;  
	}  
}
