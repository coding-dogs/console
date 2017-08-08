package com.easyorder.common.enums;

public enum SequenceTypeEnums {
	PRODUCT_NO("product_no", "产品编号序列")
	;
	
	public String type;
	public String desc;
	
	private SequenceTypeEnums(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
}
