package logic.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonDB {

	private static SingletonDB dbInstance = null;
	private Connection conn;

	private SingletonDB() throws SQLException {
		try {
			Class.forName(AppProperties.getInstance().getProperty("dbdriver"));
		} catch (ClassNotFoundException e) {
			throw new SQLException();
		}
	}

	public Connection getConnection() throws SQLException {
		if (conn == null) {
			conn = DriverManager.getConnection(AppProperties.getInstance().getProperty("dburl"),
					AppProperties.getInstance().getProperty("dbuser"),
					AppProperties.getInstance().getProperty("dbpasswd"));
		}
		return conn;
	}

	public static SingletonDB getDbInstance() throws SQLException {
		if (dbInstance == null) {
			dbInstance = new SingletonDB();
		}
		return dbInstance;
	}
}
