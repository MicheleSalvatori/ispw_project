package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Question;
import logic.model.Student;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class QuestionDAO {

	private QuestionDAO() {

	}

	public static List<Question> getStudentCoursesQuestions(String username)
			throws SQLException, RecordNotFoundException {
		Connection conn;
		Statement stmt;
		List<Question> questions;

		conn = (SingletonDB.getDbInstance()).getConnection();
		if (conn == null) {
			throw new SQLException();
		}

		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;

		rs = Queries.getQuestionsCourse(stmt, username);

		if (!rs.first()) {
			throw new RecordNotFoundException("Question not found");

		} else {
			questions = new ArrayList<>();
			rs.first();
			do {
				int id = rs.getInt("id");
				String t = rs.getString("title");
				String te = rs.getString("text");
				Student s = StudentDAO.findStudentByUsername(rs.getString("username"));
				Date d = rs.getDate("date");
				boolean so = rs.getBoolean("solved");

				Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
				Question q = new Question(t, te, c, s, so, d);

				q.setId(id);
				questions.add(q);
			} while (rs.next());
		}

		rs.close();
		return questions;
	}

	public static List<Question> getProfessorCoursesQuestions(String username)
			throws SQLException, RecordNotFoundException {
		Connection conn;
		Statement stmt;
		List<Question> questions;

		conn = (SingletonDB.getDbInstance()).getConnection();
		if (conn == null) {
			throw new SQLException();
		}

		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;

		rs = Queries.getQuestionsProfessorCourse(stmt, username);

		if (!rs.first()) {
			throw new RecordNotFoundException("No question found");

		} else {
			questions = new ArrayList<>();
			rs.first();
			do {
				int id = rs.getInt("id");
				String t = rs.getString("title");
				String te = rs.getString("text");
				Student s = StudentDAO.findStudentByUsername(rs.getString("username"));
				Date d = rs.getDate("date");
				boolean so = rs.getBoolean("solved");
				Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));

				Question q = new Question(t, te, c, s, so, d);
				q.setId(id);

				questions.add(q);
			} while (rs.next());
		}

		rs.close();
		return questions;
	}

	public static boolean saveQuestion(Question question) throws SQLException {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String title = question.getTitle();
			String text = question.getText();
			String username = question.getStudent().getUsername();
			String course = question.getCourse().getAbbreviation();
			Date date = question.getDate();
			boolean solved = question.isSolved();

			Queries.saveQuestion(stmt, title, text, username, course, date, solved);

		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public static void setSolved(int questionID) throws SQLException {

		Connection conn;
		Statement stmt = null;

		conn = SingletonDB.getDbInstance().getConnection();
		if (conn == null) {
			throw new SQLException();
		}

		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		Queries.setQuestionSolved(stmt, questionID);
	}

	public static Question getQuestion(int id) throws SQLException, RecordNotFoundException {
		Connection conn;
		Statement stmt = null;
		Question question = null;

		conn = SingletonDB.getDbInstance().getConnection();
		if (conn == null) {
			throw new SQLException();
		}

		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;

		rs = Queries.getQuestion(stmt, id);

		if (!rs.first()) {
			throw new RecordNotFoundException("No question found");

		} else {
			rs.first();
			int i = rs.getInt("id");
			String t = rs.getString("title");
			String te = rs.getString("text");
			Student s = StudentDAO.findStudentByUsername(rs.getString("username"));
			Date d = rs.getDate("date");
			boolean so = rs.getBoolean("solved");
			Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));

			question = new Question(t, te, c, s, so, d);
			question.setId(i);
		}

		rs.close();
		return question;
	}

	public static int countQuestionsByProfessor(String professor) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		int tot;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.countQuestionsByProfessor(stmt, professor);

			if (!rs.first()) {
				throw new RecordNotFoundException("No verbalized exam found");

			} else {
				rs.first();
				tot = rs.getInt(1);
			}
			rs.close();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return tot;
	}

	public static void deleteQuestion(int id) throws SQLException {
		Statement stmt = null;
		Connection conn = null;

		conn = SingletonDB.getDbInstance().getConnection();
		if (conn == null) {
			throw new SQLException();
		}
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		Queries.deleteQuestion(stmt, id);

	}

}
