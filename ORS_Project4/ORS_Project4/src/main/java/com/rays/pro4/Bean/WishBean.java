
package com.rays.pro4.Bean;

import java.util.Date;

public class WishBean extends BaseBean {

	private String Product;
	private Date Dob;
	private String UserName;
	private String Remark;

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Product + " ";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Product;
	}

	
}
