package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.Student;
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
				studentLogged = null;
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				studentLogged = new Student();
				studentLogged.setUsername(resultSet.getString("username"));
				studentLogged.setName(resultSet.getString("name"));
				studentLogged.setSurname(resultSet.getString("surname"));
				studentLogged.setEmail(resultSet.getString("email"));
				studentLogged.setPassword(resultSet.getString("password"));
			}
			
			if (studentLogged == null) {
				RecordNotFoundException e = new RecordNotFoundException("No Username Found matching with name: " + username);
            	throw e;
			}
			
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		System.out.println("StudentDAO -> username = " + studentLogged.getUsername());
		return studentLogged;
	}

	public static void addStudent(String username, String password, String name, String surname, String email) throws SQLException, DuplicatedRecordException {
		
		Connection conn = null;
		Statement stmt = null;
		Student userRegistered = null;
		ResultSet resultSet = null;
		int result;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			resultSet = Queries.selectStudent(stmt, username, password);
			if (!resultSet.first()) {
				userRegistered = null;
			} else {
				// Raise duplicate entry exception
				DuplicatedRecordException e = new DuplicatedRecordException("Duplicated Instance Username. Username " + username + " was already assigned");
            	throw e;   
			}
			
			if (userRegistered == null) {
				// If there are no entry then insert a new User
				result = Queries.insertStudent(stmt, username, password, name, surname, email);
				
				if (result == 0) {
					System.out.println("Insert error");
				}
				else {
					System.out.println("Insert done");
				}
			}
			
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		System.out.println("StudentDAO: new student entry created with username = " + username + "\n");
	}
}