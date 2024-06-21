package com.rays.pro4.Bean;

import java.util.Date;

public class DoctorBean extends BaseBean{


	private String name;
	private String mobile;
	private Date dob;
	private String expertise;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return  expertise;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return expertise;
	}
}
