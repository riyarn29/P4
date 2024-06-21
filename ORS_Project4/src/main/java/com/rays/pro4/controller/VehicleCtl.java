package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.VehicleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "VehicleCtl", urlPatterns = { "/ctl/VehicleCtl" })
public class VehicleCtl extends BaseCtl{

		@Override
		protected void preload(HttpServletRequest request) {
			System.out.println("uctl preload");
		VehicleModel model = new VehicleModel();
		
	    List list;
		try {
			list = model.list(0, 0);
			request.setAttribute("vList", list);

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

			if (DataValidator.isNull(request.getParameter("purchaseBy"))) {
				request.setAttribute("purchaseBy", PropertyReader.getValue("error.require", "PURCHASE BY"));
				pass = false;
			} else if (!DataValidator.isName(request.getParameter("purchaseBy"))) {
				request.setAttribute("purchaseBy", "purchaseBy must contains alphabet only");
				pass = false;
			}
			
			if (DataValidator.isNull(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.require", "Date"));
				pass = false;
			} else if (!DataValidator.isDate(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.date", "Date"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("company"))) {
				request.setAttribute("company", PropertyReader.getValue("error.require", "company"));
				pass = false;
			} else if (!DataValidator.isName(request.getParameter("company"))) {
				request.setAttribute("company", "company must contains alphabet only");
				pass = false;
			}
			
			if (DataValidator.isNull(request.getParameter("manufacturedBy"))) {
				request.setAttribute("manufacturedBy", PropertyReader.getValue("error.require", "manufacturedBy"));
				pass = false;
			} else if (!DataValidator.isName(request.getParameter("manufacturedBy"))) {
				request.setAttribute("manufacturedBy", "manufacturedBy must contains alphabet only");
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
			
			VehicleBean bean = new VehicleBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));

			bean.setName(DataUtility.getString(request.getParameter("name")));

			bean.setPurchaseBy(DataUtility.getString(request.getParameter("purchaseBy")));
			
			bean.setPurchaseDate(DataUtility.getDate(request.getParameter("dob")));

			bean.setCompany(DataUtility.getString(request.getParameter("company")));

			bean.setManufacturedBy(DataUtility.getString(request.getParameter("manufacturedBy")));


			
		
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
		
			System.out.println("u ctl do get 1111111");
			String op = DataUtility.getString(request.getParameter("operation"));
			// get model
			VehicleModel model = new VehicleModel();
			long id = DataUtility.getLong(request.getParameter("id"));
			if (id > 0 || op != null) {
				System.out.println("in id > 0  condition");
				VehicleBean bean;
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

			VehicleModel model = new VehicleModel();
			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
				VehicleBean bean = (VehicleBean) populateBean(request);
				System.out.println(" U ctl DoPost 11111111");

				try {
					if (id > 0) {
	 
						// System.out.println("hi i am in dopost update");
						model.update(bean);
						ServletUtility.setBean(bean, request);
						System.out.println(" U ctl DoPost 222222");
						ServletUtility.setSuccessMessage("VEHICLE is successfully Updated", request);

					} else {
						System.out.println(" U ctl DoPost 33333");
						long pk = model.add(bean);
						// bean.setId(pk);
						// ServletUtility.setBean(bean, request);

						ServletUtility.setSuccessMessage("VEHICLE is successfully Added", request);
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
					System.out.println(" U ctl D post 4444444");
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Login id already exists", request);
				}
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println(" U ctl D p 5555555");

				VehicleBean bean = (VehicleBean) populateBean(request);
				try {
					model.delete(bean);
					System.out.println(" u ctl D Post  6666666");
					ServletUtility.redirect(ORSView.USER_CTL, request, response);
					return;
				} catch (ApplicationException e) {
				
					ServletUtility.handleException(e, request, response);
					return;
				}

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {
				System.out.println(" U  ctl Do post 77777");

				ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
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
			return ORSView.VEHICLE_VIEW;
		}

	}


