package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Communication;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class CommunicationDAO {

	public static List<Communication> getCommunications() throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Communication> communications;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.getComunication(stmt);

			if (!rs.first()) {
				throw new RecordNotFoundException("No course found");

			} else {
				rs.first();
				communications = new ArrayList<>();
				do {
					Communication c = new Communication();
					c.setDate(rs.getDate("date"));
					c.setId(rs.getInt("id"));
					c.setText(rs.getString("text"));
					c.setTitle(rs.getString("title"));
					communications.add(c);
				} while (rs.next());
			}
			rs.close();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return communications;
	}

	public void postCommunication() {
	}

	public static void saveCommunication(Communication communication) throws SQLException{
		Connection conn = null;
		Statement stmt = null;

			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String title = communication.getTitle();
			String text = communication.getText();
			Date date = communication.getDate();

			Queries.saveCommunication(stmt, title, text, date);

	}

}
