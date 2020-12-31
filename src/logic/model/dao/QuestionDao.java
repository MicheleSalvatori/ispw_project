package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicBorders.FieldBorder;

import com.mysql.jdbc.PreparedStatement;

import logic.bean.QuestionBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Question;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class QuestionDao {

	private QuestionDao() {
	}

	public static List<Question> getCoursesQuestions(String username) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Question> questions;
		ResultSet resultSet = null;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				System.out.println("Conn == null");
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.getQuestionsCourse(stmt, username);

			if (!resultSet.first()) {
				System.out.println("resultSet is null");
				questions = null;
			} else {
				resultSet.first(); // Torniamo alla prima riga
				questions = new ArrayList<>();
				do {
					Question q = new Question();
					q.setId(resultSet.getInt("id"));
					q.setCourse(CourseDao.getCourseByAbbrevation(resultSet.getString("course")));
					q.setTitle(resultSet.getString("title"));
					q.setText(resultSet.getString("text"));
					System.out.println("USERNAME DOMANDA #"+resultSet.getInt("id")+" "+resultSet.getString("username"));
					q.setStudent(StudentDAO.findStudentByUsername(resultSet.getString("username")));
					questions.add(q);
				} while (resultSet.next());
			}

			resultSet.close();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return questions;
	}

	public static List<Question> getStudentQuestions(String username) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Question> questions;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.getQuestionByUsername(stmt, username);

			if (!rs.first()) {
				questions = null;
			} else {
				rs.first();
				questions = new ArrayList<>();
				do {
					Question q = new Question();
					q.setId(rs.getInt("id"));
					q.setCourse(CourseDao.getCourseByAbbrevation(rs.getString("course")));
					q.setTitle(rs.getString("title"));
					q.setText(rs.getString("text"));
					q.setStudent(StudentDAO.findStudentByUsername(rs.getString("username")));
					questions.add(q);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return questions;
	}

	public static boolean saveQuestion(QuestionBean questionBean){
		Connection conn = null;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			String subject = questionBean.getTitle();
			String text = questionBean.getText();
			String username = questionBean.getStudent().getUsername();
			String course = "ISPW"; //TODO getCourseByName
			Queries.saveQuestion(conn, subject, text, username, course);
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
