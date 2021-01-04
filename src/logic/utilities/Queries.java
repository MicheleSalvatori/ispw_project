package logic.utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

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

	public static ResultSet getQuestionsCourse(Statement stmt, String username) throws SQLException {
		String query = "SELECT * FROM question where course in (SELECT course FROM follow WHERE student = '"+username+"') ORDER BY id DESC;";
		System.out.println(query);
		return stmt.executeQuery(query);
	}

	public static ResultSet findCourseOfStudent(Statement stmt, String username) throws SQLException {
		String sql = "SELECT course from follow where student ='"+username+"';";
		System.out.println(sql);
		return stmt.executeQuery(sql);
	}

	public static ResultSet findCourseByAbbr(Statement stmt, String abbrevation) throws SQLException {
		String sql = "SELECT * from course where abbrevation ='"+abbrevation+"';";
		return stmt.executeQuery(sql);
	}

	public static ResultSet findStudent(Statement stmt, String username) throws SQLException {
		String sql = "SELECT * from student where username = '"+username+"';";
		return stmt.executeQuery(sql);
	}

	public static ResultSet getQuestionByUsername(Statement stmt, String username) throws SQLException {
		String sql = "SELECT * from question where username = '"+username+"';";
		return stmt.executeQuery(sql);
	}

	public static void saveQuestion(Connection conn, String subject, String text, String username, String course, Date date, boolean solved) throws SQLException {
		String sql = "INSERT INTO question VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setNull(1, java.sql.Types.INTEGER);
		stmt.setString(2, subject);
		stmt.setString(3, text);
		stmt.setString(4, username);
		stmt.setString(5, course);
		stmt.setBoolean(6, solved);
		stmt.setDate(7, date);
		
		stmt.executeUpdate();	
		if (stmt != null) {
			stmt.close();
		}
	}

	public static ResultSet findAnswerByIDQuestion(Statement stmt, int id) throws SQLException {
		String sql = "SELECT * from answer where id = '"+id+"';";
		System.out.println(sql);
		return stmt.executeQuery(sql);
	}
	
	public static void saveAnswer(Connection conn, int id, String username, String text, Date date) throws SQLException {
		String sql = "INSERT INTO answer VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.setString(2, username);
		stmt.setString(3, text);
		stmt.setDate(4, date);
		stmt.executeUpdate();	
		if (stmt != null) {
			stmt.close();
		}
	}

	public static ResultSet countCourses(Statement stmt, String username) throws SQLException {
		String sql  = "SELECT count(distinct course) from follow where student ='"+username+"';"; 
		return stmt.executeQuery(sql);
	}

}
