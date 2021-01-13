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
				throw new RecordNotFoundException("No assignment found");
				
			} else {
				assignments = new ArrayList<>();
				rs.first();
				do {
					int id = rs.getInt("id");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					String t = rs.getString("title");
					Date d = rs.getDate("date");
					String te = rs.getString("text");
					Assignment assignment = new Assignment(id, c, t, d, te);
					assignments.add(assignment);
				} while (rs.next());
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
				throw new RecordNotFoundException("No assignment found");
				
			} else {
				assignments = new ArrayList<>();
				rs.first();
				do {
					int id = rs.getInt("id");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					String t = rs.getString("title");
					Date d = rs.getDate("date");
					String te = rs.getString("text");
					Assignment assignment = new Assignment(id, c, t, d, te);
					assignments.add(assignment);
				} while (rs.next());
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
			String course = assignment.getCourse().getAbbrevation();
			Date date = assignment.getDate();
		
			Queries.saveAssignment(stmt, title, text, course, date);
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}
}