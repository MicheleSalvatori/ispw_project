package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.RecordNotFoundException;
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
				typeUser = null;
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				typeUser = resultSet.getString("type");
			}
			
			if (typeUser == null) {
				RecordNotFoundException e = new RecordNotFoundException("No Username Found matching with name: " + username);
            	throw e;
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
		System.out.println("RoleDAO -> username = " + username );
		return role;
	}
}
