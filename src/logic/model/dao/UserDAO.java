package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.exceptions.RecordNotFoundException;
import logic.model.User;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class UserDAO {

	private UserDAO() {
		
	}
	
	public static User findUser(String username) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		Statement stmt = null;
		User user = null;
		ResultSet resultSet = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = Queries.selectUser(stmt, username);
			
			if (!resultSet.first()) {
				throw new RecordNotFoundException("No Username Found matching with name: " + username);
				
			}else {
				resultSet.first();				// mi riposiziono alla prima riga 
				user = new User();
				user.setUsername(resultSet.getString("username"));
				user.setName(resultSet.getString("name"));
				user.setSurname(resultSet.getString("surname"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
			}
			
			resultSet.close();
			
		} finally {
			if(stmt != null)
				stmt.close();
		}
		
		return user;
	}
}
