package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.model.Course;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class CourseDAO {

	private CourseDAO() {
		
	}
	
	public static Course getCourseByAbbrevation(String abbrevation) throws SQLException {
		
		Statement stmt = null;
		Connection conn = null;
		Course course;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectCourse(stmt, abbrevation);

			if (!rs.first()) {
				course = null;
			} else {
				rs.first();
				String n = rs.getString("name");
				String a = rs.getString("abbrevation");
				course = new Course(n, a);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return course;
	}
	
	public static List<Course> getAllCourses() throws SQLException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectAllCourses(stmt);

			if (!rs.first()) {
				courses = null;
			} else {
				courses = new ArrayList<>();
				rs.first();
				do {
					String n = rs.getString("name");
					String a = rs.getString("abbrevation");
					Course course = new Course(n, a);
					courses.add(course);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}
	
	public static List<Course> getProfessorCourses(String professor) throws SQLException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectCoursesByProfessor(stmt, professor);

			if (!rs.first()) {
				courses = null;
			} else {
				courses = new ArrayList<>();
				rs.first();
				do {
					String n = rs.getString("name");
					String a = rs.getString("abbrevation");
					Course course = new Course(n, a);
					courses.add(course);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}
	
	public static List<Course> getStudentCourses(String student) throws SQLException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectCoursesByStudent(stmt, student);

			if (!rs.first()) {
				courses = null;
			} else {
				courses = new ArrayList<>();
				rs.first();
				do {
					String n = rs.getString("name");
					String a = rs.getString("abbrevation");
					Course course = new Course(n, a);
					courses.add(course);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}
}
