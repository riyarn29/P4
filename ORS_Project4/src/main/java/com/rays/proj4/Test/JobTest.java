package com.rays.proj4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.JobRequirementBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.JobRequirementModel;

public class JobTest {

	public static void main(String[] args) {
		testSearch();
	}
	
	private static void testSearch() {
		try {
			JobRequirementBean bean = new JobRequirementBean();
			JobRequirementModel model = new JobRequirementModel();
			List list = new ArrayList();
			bean.setDescription("full");
			// bean.setId(8L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (JobRequirementBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getTitle());
				System.out.println(bean.getClient());
				System.out.println(bean.getDescription());
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
}
