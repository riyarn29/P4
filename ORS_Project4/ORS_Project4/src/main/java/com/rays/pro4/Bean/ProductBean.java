package com.rays.pro4.Bean;

import java.util.Date;

public class ProductBean extends BaseBean{

	private String name;
	private String amount;
	private String status;
	private Date purchaseDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return status + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return status;
	}
	
	
	
}
