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
	
	public static ResultSet selectStudent(Statement stmt, String username, String password) throws SQLException {
		String query = "SELECT * FROM student WHERE username = '" +  username + "' AND password = '" + password + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static int insertStudent(Statement stmt, String username, String password, String name, String surname, String email) throws SQLException {
		String query = "INSERT INTO student (username, password, name, surname, email) VALUES ('" + username + "', '" + password + "', '" + name + "', '" + surname + "', '" + email + "');";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
}
