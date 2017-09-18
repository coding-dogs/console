package com.easyorder.common.enums;

/**
 * 异步请求响应枚举
 * @author qiudequan
 */
public enum EasyResponseEnums {
	SUCCESS("200", "请求成功"),
	// 请求成功，但无响应结果
	SUCCESS_NO_RESULT("201", "请求成功"),
	
	REQUEST_PARAM_ERROR("400", "请求错误"),
	
	ERROR("500", "内部服务异常，请联系管理员"),
	
	NOT_FOUND_SUPPLIER("601", "未找到指定供应商"),
	NOT_FOUND_PRODUCT("602", "未找到商品信息"),
	NOT_FOUND_CUSTOMER("603", "未找到客户信息")
	;
	
	
	public String code;
	public String message;
	
	
	private EasyResponseEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
