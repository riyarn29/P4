package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.JobRequirementBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.JobRequirementModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "JobRequirementCtl", urlPatterns = { "/ctl/JobRequirementCtl" })

public class JobRequirementCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("jctl preload");
	JobRequirementModel model = new JobRequirementModel();
	
    List jlist;
	try {
		jlist = model.list(0, 0);
		request.setAttribute("jList", jlist);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
     
	}
	

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("jctl Validate");
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
			
		}  else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "Title must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("client"))) {
			request.setAttribute("client", PropertyReader.getValue("error.require", "Client"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("client"))) {
			request.setAttribute("client", "client name must contains alphabet only");
			pass = false;
		}
		
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("description"))) {
			request.setAttribute("description", "description must contains alphabet only");
			pass = false;
		}
		

		return pass;

	}

	

	protected BaseBean populateBean(HttpServletRequest request) {
	
		
		JobRequirementBean bean = new JobRequirementBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setTitle(DataUtility.getString(request.getParameter("title")));

		bean.setClient(DataUtility.getString(request.getParameter("client")));
		
		bean.setOpenDate(DataUtility.getDate(request.getParameter("date")));

		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		


		
	
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
	
		System.out.println("j ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		JobRequirementModel model = new JobRequirementModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			JobRequirementBean bean;
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

		JobRequirementModel model = new JobRequirementModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			JobRequirementBean bean = (JobRequirementBean) populateBean(request);
			

			try {
				if (id > 0) {
 
				
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" J ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Job is successfully Updated", request);

				} else {
					System.out.println(" J ctl DoPost 33333");
					long pk = model.add(bean);
					

					ServletUtility.setSuccessMessage("Job is successfully Added", request);
					
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
				System.out.println(" Jctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("JOB EXISTS", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" J ctl D p 5555555");

			JobRequirementBean bean = (JobRequirementBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.JOBREQUIREMENT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" J  ctl Do post 77777");

			ServletUtility.redirect(ORSView.JOBREQUIREMENT_LIST_CTL, request, response);
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
		return ORSView.JOBREQUIREMENT_VIEW;
	}

}