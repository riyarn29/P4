package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.SalaryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SalaryCtl", urlPatterns = { "/ctl/SalaryCtl" })
public class SalaryCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("jctl Validate");
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
			
		}  else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "name must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}
		
		
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("salary"))) {
			request.setAttribute("salary", PropertyReader.getValue("error.require", "salary"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("salary"))) {
			request.setAttribute("salary", "salary must contains integer values only");
			pass = false;
		}
		

		return pass;

	}

	

	protected BaseBean populateBean(HttpServletRequest request) {
	
		
		SalaryBean bean = new SalaryBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setStatus(DataUtility.getString(request.getParameter("status")));
		
		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		bean.setSalary(DataUtility.getString(request.getParameter("salary")));

		


		
	
		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		System.out.println("s ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		SalaryModel model = new SalaryModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			SalaryBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
	
		ServletUtility.forward(getView(), request, response);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		SalaryModel model = new SalaryModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SalaryBean bean = (SalaryBean) populateBean(request);
			

			try {
				if (id > 0) {
 
				
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" salary ctl DoPost 222222");
					ServletUtility.setSuccessMessage("salary is successfully Updated", request);

				} else {
					System.out.println(" salary ctl DoPost 33333");
					long pk = model.add(bean);
					

					ServletUtility.setSuccessMessage("salary is successfully Added", request);
					
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage( is successfully saved", request);
				 */

			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" salary D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("salary EXISTS", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" J ctl D p 5555555");

			SalaryBean bean = (SalaryBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.SALARY_CTL, request, response);
				return;
			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" J  ctl Do post 77777");

			ServletUtility.redirect(ORSView.SALARY_LIST_CTL, request, response);
			return;
		}
	
		ServletUtility.forward(getView(), request, response);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.SALARY_VIEW;
	}


}
