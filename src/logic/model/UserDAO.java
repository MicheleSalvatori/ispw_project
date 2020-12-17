package logic.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class UserDAO {
	private UserDAO() {}
	
	public static User findUser(String username, String password) throws SQLException, ClassNotFoundException {
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
				userLogged.setPassword(resultSet.getString("password"));
				// aggiungere gli altri dati di user
			}
			if (userLogged == null) {
				//Sollevare eccezzione penso
			}
			resultSet.close();
		} finally {
			if(stmt != null)
				stmt.close();
		}
		System.out.println("UserDAO -> username= "+ userLogged.getUsername());
		return userLogged;
	}
}
