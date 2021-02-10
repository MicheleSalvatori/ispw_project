package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Assignment;
import logic.model.Course;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class AssignmentDAO {
	
	private static String noAssigment = "No assignment found";
	private static String rsCourse = "course";
	private static String rsTitle = "title";
	
	private AssignmentDAO() {
		
	}

	public static List<Assignment> getAssignmentsByStudent(String student) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Assignment> assignments;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectAssignmentsByStudent(stmt, student);

			if (!rs.first()) {
				throw new RecordNotFoundException(noAssigment);
				
			} else {
				assignments = getAssignments(rs);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return assignments;
	}
	
	public static List<Assignment> getAssignmentsByProfessor(String professor) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Assignment> assignments;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectAssignmentsByProfessor(stmt, professor);

			if (!rs.first()) {
				throw new RecordNotFoundException(noAssigment);
				
			} else {
				assignments = getAssignments(rs);
			}
			
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return assignments;
	}
	
	public static boolean saveAssignment(Assignment assignment) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String title = assignment.getTitle();
			String text = assignment.getText();
			String course = assignment.getCourse().getAbbreviation();
			Date date = assignment.getDate();
		
			Queries.saveAssignment(stmt, title, text, course, date);
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}

	public static Assignment getAssignment(int id) throws SQLException, RecordNotFoundException {
		Connection conn;
		Statement stmt = null;
		Assignment assignment = null;

		conn = SingletonDB.getDbInstance().getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = Queries.getAssignment(stmt, id);

		if (!rs.first()) {
			throw new RecordNotFoundException(noAssigment);

		} else {
			rs.first();
			int i = rs.getInt("id");
			String t = rs.getString(rsTitle);
			String te = rs.getString("text");
			Date d = rs.getDate("date");
			Course c = CourseDAO.getCourseByAbbrevation(rs.getString(rsCourse));

			assignment = new Assignment(i, c, t, d, te);
		}

		rs.close();
		return assignment;
	}
	
	private static List<Assignment> getAssignments(ResultSet rs) throws SQLException, RecordNotFoundException {
		List<Assignment> assignments = new ArrayList<>();
		rs.first();
		do {
			int id = rs.getInt("id");
			Course c = CourseDAO.getCourseByAbbrevation(rs.getString(rsCourse));
			String t = rs.getString(rsTitle);
			Date d = rs.getDate("date");
			String te = rs.getString("text");
			Assignment assignment = new Assignment(id, c, t, d, te);
			assignments.add(assignment);
		} while (rs.next());
		
		return assignments;
	}
}
