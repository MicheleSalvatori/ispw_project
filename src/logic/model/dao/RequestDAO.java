package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.bean.RequestBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Request;
import logic.model.Student;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class RequestDAO {

	private RequestDAO() {
		
	}
	
	public static List<Request> getRequestsByProfessor(String professor) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Request> requests;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequestsByProfessor(stmt, professor);

			if (!rs.first()) {
				requests = null;
			} else {
				requests = new ArrayList<>();
				rs.first();
				do {
					Student student = StudentDAO.findStudentByUsername(rs.getString("student"));
					Course course = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Request request = new Request(student, course);
					requests.add(request);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return requests;
	}
	
	public static boolean insertRequest(RequestBean requestBean) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Student student = requestBean.getStudent();
			Course course = requestBean.getCourse();
			
			Queries.insertRequest(stmt, student.getUsername(), course.getAbbrevation());
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public static boolean deleteRequest(RequestBean requestBean) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Student student = requestBean.getStudent();
			Course course = requestBean.getCourse();
			
			Queries.deleteRequest(stmt, student.getUsername(), course.getAbbrevation());
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public static boolean insertFollow(RequestBean requestBean) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Student student = requestBean.getStudent();
			Course course = requestBean.getCourse();
			
			Queries.insertFollow(stmt, student.getUsername(), course.getAbbrevation());
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public static boolean deleteFollow(RequestBean requestBean) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Student student = requestBean.getStudent();
			Course course = requestBean.getCourse();
			
			Queries.deleteFollow(stmt, student.getUsername(), course.getAbbrevation());
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
