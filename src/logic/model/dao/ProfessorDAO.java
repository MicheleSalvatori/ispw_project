package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.Professor;
import logic.model.User;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class ProfessorDAO {
	private static String noUsername = "No Username Found matching with name: ";
	
	private ProfessorDAO() {}
	
	public static Professor findProfessor(String username, String password) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		Professor professorLogged = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectProfessor(stmt, username, password);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException(noUsername + username);
				
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				professorLogged = setupProfessor(resultSet);
			}
			
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		return professorLogged;
	}
	
	
	public static Professor findProfessor(String username) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		Professor professor = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectProfessorByUsername(stmt, username);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException(noUsername+ username);
				
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				professor = setupProfessor(resultSet);
			}
			
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		return professor;
	}
	
	
	public static List<Professor> getCourseProfessors(String course) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		List<Professor> professors;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectProfessorsByCourse(stmt, course);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException("No Professor found.");
				
			}else {
				professors = new ArrayList<>();
				resultSet.first();
				do {
					Professor prof = setupProfessor(resultSet);
					professors.add(prof);
				} while(resultSet.next());
			}
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		return professors;
	}
	
	public static void addProfessor(User user) throws SQLException, DuplicatedRecordException {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = Queries.selectProfessorByUsername(stmt, user.getUsername());
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
			result = Queries.insertRole(stmt, user.getUsername(), "professor");
			if (result == 0) {
				throw new SQLException();
			}
				
			result = Queries.insertProfessor(stmt, user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail());
			if (result == 0) {
				throw new SQLException();
			}
			
			rs.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}	
	}
	
	public static void changePasswordProfessor(User user) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = Queries.selectProfessorByUsername(stmt, user.getUsername());
			if (!rs.first()) {
				throw new RecordNotFoundException(noUsername + user.getUsername());
			}
			
			Queries.updateProfessorPassword(stmt, user.getUsername(), user.getPassword());
			
			rs.close();
			
		} finally {
			if(stmt != null) stmt.close();
		}
	}

	
	private static Professor setupProfessor(ResultSet resultSet) throws SQLException {
		String u = resultSet.getString("username");
		String p = resultSet.getString("password");
		String n = resultSet.getString("name");
		String s = resultSet.getString("surname");
		String e = resultSet.getString("email");
		return new Professor(u, p, n, s, e);
  }


	public static void deleteProfessor(User user) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = Queries.selectProfessorByUsername(stmt, user.getUsername());
			if (!rs.first()) {
				throw new RecordNotFoundException("No Username Found matching with name: " + user.getUsername());	
			} 
			
			Queries.deleteProfessor(stmt, user.getUsername());
			
			RoleDAO.deleteRole(user);
			
			rs.close();
			
		} finally {
			if(stmt != null) stmt.close();
		}
	}
}
