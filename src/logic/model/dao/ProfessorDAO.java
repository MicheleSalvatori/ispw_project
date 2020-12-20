package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.RecordNotFoundException;
import logic.model.Professor;
import logic.model.Student;
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
}
