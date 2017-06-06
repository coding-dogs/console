package com.easyorder.common.beans;

import com.easyorder.common.constant.EasyResponseCode;

public class EasyResponse<T> {
	private String code;
	private String msg;
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
	
	
	
	public EasyResponse(String code, String msg, T result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public static <T> EasyResponse<T> buildSuccess(T result, String msg) {
		return new EasyResponse<T>(EasyResponseCode.SUCCESS, msg, result);
	}
	
	public static <T> EasyResponse<T> buildError(String msg) {
		return new EasyResponse<T>(EasyResponseCode.ERROR, msg, null);
	}
	
}
