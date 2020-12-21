package logic.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonDB {

	private static SingletonDB dbInstance = null;
	private static Connection conn;

	private SingletonDB() throws SQLException {
		try {
			Class.forName(AppProperties.getInstance().getProperty("dbdriver"));
		} catch (ClassNotFoundException e) {
			throw new SQLException();
		}
	}

	public Connection getConnection() throws SQLException {
		if (conn == null) {
			System.out.println(AppProperties.getInstance().getProperty("dburl"));
			System.out.println(AppProperties.getInstance().getProperty("dbuser"));
			System.out.println(AppProperties.getInstance().getProperty("dbpasswd"));
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
