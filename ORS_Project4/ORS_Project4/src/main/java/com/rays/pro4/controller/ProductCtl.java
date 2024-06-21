package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ProductCtl", urlPatterns = { "/ctl/ProductCtl" })
public class ProductCtl extends BaseCtl {
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		ProductModel model = new ProductModel();

		ProductBean bean = new ProductBean();

		try {

			List rlist = model.list(0,0);
			request.setAttribute("rlist", rlist);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Product Name"));
			pass = false;
		} else 
		
		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "Product Price"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Product Status"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Purchase Date"));
			pass = false;
		}
		
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ProductBean bean = new ProductBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setAmount(DataUtility.getString(request.getParameter("amount")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		bean.setPurchaseDate(DataUtility.getDate(request.getParameter("dob")));
		

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("j ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		ProductModel model = new ProductModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			ProductBean bean;
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

		ProductModel model = new ProductModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ProductBean bean = (ProductBean) populateBean(request);
			

			try {
				if (id > 0) {
 
				
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" J ctl DoPost 222222");
					ServletUtility.setSuccessMessage("product is successfully Updated", request);

				} else {
					System.out.println(" J ctl DoPost 33333");
					long pk = model.add(bean);
					

					ServletUtility.setSuccessMessage("product is successfully Added", request);
					
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

			ProductBean bean = (ProductBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
			
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" J  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
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
		return ORSView.PRODUCT_VIEW;
	}
}