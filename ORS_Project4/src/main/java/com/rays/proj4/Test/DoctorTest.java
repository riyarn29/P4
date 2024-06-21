package com.rays.proj4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.DoctorBean;
import com.rays.pro4.Bean.MarksheetBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.DoctorModel;

public class DoctorTest {

public static void main(String[] args) {
	testList();
}

public static void testList() {
	try {
	DoctorModel model = new DoctorModel();
		DoctorBean bean = new DoctorBean();
		List list = new ArrayList();
		list = model.list(0,0);
		if (list.size() == 0) {
			System.out.println("Test List fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (DoctorBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getExpertise());
			System.out.println(bean.getMobile());
			
		}

	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
}