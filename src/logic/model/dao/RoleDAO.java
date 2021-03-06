package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.RecordNotFoundException;
import logic.model.User;
import logic.utilities.Queries;
import logic.utilities.Role;
import logic.utilities.SingletonDB;

public class RoleDAO {
	
	private RoleDAO() {}
	
	public static Role findType(String username) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		String typeUser = null;
		Role role = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectRole(stmt, username);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException("No Username Found matching with name: " + username);
				
			} else {
				resultSet.first();				// mi riposiziono alla prima riga 
				typeUser = resultSet.getString("type");
			}
			
			switch (typeUser) {
			case "student":
				role = Role.STUDENT;
				break;
			case "admin":
				role = Role.ADMIN;
				break;
			case "professor":
				role = Role.PROFESSOR;
				break;
			default:
				break;
			}
			
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		return role;
	}
	
	public static User getPasswordByEmail(String email) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		User user = null;
		ResultSet rs = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = Queries.selectUserByEmail(stmt, email);
			
			if (!rs.first()) {
				throw new RecordNotFoundException("No user found matching with email: '" + email + "'.");
				
			}else {
				rs.first();				// mi riposiziono alla prima riga 
				String password = rs.getString("password");
				user = new User();
				user.setPassword(password);
			}
			
			rs.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		return user;
	}
	
	public static void deleteRole(User user) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			Queries.deleteRole(stmt, user.getUsername());
			
		} finally {
			if(stmt != null) stmt.close();
		}
	}
}
