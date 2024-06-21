package com.rays.pro4.Bean;

public class OrderBean extends BaseBean {
	
	private String shop;
	private String type;
	private int price;

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	}

}