package mvc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
		Connection conn = null;
		
		String url = "jdbc:mysql://192.168.111.101:3306/test";
		String user = "test";
		String password = "1234";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		
		return conn;
		
	}
}
