package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.RecordNotFoundException;
import logic.model.Admin;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class AdminDAO {
	
	private AdminDAO() {}
	
	public static Admin findAdmin(String username, String password) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		Admin adminLogged = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectProfessor(stmt, username, password);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException("No Username Found matching with name: " + username);
				
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				adminLogged = new Admin();
				adminLogged.setUsername(resultSet.getString("username"));
				adminLogged.setName(resultSet.getString("name"));
				adminLogged.setSurname(resultSet.getString("surname"));
				adminLogged.setEmail(resultSet.getString("email"));
				adminLogged.setPassword(resultSet.getString("password"));
			}
			
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		System.out.println("AdminDAO -> username = " + adminLogged.getUsername());
		return adminLogged;
	}
}
