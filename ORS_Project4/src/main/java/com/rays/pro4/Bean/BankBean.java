package com.rays.pro4.Bean;

import java.util.Date;

public class BankBean extends BaseBean{

	private String name;
	private Date openDate;
	private String type;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return type +"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return type;
	}
	
	
}