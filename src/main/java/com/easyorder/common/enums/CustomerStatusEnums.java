package com.easyorder.common.enums;

/**
 * 客户状态枚举
 * @author qiudequan
 */
public enum CustomerStatusEnums {
	ACTIVATION("ACTIVATION", "待激活"),
	PENDING_AUDIT("PENDING_AUDIT", "待审核"),
	ENABLED("ENABLED", "已启用"),
	DEACTIVATED("DEACTIVATED", "已停用");
	
	
	public String value;
	public String label;
	
	private CustomerStatusEnums(String value, String label) {
		this.value = value;
		this.label = label;
	}
}
