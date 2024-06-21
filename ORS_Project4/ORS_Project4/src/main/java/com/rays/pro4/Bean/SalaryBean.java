package com.rays.pro4.Bean;

import java.util.Date;

public class SalaryBean extends BaseBean {

	private String name;
	private String salary;
	private Date date;
	private String status;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return status +"";
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return status;
	}
}
