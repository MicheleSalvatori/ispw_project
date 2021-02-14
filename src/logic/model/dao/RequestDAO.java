package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Request;
import logic.model.Student;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class RequestDAO {
	private static String noRequest = "No request found";
	
	private RequestDAO() {
		
	}
	
	public static Request getRequest(String student, String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		Request request;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequest(stmt, student, course);

			if (!rs.first()) {
				throw new RecordNotFoundException(noRequest);
				
			} else {
				rs.first();
				request = getRequest(rs);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return request;
	}
	
	public static List<Request> getRequestsByProfessor(String professor) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Request> requests;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequestsByProfessor(stmt, professor);
			requests = getRequests(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return requests;
	}
	
	public static List<Request> getRequestsByProfessorAndCourse(String professor, String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Request> requests;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequestsByProfessorAndCourse(stmt, professor, course);
			requests = getRequests(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return requests;
	}
	
	public static void insertRequest(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		conn = (SingletonDB.getDbInstance()).getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		Student student = request.getStudent();
		Course course = request.getCourse();
			
		Queries.insertRequest(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	public static void deleteRequest(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;

		conn = (SingletonDB.getDbInstance()).getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		Student student = request.getStudent();
		Course course = request.getCourse();
			
		Queries.deleteRequest(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	public static void insertFollow(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		conn = (SingletonDB.getDbInstance()).getConnection();
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		Student student = request.getStudent();
		Course course = request.getCourse();
		
		Queries.insertFollow(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	public static void deleteFollow(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;

		conn = (SingletonDB.getDbInstance()).getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		Student student = request.getStudent();
		Course course = request.getCourse();
			
		Queries.deleteFollow(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	private static Request getRequest(ResultSet rs) throws SQLException, RecordNotFoundException {
		Student s = StudentDAO.findStudentByUsername(rs.getString("student"));
		Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
		return new Request(s, c);
	}
	
	private static List<Request> getRequests(ResultSet rs) throws SQLException, RecordNotFoundException {
		List<Request> requests;
		
		if (!rs.first()) {
			throw new RecordNotFoundException(noRequest);

		} else {
			requests = new ArrayList<>();
			rs.first();
			do {
				Request request = getRequest(rs);
				requests.add(request);
			} while (rs.next());
		}
		rs.close();
		
		return requests;
	}
}
