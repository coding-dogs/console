package com.easyorder.common.utils;

import com.google.gson.Gson;

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
}
