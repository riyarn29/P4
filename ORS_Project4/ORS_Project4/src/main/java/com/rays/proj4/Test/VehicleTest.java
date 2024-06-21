package com.rays.proj4.Test;

import java.text.SimpleDateFormat;

import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Model.VehicleModel;

public class VehicleTest {

	public static void main(String[] args) throws Exception {
		//add();
		update();
	}
	
	private static void update() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		VehicleBean bean = new VehicleBean();
		bean.setId(1);
		bean.setPurchaseBy("yyyayayaaaa");
		bean.setPurchaseDate(sdf.parse("05-07-1997"));
		bean.setCompany("tata");
		bean.setManufacturedBy("ayayay");
		VehicleModel model = new VehicleModel();
		 model.update(bean);
		
		
	}

	public static void add()throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		VehicleBean bean = new VehicleBean();
		bean.setId(1);
		bean.setPurchaseBy("riya");
		bean.setPurchaseDate(sdf.parse("05-07-1997"));
		bean.setCompany("tata");
		bean.setManufacturedBy("ayayay");
		VehicleModel model = new VehicleModel();
		long i = model.add(bean);
		System.out.println(i);
	}
}
