package com.rays.pro4.Bean;

import java.util.Date;

public class JobRequirementBean extends BaseBean {

	private String title;
	private String client;
	private Date openDate;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return client +"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return client;
	}

}
