package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.LessonBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class LessonModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_lesson";
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

	 public long add(LessonBean bean) throws ApplicationException, DuplicateRecordException {
      		

		String sql = "INSERT INTO st_lesson VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAuthor());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getSubject());
		

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

	public void delete(LessonBean bean) throws ApplicationException {
	
		String sql = "DELETE FROM st_lesson WHERE ID=?";
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

	
	public LessonBean findByPK(long pk) throws ApplicationException {
		
		String sql = "SELECT * FROM st_lesson WHERE ID=?";
		LessonBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new LessonBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAuthor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setSubject(rs.getString(5));

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

	public void update(LessonBean bean) throws ApplicationException, DuplicateRecordException {
		
		String sql = "UPDATE st_lesson SET NAME=?,AUTHOR=?,DOB=?, SUBJECT=? WHERE ID=?";
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAuthor());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getSubject());
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

	public List search(LessonBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(LessonBean bean, int pageNo, int pageSize) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM st_lesson where 1=1");
		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			
			if (bean.getAuthor() != null && bean.getAuthor().length() > 0) {
				sql.append(" AND AUTHOR like '" + bean.getAuthor() + "%'");
			}
			
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
		  		Date d = new java.sql.Date(bean.getDob().getTime());
				sql.append(" AND DOB like '"+d+"%'");
			}
			
			if (bean.getSubject() != null && bean.getSubject().length() > 0) {
				sql.append(" AND SUBJECT like '" + bean.getSubject() + "%'");
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
				bean = new LessonBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAuthor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setSubject(rs.getString(5));
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
		StringBuffer sql = new StringBuffer("select * from st_lesson");
		
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
				LessonBean bean = new LessonBean() ;
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAuthor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setSubject(rs.getString(5));

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


