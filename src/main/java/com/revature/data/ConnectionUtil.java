package com.revature.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static Connection conn = null;
	
	public static Connection getConnection() {
		
		/*
		 * We will use driver manager to actually connect to our database. 
		 * 
		 * We need to provide it a few arguments:
		 * 		jdbc:oracle:thin:@ENDPOINT:port:sid
		 * 		username
		 * 		password
		 * 
		 * For this example, we will likely just write our arguments in Strings directly.
		 * However, if we commit this to our github repo, our credentials are available to the public,
		 * so it is far safer to store credentials as an environment variable, and just read them in the
		 * java application.
		 * 
		 * When we modify our environment variables, we must restart STS to have them take effect.
		 * 
		 */
		
		try {
			
				/*
				 * This statement uses reflection to check whether a class with this fully qualified
				 * name is available.
				 */
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String[] creds = System.getenv("DBCreds2").split(";");
				conn = DriverManager.getConnection(creds[0], creds[1], creds[2]);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

