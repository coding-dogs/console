package com.easyorder.modules.product.entity;

import java.util.List;

public class SpecificationItem {
	private String name;
	private String no;
	private List<SpecificationItem> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public List<SpecificationItem> getChildren() {
		return children;
	}
	public void setChildren(List<SpecificationItem> children) {
		this.children = children;
	}
	
}
