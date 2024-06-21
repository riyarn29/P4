
package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.WishBean;
import com.rays.pro4.Model.WishModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "WishCtl", urlPatterns = { "/ctl/WishCtl" })
public class WishCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("Product"))) {
			request.setAttribute("Product", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Product"))) {
			request.setAttribute("Product", "Product must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("UserName"))) {
			request.setAttribute("UserName", PropertyReader.getValue("error.require", "UserName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("UserName"))) {
			request.setAttribute("UserName", "UserName must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Remark"))) {
			request.setAttribute("Remark", PropertyReader.getValue("error.require", "Remark"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Remark"))) {
			request.setAttribute("Remark", "Remark must contains alphabet only");
			pass = false;
		}
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		WishBean bean = new WishBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setProduct(DataUtility.getString(request.getParameter("Product")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
        bean.setUserName(DataUtility.getString(request.getParameter("UserName")));
		bean.setRemark(DataUtility.getString(request.getParameter("Remark")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		WishModel model = new WishModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			WishBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		WishModel model = new WishModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WISH_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WISH_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			WishBean bean = (WishBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Wish is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Wish not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("Wish is successfully Added", request);
					ServletUtility.setBean(bean, request);

					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Wish not added");
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.WISH_VIEW;
	}

}
