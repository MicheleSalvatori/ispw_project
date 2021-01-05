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
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class QuestionDAO {

	public static List<Question> getCoursesQuestions(String username) throws SQLException, RecordNotFoundException {
		Connection conn;
		Statement stmt;
		List<Question> questions;

		conn = (SingletonDB.getDbInstance()).getConnection();
		if (conn == null) {
			questions = null;
		}
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = Queries.getQuestionsCourse(stmt, username);

		if (!rs.first()) {
			questions = null;
		} else {
			questions = new ArrayList<>();
			rs.first();
			do {
				Question q = new Question();
				q.setId(rs.getInt(1));
				q.setTitle(rs.getString("title"));
				q.setText(rs.getString("Text"));
				q.setStudent(StudentDAO.findStudentByUsername(rs.getString("username")));
				q.setDate(rs.getDate("date"));
				q.setSolved(rs.getBoolean("solved"));
				q.setAnswers(AnswerDAO.getAnswerOf(rs.getInt("id")));
				q.setCourse(new Course(rs.getString("course")));
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
}
