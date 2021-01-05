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
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class AnswerDAO {

	public static List<Answer> getAnswerOf(int id) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Answer> answers;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.findAnswerByIDQuestion(stmt, id);

			if (!rs.first()) {
				answers = null;
			} else {
				answers = new ArrayList<>();
				rs.first();
				do {
					Answer a = new Answer();
					a.setDate(rs.getDate("date"));
					a.setText(rs.getString("text"));
					a.setStudent(StudentDAO.findStudentByUsername(rs.getString("username")));
					a.setId(rs.getInt("id"));
					answers.add(a);
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return answers;
	}

	public static void saveAnswer(Answer answer) throws SQLException {
		Connection conn = null;

			conn = SingletonDB.getDbInstance().getConnection();
			int id = answer.getId();
			String text = answer.getText();
			String username = answer.getStudent().getUsername();
			Date date = answer.getDate();
			Queries.saveAnswer(conn, id, username, text, date);
	}
}
