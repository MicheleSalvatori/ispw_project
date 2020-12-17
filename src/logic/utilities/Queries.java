package logic.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

	public static ResultSet selectUser(Statement stmt, String username, String password) throws SQLException {
		String query = "Select * from user where username = username and password = password";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
}
