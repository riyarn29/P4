package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Exception.RecordNotFoundException;
import com.rays.pro4.Util.EmailBuilder;
import com.rays.pro4.Util.EmailMessage;
import com.rays.pro4.Util.EmailUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class VehicleModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_vehicle";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(VehicleBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_vehicle VALUES(?,?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPurchaseBy());
			pstmt.setDate(4, new java.sql.Date(bean.getPurchaseDate().getTime()));
			pstmt.setString(5, bean.getCompany());
			pstmt.setString(6, bean.getManufacturedBy());

			// date of birth caste by sql date

			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void delete(VehicleBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_vehicle WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public VehicleBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_vehicle WHERE ID=?";
		VehicleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new VehicleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setPurchaseBy(rs.getString(3));
				bean.setPurchaseDate(rs.getDate(4));
				bean.setCompany(rs.getString(5));
				;
				bean.setManufacturedBy(rs.getString(6));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public void update(VehicleBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_vehicle SET NAME=?,PURCHASE_BY=?,PURCHASE_DATE=?,COMPANY=?,MANUFACTURED_BY=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getPurchaseBy());
			pstmt.setDate(3, new Date(bean.getPurchaseDate().getTime()));
			pstmt.setString(4, bean.getCompany());
			pstmt.setString(5, bean.getManufacturedBy());
			pstmt.setLong(6, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(VehicleBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(VehicleBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_vehicle where 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getPurchaseBy() != null && bean.getPurchaseBy().length() > 0) {
				sql.append(" AND PURCHASE_BY like '" + bean.getPurchaseBy() + "%'");
			}

			if (bean.getPurchaseDate() != null && bean.getPurchaseDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getPurchaseDate().getTime());
				sql.append(" AND PURCHASE_DATE like '" + d + "%'");
			}
			if (bean.getCompany() != null && bean.getCompany().length() > 0) {
				sql.append(" AND COMPANY like '" + bean.getCompany() + "%'");
			}
			if (bean.getManufacturedBy() != null && bean.getManufacturedBy().length() > 0) {
				sql.append(" AND MANUFACTURED_BY like '" + bean.getManufacturedBy() + "%'");
			}
			/*
			 * if (bean.getDob() != null && bean.getDob().getTime() > 0) { Date d = new
			 * Date(bean.getDob().getTime()); sql.append("AND DOB = '" +
			 * DataUtility.getDateString(d) + "'"); }
			 */
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new VehicleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setPurchaseBy(rs.getString(3));
				;
				bean.setPurchaseDate(rs.getDate(4));
				bean.setCompany(rs.getString(5));
				bean.setManufacturedBy(rs.getString(6));
				;

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_vehicle");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("preload........" + sql);
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				VehicleBean bean = new VehicleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setPurchaseBy(rs.getString(3));
				bean.setPurchaseDate(rs.getDate(4));
				bean.setCompany(rs.getString(5));
				bean.setManufacturedBy(rs.getString(6));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}