package logic.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

	public static ResultSet selectProfessor(Statement stmt, String username, String password) throws SQLException {
		String query = "SELECT * FROM professor WHERE username = '" +  username + "' AND password = '" + password + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static int insertUser(Statement stmt, String username, String name, String surname, String email, String password) throws SQLException {
		String query = "INSERT INTO user (username, password, name, surname, email) VALUES ('" + username + "', '" + password + "', '" + name + "', '" + surname + "', '" + email + "');";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
}
