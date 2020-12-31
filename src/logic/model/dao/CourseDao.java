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

public class CourseDao {

	private CourseDao() {
	}

	// Non ancora utilizza ma serve per il profilo
	public static List<Course> getCourseOfStudent(String username) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		List<Course> courses;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.findCourseOfStudent(stmt, username);

			if (!rs.first()) {
				courses = null;
			} else {
				courses = new ArrayList<>();
				rs.first();
				do {
					String abbrevation = rs.getString("course");
					String name = rs.getString("nameCourse");
					Course c = new Course(name, abbrevation);
					courses.add(c);
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return courses;
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
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.findCourseByAbbr(stmt, abbrevation);
			
			if (!rs.first()) {
				course = null;
			}else {
				rs.first();
				String name = rs.getString("name");
				String abbr = rs.getString("abbrevation");
				course = new Course(name, abbr);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return course;
	}
}
