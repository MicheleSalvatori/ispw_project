package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Professor;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class ProfessorDAO {
	
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
				professorLogged = null;
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				professorLogged = new Professor();
				professorLogged.setUsername(resultSet.getString("username"));
				professorLogged.setName(resultSet.getString("name"));
				professorLogged.setSurname(resultSet.getString("surname"));
				professorLogged.setEmail(resultSet.getString("email"));
				professorLogged.setPassword(resultSet.getString("password"));
			}
			
			if (professorLogged == null) {
				RecordNotFoundException e = new RecordNotFoundException("No Username Found matching with name: " + username);
            	throw e;
			}
			
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		System.out.println("ProfessorDAO -> username = " + professorLogged.getUsername());
		return professorLogged;
	}
	
	
	public static Professor findProfessor(String username) throws SQLException {
		
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
				professor = null;
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				professor = new Professor();
				professor.setUsername(resultSet.getString("username"));
				professor.setName(resultSet.getString("name"));
				professor.setSurname(resultSet.getString("surname"));
				professor.setEmail(resultSet.getString("email"));
				professor.setPassword(resultSet.getString("password"));
			}
			
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		System.out.println("ProfessorDAO -> username = " + professor.getUsername());
		return professor;
	}
	
	
	public static List<Professor> getCourseProfessors(String course) throws SQLException {
		
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
				professors = null;
			}else {
				professors = new ArrayList<>();
				resultSet.first();				// mi riposiziono alla prima riga 
				do {
					String u = resultSet.getString("username");
					String n = resultSet.getString("name");
					String s = resultSet.getString("surname");
					String e = resultSet.getString("email");
					
					Professor prof = new Professor(u, n, s, e);
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
	
	public static void changePassword(String username, String password) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = Queries.selectProfessorByUsername(stmt, username);
			if (!rs.first()) {
				System.out.println("No user found");
			} else {
				System.out.println("User found");
			}
			
			Queries.updateProfessorPassword(stmt, username, password);
			
			rs.close();
			
		} finally {
			if(stmt != null) stmt.close();
		}
	}
}
