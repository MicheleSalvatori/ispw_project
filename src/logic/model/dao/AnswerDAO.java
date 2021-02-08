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
import logic.model.User;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class AnswerDAO {

	private AnswerDAO() {

	}

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
				throw new RecordNotFoundException("No answer found.");
				
			} else {
				answers = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					String t = rs.getString("text");
					int i = rs.getInt("id");
					User u = UserDAO.findUser(rs.getString("username"));
					Answer a = new Answer(i, t, u, d);
					
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

	public static boolean saveAnswer(Answer answer) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int id = answer.getId();
			String text = answer.getText();
			String username = answer.getUser().getUsername();
			Date date = answer.getDate();
			
			Queries.saveAnswer(stmt, id, username, text, date);
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}
}
