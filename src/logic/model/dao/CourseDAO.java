package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class CourseDAO {
	private static String noCourse = "No course found";
	
	private CourseDAO() {
		
	}

	public static Course getCourseByAbbrevation(String abbrevation) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		Course course;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.findCourseByAbbr(stmt, abbrevation);

			if (!rs.first()) {
				throw new RecordNotFoundException(noCourse);
				
			} else {
				rs.first();
				course = getCourse(rs);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return course;
	}


	public static int getProfessorCourseNumberOf(String username) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		int number;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;

			rs = Queries.countCoursesProf(stmt, username);
			number = getNumber(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return number;
	}
	
	public static int getStudentCourseNumberOf(String username) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		int number;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			rs = Queries.countCourses(stmt, username);
			number = getNumber(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return number;
	}
	
	private static int getNumber(ResultSet rs) throws SQLException {
		int number;
		if (!rs.first()) {
			number = 0;
		} else {
			rs.first();
			number = rs.getInt(1);
		}
		rs.close();
		
		return number;
	}

	public static List<Course> getAllCourses() throws SQLException, RecordNotFoundException {

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
			courses = getCourses(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}

	public static List<Course> getAvailableCourses(String student) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectAvailableCourses(stmt, student);
			courses = getCourses(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}
	
	public static List<Course> getNotVerbalizedCourses(String student) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = Queries.selectNotVerbalizedCourses(stmt, student);
			courses = getCourses(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}

	public static List<Course> getStudentCourses(String student) throws SQLException, RecordNotFoundException {

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
			courses = getCourses(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}

	public static List<Course> getProfessorCourses(String professor) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;

		try {
			conn = SingletonDB.getDbInstance().getConnection();

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectCoursesByProfessor(stmt, professor);
			courses = getCourses(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}

	public static List<Course> getStudentCoursesByRequest(String student) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectCoursesRequestedByStudent(stmt, student);
			courses = getCourses(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
	}
	
	private static List<Course> getCourses(ResultSet rs) throws SQLException, RecordNotFoundException {
		List<Course> courses;
		if (!rs.first()) {
			throw new RecordNotFoundException(noCourse);
			
		} else {
			courses = new ArrayList<>();
			rs.first();
			do {
				Course course = getCourse(rs);
				courses.add(course);
			} while (rs.next());
		}
		
		rs.close();
		
		return courses;
	}

	public static void addCourse(Course course) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
			Queries.insertCourse(stmt, course);
			
		} finally {
			if(stmt != null)
				stmt.close();
		}	
		
	}
	
	private static Course getCourse(ResultSet rs) throws SQLException {
		String n = rs.getString("name");
		String a = rs.getString("abbrevation");
		String y = rs.getString("year");
		String s = rs.getString("semester");
		String c = rs.getString("credits");
		String p = rs.getString("prerequisites");
		String g = rs.getString("goal");
		String r = rs.getString("reception");
		
		List<String> info = new ArrayList<>();
		info.add(n);
		info.add(a);
		info.add(y);
		info.add(s);
		info.add(c);
		info.add(p);
		info.add(g);
		info.add(r);
		
		return new Course(info);
	}
}
