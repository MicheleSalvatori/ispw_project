package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Answer;
import logic.model.Course;
import logic.model.Question;
import logic.model.Student;
import logic.utilities.Queries;
import logic.utilities.Role;
import logic.utilities.SingletonDB;

public class QuestionDAO {

	public static List<Question> getCoursesQuestions(String username, Role role) throws SQLException, RecordNotFoundException {
		Connection conn;
		Statement stmt;
		List<Question> questions;

		conn = (SingletonDB.getDbInstance()).getConnection();
		if (conn == null) {
			questions = null;
		}
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		switch (role) {
		case PROFESSOR:
			rs = Queries.getQuestionsProfessorCourse(stmt, username);
			break;
		case STUDENT:
			rs = Queries.getQuestionsCourse(stmt, username);
			break;
		default:
			break;
		}
		if (!rs.first()) {
			questions = null;
		} else {
			questions = new ArrayList<>();
			rs.first();
			do {
				int id = rs.getInt(1);
				String t = rs.getString("title");
				String te = rs.getString("text");
				Student s = StudentDAO.findStudentByUsername(rs.getString("username"));
				Date d = rs.getDate("date");
				boolean so = rs.getBoolean("solved");
				List<Answer> a = AnswerDAO.getAnswerOf(rs.getInt("id"));
				Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
				Question q = new Question(t, te, c, s, so, d);
				q.setAnswers(a);
				q.setId(id);
				questions.add(q);
			} while (rs.next());
		}
		rs.close();
		return questions;
	}

	public static void saveQuestion(Question question) throws SQLException {
		Connection conn;

		conn = SingletonDB.getDbInstance().getConnection();
		String subject = question.getTitle();
		String text = question.getText();
		String username = question.getStudent().getUsername();
		String course = question.getCourse().getAbbrevation();
		Date date = question.getDate();
		boolean solved = question.isSolved();
		Queries.saveQuestion(conn, subject, text, username, course, date, solved);

	}

	public static void setSolved(int questionID) throws SQLException {
		Connection conn;
		Statement stmt = null;
		conn = SingletonDB.getDbInstance().getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		Queries.setQuestionSolved(stmt, questionID);
	}
}
