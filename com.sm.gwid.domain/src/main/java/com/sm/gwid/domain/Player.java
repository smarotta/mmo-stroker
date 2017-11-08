package com.sm.gwid.domain;

public class Player extends IdentifiableDomain {

	private String cellphone;
	private String name;
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
