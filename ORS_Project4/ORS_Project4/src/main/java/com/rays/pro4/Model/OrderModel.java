package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class OrderModel {


		public static Integer nextPK() throws ApplicationException {

			Connection conn = null;
			int pk = 0;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM st_order");
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					pk = rs.getInt(1);
				}
				rs.close();
			} catch (Exception e) {

				throw new ApplicationException("Exception : in getting next pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			return pk + 1;
		}

		/**
		 * 
		 * Add a User
		 *
		 */
		public static long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {

			Connection conn = null;
			int pk = 0;

			// OrderModelBean beanExists = findByLogin(bean.);

			/*
			 * if (beanExists != null) {
			 * 
			 * throw new DuplicateRecordException("LogIn ID is already Exists"); }
			 */
			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO st_order VALUES(?, ?, ? , ?)");
				pstmt.setLong(1, pk);
				pstmt.setString(2, bean.getShop());
				pstmt.setString(3, bean.getType());
				pstmt.setInt(4, bean.getPrice());

				pstmt.executeUpdate();

				conn.commit();
				pstmt.close();
			} catch (Exception e) {
				// .error("Database Exception....", e);

				try {
					conn.rollback();
				} catch (Exception e1) {
					throw new ApplicationException("Exception : add rollback=" + e1.getMessage());
				}

				throw new ApplicationException("Exception : in adding user=" + e.getMessage());

			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			// log.debug("Model Add Ended");
			return pk;
		}

		/**
		 * 
		 * Update a user
		 *
		 */
		public void update(OrderBean bean) throws ApplicationException, DuplicateRecordException {
			// log.debug("Model Update Started");
			Connection conn = null;

			// UserBean beanExists = findByLogin(bean.getLogIn());

			/*
			 * if (beanExists != null && !(beanExists.getId() == bean.getId())) { //
			 * System.out.println("id " + beanExists.getId()); throw new
			 * DuplicateRecordException("Login Already Exists"); }
			 */

			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("UPDATE st_order SET SHOP=?, TYPE=?, PRICE=? WHERE ID=?");
				pstmt.setString(1, bean.getShop());
				pstmt.setString(2, bean.getType());
				pstmt.setInt(3, bean.getPrice());
				pstmt.setLong(4, bean.getId());

				pstmt.executeUpdate();

				conn.commit();
				pstmt.close();

			} catch (Exception e) {
				// log.error("Database Exception...", e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					throw new ApplicationException("Exception : update rollback" + e1.getMessage());
				}
				throw new ApplicationException("Exception : in updating user");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

		}

		/**
		 * 
		 * Delete a User
		 *
		 */
		public static void delete(OrderBean bean) throws ApplicationException {

			Connection conn = null;

			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_order WHERE id=?");
				pstmt.setLong(1, bean.getId());

				pstmt.executeUpdate();

				conn.commit();
				pstmt.close();
			} catch (Exception e) {

				try {
					conn.rollback();
				} catch (Exception e1) {
					throw new ApplicationException("Exception : delete rollback" + e1.getMessage());
				}
				throw new ApplicationException("Exception : in deleting user");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

		}

		/**
		 * 
		 * Find User by Login
		 *
		 *//*
			 * public static OrderBean findByLogin(String login) throws ApplicationException
			 * {
			 * 
			 * StringBuffer sql = new StringBuffer("SELECT * FROM st_order WHERE LOGIN=?");
			 * 
			 * OrderBean bean = null; Connection conn = null;
			 * 
			 * try { conn = JDBCDataSource.getConnection(); PreparedStatement pstmt =
			 * conn.prepareStatement(sql.toString());
			 * 
			 * pstmt.setString(1, login); ResultSet rs = pstmt.executeQuery();
			 * 
			 * while (rs.next()) { bean = new OrderBean();
			 * 
			 * bean.setId(rs.getLong(1)); bea } rs.close(); } catch (Exception e) {
			 * log.error("Database Exception...", e); throw new
			 * ApplicationException("Exception : In getting user by Login"); } finally {
			 * JDBCDataSource.closeConnection(conn); }
			 * log.debug("Model Find By Login Ended"); return bean;
			 */

		/**
		 * 
		 * Find User by PK
		 *
		 */
		public OrderBean findByPK(long pk) throws ApplicationException {

			Connection conn = null;
			OrderBean bean = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select * from st_order where id = ?");
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					bean = new OrderBean();

					bean.setId(rs.getInt(1));
					bean.setShop(rs.getString(2));
					bean.setType(rs.getString(3));
					bean.setPrice(rs.getInt(4));

				}
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
				throw new ApplicationException("Exception : In getting user by PK");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			return bean;
		}

		/**
		 * 
		 * Search User with pagination
		 *
		 *
		 */
		public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {
			System.out.println("List search(UserBean bean, int pageNo, int pageSize)");

			StringBuffer sql = new StringBuffer("SELECT * FROM st_order WHERE 1=1");

			if (bean != null) {
				System.out.println(bean.getId());
				if (bean.getId() > 0) {
					sql.append(" AND ID Like '" + bean.getId() + "%'");
				}
				if (bean.getPrice() > 0) {
					sql.append("AND ID Like '" + bean.getPrice() + "%'");
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;

				sql.append(" LIMIT " + pageNo + ", " + pageSize);
			}

			System.out.println(sql.toString());

			ArrayList list = new ArrayList();
			Connection conn = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());

				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new OrderBean();

					bean.setId(rs.getLong(1));
					bean.setShop(rs.getString(2));
					bean.setType(rs.getString(3));
					bean.setPrice(rs.getInt(4));
					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {

				throw new ApplicationException("Exception : in getting user list");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			return list;

		}
	}