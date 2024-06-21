package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.DoctorBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.DoctorModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "DoctorCtl", urlPatterns = { "/ctl/DoctorCtl" })

public class DoctorCtl extends BaseCtl {

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

		if (DataValidator.isNull(request.getParameter("expertise"))) {
			request.setAttribute("expertise", PropertyReader.getValue("error.require", "expertise"));
			pass = false;
		}
		
		
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Dob"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {
			request.setAttribute("mobile", "Mobile No. contain 10 Digits & Series start with 6-9");
			pass = false;
		}

		return pass;

	}

	

	protected BaseBean populateBean(HttpServletRequest request) {
	
		
		DoctorBean bean = new DoctorBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setMobile(DataUtility.getString(request.getParameter("mobile")));
		
		bean.setDob(DataUtility.getDate(request.getParameter("date")));

		bean.setExpertise(DataUtility.getString(request.getParameter("expertise")));


		


		
	
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
		DoctorModel model = new DoctorModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			DoctorBean bean;
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

		DoctorModel model = new DoctorModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			DoctorBean bean = (DoctorBean) populateBean(request);
			

			try {
				if (id > 0) {
 
				
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" doctor ctl DoPost 222222");
					ServletUtility.setSuccessMessage("doctor is successfully Updated", request);

				} else {
					System.out.println(" doctor ctl DoPost 33333");
					long pk = model.add(bean);
					

					ServletUtility.setSuccessMessage("doctor is successfully Added", request);
					
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
				System.out.println(" doctor D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("doctor EXISTS", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" J ctl D p 5555555");

			DoctorBean bean = (DoctorBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
				return;
			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" J  ctl Do post 77777");

			ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
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
		return ORSView.DOCTOR_VIEW;
	}


}
