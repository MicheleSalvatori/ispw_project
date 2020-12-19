package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.User;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class UserDAO {
	
	private UserDAO() {}
	
	public static User findUser(String username, String password) throws SQLException, ClassNotFoundException, RecordNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		User userLogged = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			// se conn null throw exception
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectUser(stmt, username, password);
			
			if (!resultSet.first()) {
				userLogged = null;
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				userLogged = new User();
				userLogged.setUsername(resultSet.getString("username"));
				userLogged.setName(resultSet.getString("name"));
				userLogged.setSurname(resultSet.getString("surname"));
				userLogged.setEmail(resultSet.getString("email"));
				userLogged.setPassword(resultSet.getString("password"));
			}
			
			if (userLogged == null) {
				RecordNotFoundException e = new RecordNotFoundException("No Username Found matching with name: " + username);
            	throw e;
			}
			
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		System.out.println("UserDAO -> username = " + userLogged.getUsername());
		return userLogged;
	}
	
	
	public static User addUser(String username, String password, String name, String surname, String email) throws SQLException, ClassNotFoundException, DuplicatedRecordException {
		
		Connection conn = null;
		Statement stmt = null;
		User userRegistered = null;
		ResultSet resultSet = null;
		int result;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			// se conn null throw exception
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			resultSet = Queries.selectUser(stmt, username, password);
			if (!resultSet.first()) {
				userRegistered = null;
			} else {
				// Raise duplicate entry exception
				DuplicatedRecordException e = new DuplicatedRecordException("Duplicated Instance Username. Username " + username + " was already assigned");
            	throw e;   
			}
			
			if (userRegistered == null) {
				// If there are no entry then insert a new User
				result = Queries.insertUser(stmt, username, password, name, surname, email);
				
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
		
		System.out.println("UserDAO -> username = " + username + "\n");
		return userRegistered;
	}
}
