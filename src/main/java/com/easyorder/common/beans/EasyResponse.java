package com.easyorder.common.beans;

import com.easyorder.common.enums.EasyResponseEnums;

/**
 * 异步请求响应类
 * @author qiudequan
 * @param <T> 响应结果范型
 */
public class EasyResponse<T> {
	private static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
	
	// 响应码，为达到规范使用，建议使用com.easyorder.common.enums.EasyResponseEnums中的枚举数据
	private String code;
	// 响应信息
	private String msg;
	// 响应结果
	private T result;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getResult() {
		return result;
	}
	
	public void setResult(T result) {
		this.result = result;
	}
	
	
	
	public EasyResponse(String code, T result, String msg) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	/**
	 * 构建请求成功响应体
	 * @param result 响应结果
	 * @param msg 响应信息
	 * @return
	 */
	public static <T> EasyResponse<T> buildSuccess(T result, String msg) {
		return new EasyResponse<T>(EasyResponseEnums.SUCCESS.code, result, msg);
	}
	
	/**
	 * 构建请求成功响应体
	 * @param result 响应结果
	 * @return
	 */
	public static <T> EasyResponse<T> buildSuccess(T result) {
		return new EasyResponse<T>(EasyResponseEnums.SUCCESS.code, result, DEFAULT_SUCCESS_MESSAGE);
	}
	
	/**
	 * 构建请求错误通用响应体
	 * @return
	 */
	public static <T> EasyResponse<T> buildError() {
		return new EasyResponse<T>(EasyResponseEnums.ERROR.code, null, EasyResponseEnums.ERROR.message);
	}
	
	/**
	 * 构建请求错误响应体
	 * @param msg 错误信息
	 * @return
	 */
	public static <T> EasyResponse<T> buildError(String msg) {
		return new EasyResponse<T>(EasyResponseEnums.ERROR.code, null, msg);
	}
	
	/**
	 * 使用com.easyorder.common.enums.EasyResponseEnums枚举构建响应体
	 * @param response com.easyorder.common.enums.EasyResponseEnums枚举
	 * @param result 响应结果
	 * @return
	 */
	public static <T> EasyResponse<T> buildByEnum(EasyResponseEnums response, T result) {
		return new EasyResponse<T>(response.code, result, response.message);
	}
	
	/**
	 * 使用com.easyorder.common.enums.EasyResponseEnums枚举构建响应体
	 * @param response com.easyorder.common.enums.EasyResponseEnums枚举
	 * @param result 响应结果
	 * @return
	 */
	public static <T> EasyResponse<T> buildByEnum(EasyResponseEnums response) {
		return new EasyResponse<T>(response.code, null, response.message);
	}
	
	/**
	 * 指定code和message构建响应体
	 * @param code 响应码
	 * @param result 响应结果
	 * @param msg 响应信息
	 * @return
	 */
	public static <T> EasyResponse<T> buildByCode(String code, T result, String msg) {
		return new EasyResponse<T>(code, result, msg);
	}
	
}
