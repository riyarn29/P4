package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.LessonBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.LessonModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet (name = "LessonCtl" , urlPatterns = {"/ctl/LessonCtl"})
public class LessonCtl extends BaseCtl{
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("uctl preload");
	LessonModel model = new LessonModel();
		
		
		
		List sub;
		try {
			sub = model.list(0, 0);
			request.setAttribute("sub",sub);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name","name must contains alphabet only");
			pass = false;
		}
			if (DataValidator.isNull(request.getParameter("subject"))) {
				request.setAttribute("subject", PropertyReader.getValue("error.require", "subject"));
				pass = false;
			} else if (!DataValidator.isName(request.getParameter("subject"))) {
				request.setAttribute("subject","subject must contains alphabet only");
				pass = false;
			
			}

		if (DataValidator.isNull(request.getParameter("author"))) {
			request.setAttribute("author", PropertyReader.getValue("error.require", "author name"));
			pass = false;
} 
		  else if (!DataValidator.isName(request.getParameter("author"))) {
		  request.setAttribute("author", "author name must contains alphabet only"); pass =
		  false; }
		 
		
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "author dob"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "author dob"));
			pass = false;
		}

		return pass;
		
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		
		LessonBean bean = new LessonBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));
		
		bean.setAuthor(DataUtility.getString(request.getParameter("author")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		
		bean.setSubject(DataUtility.getString(request.getParameter("subject")));

	
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
	
		System.out.println("l ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		LessonModel model = new LessonModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			LessonBean bean;
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
		System.out.println("uctl Do Post");


		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		LessonModel model = new LessonModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			LessonBean bean = (LessonBean) populateBean(request);
			System.out.println(" U ctl DoPost 11111111");

			try {
				if (id > 0) {
 
					// System.out.println("hi i am in dopost update");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					ServletUtility.setSuccessMessage("lesson is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);
					// bean.setId(pk);
					// ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("lesson is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("User is successfully saved", request);
				 */

			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" L ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" L ctl D p 5555555");

			LessonBean bean = (LessonBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" L ctl D Post  6666666");
				ServletUtility.redirect(ORSView.LESSON_CTL, request, response);
				return;
			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" L  ctl Do post 77777");

			ServletUtility.redirect(ORSView.LESSON_LIST_CTL, request, response);
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
		return ORSView.LESSON_VIEW;
	}

}