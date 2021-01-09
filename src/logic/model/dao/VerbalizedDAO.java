package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.NullException;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Student;
import logic.model.Verbalized;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class VerbalizedDAO {
	
	private VerbalizedDAO() {
		
	}

	public static List<Verbalized> getVerbalizedExams(String student) throws SQLException, RecordNotFoundException, NullException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Verbalized> verbs;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectVerbalizedExamsByStudent(stmt, student);

			if (!rs.first()) {
				throw new NullException("No verbalized exam found");

			} else {
				verbs = new ArrayList<>();
				rs.first();
				do {
					Student s = StudentDAO.findStudentByUsername(rs.getString("student"));
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					int g = rs.getInt("grade");
					Date d = rs.getDate("date");
					Verbalized verb = new Verbalized(s, c, g, d);
					verbs.add(verb);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return verbs;
	}

	public static boolean insert(Verbalized verb) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Date date = verb.getDate();
			Course course = verb.getCourse();
			int grade = verb.getGrade();
			Student student = verb.getStudent();
			
			Queries.insertVerbalizedExam(stmt, student.getUsername(), course.getAbbrevation(), grade, date);
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}

	public static boolean delete(Verbalized verb) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Course course = verb.getCourse();
			Student student = verb.getStudent();
			
			Queries.deleteVerbalizedExam(stmt, student.getUsername(), course.getAbbrevation());
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}
}
