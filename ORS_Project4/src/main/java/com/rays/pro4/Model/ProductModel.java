package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class ProductModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_product";
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

	 public long add(ProductBean bean) throws ApplicationException, DuplicateRecordException {
      		

		String sql = "INSERT INTO st_product VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(4, bean.getAmount());
			pstmt.setString(4, bean.getStatus());
			pstmt.setDate(3, new java.sql.Date(bean.getPurchaseDate().getTime()));
		
		

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

	public void delete(ProductBean bean) throws ApplicationException {
	
		String sql = "DELETE FROM st_product WHERE ID=?";
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

	
	public ProductBean findByPK(long pk) throws ApplicationException {
		
		String sql = "SELECT * FROM st_product WHERE ID=?";
		ProductBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAmount(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setPurchaseDate(rs.getDate(5));
				

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

	public void update(ProductBean bean) throws ApplicationException, DuplicateRecordException {
		
		String sql = "UPDATE st_product SET NAME=?, AMOUNT=?, STATUS=?, DATE =? WHERE ID=?";
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
		
			
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAmount());
			pstmt.setString(3, bean.getStatus());
			pstmt.setDate(4, new java.sql.Date(bean.getPurchaseDate().getTime()));
			pstmt.setLong(5, bean.getId());
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

	public List search(ProductBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(ProductBean bean, int pageNo, int pageSize) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM st_product where 1=1");
		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			
			if (bean.getAmount() != null && bean.getAmount().length() > 0) {
				sql.append(" AND AMOUNT like '" + bean.getAmount() + "%'");
			}
			
			if (bean.getPurchaseDate() != null && bean.getPurchaseDate().getTime() > 0) {
		  		Date d = new java.sql.Date(bean.getPurchaseDate().getTime());
				sql.append(" AND DATE like '"+d+"%'");
			}
			
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND STATUS like '" + bean.getStatus() + "%'");
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
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAmount(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setPurchaseDate(rs.getDate(5));
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
		StringBuffer sql = new StringBuffer("select * from st_product");
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("preload........"+sql);
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean bean = new ProductBean() ;
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAmount(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setPurchaseDate(rs.getDate(5));

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
