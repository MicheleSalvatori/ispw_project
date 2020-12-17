package logic.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonDB {

	private static String USER = "admin";
	private static String PASS = "ISPWproject2020.";
	private static String DB_URL = "jdbc:mysql://ispw-db.czyfycdou3fy.eu-west-3.rds.amazonaws.com:3306/ispw_db?autoReconnect=false&useSSL=false";
    private static String driverClassName = "com.mysql.jdbc.Driver";
    
    private static SingletonDB dbInstance = null;
    private static Connection conn;
    
    private SingletonDB() throws SQLException, ClassNotFoundException {
    	Class.forName(driverClassName);
    	conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }
    
    public Connection getConnection() {
    	return conn;
    	
    }
    public static SingletonDB getDbInstance() throws SQLException, ClassNotFoundException {
    	if (dbInstance == null) {
    		dbInstance = new SingletonDB();
    	}
    	return dbInstance;
    }
}
