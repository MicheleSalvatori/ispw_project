package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.Student;
import logic.model.User;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class StudentDAO {
	
	private StudentDAO() {}
	
	public static Student findStudent(String username, String password) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		Student studentLogged = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectStudent(stmt, username, password);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException("No Username Found matching with name: " + username);
				
			}else {
				resultSet.first();				// mi riposiziono alla prima riga
				String u = resultSet.getString("username");
				String n = resultSet.getString("name");
				String s = resultSet.getString("surname");
				String e = resultSet.getString("email");
				String p = resultSet.getString("password");
				studentLogged = new Student(u, p, n, s, e);
			}
			
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		System.out.println("StudentDAO -> username = " + studentLogged.getUsername());
		return studentLogged;
	}

	public static void addStudent(User user) throws SQLException, DuplicatedRecordException {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = Queries.selectStudentByUsername(stmt, user.getUsername());
			if (rs.first()) {
				// Raise duplicate entry exception
				throw new DuplicatedRecordException("Username '" + user.getUsername() + "' already in use.");   
			}
			
			rs = Queries.selectUserByEmail(stmt, user.getEmail());
			if (rs.first()) {
				// Raise duplicate entry exception
				throw new DuplicatedRecordException("Email '" + user.getEmail() + "' already in use.");
			}
			
			// If there are no entry then insert a new User
			result = Queries.insertRole(stmt, user.getUsername());
			if (result == 0) {
				System.out.println("Insert role error");	// TODO EXCEPTION
				return;
			}
				
			result = Queries.insertStudent(stmt, user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail());
			if (result == 0) {
				System.out.println("Insert studenterror");	// TODO EXCEPTION
				return;
			}
			
			rs.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		System.out.println("StudentDAO: new student entry created with username = " + user.getUsername() + "\n");
	}
	
	
	public static void changePassword(User user) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = Queries.selectStudentByUsername(stmt, user.getUsername());
			if (!rs.first()) {
				throw new RecordNotFoundException("No Username Found matching with name: " + user.getUsername());	
			} 
			
			Queries.updateStudentPassword(stmt, user.getUsername(), user.getPassword());
			
			rs.close();
			
		} finally {
			if(stmt != null) stmt.close();
		}
	}
	
	public static Student findStudentByUsername(String username) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		Student student = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			resultSet = Queries.selectStudentByUsername(stmt, username);
			if (!resultSet.first()) {
				throw new RecordNotFoundException("No student found.");
            	
			} else {
				resultSet.first();
				String u = resultSet.getString("username");
				String n = resultSet.getString("name");
				String s = resultSet.getString("surname");
				String e = resultSet.getString("email");
				String p = resultSet.getString("password");
				student = new Student(u, p, n, s, e);
			}
			
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		return student;
	}

}