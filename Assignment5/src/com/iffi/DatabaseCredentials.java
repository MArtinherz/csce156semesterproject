package com.iffi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Connection parameters that are necessary for CSE's configuration, from the lab assignments 9 and 10
 * @author Martin Herz, Michael Endacott
 * A connection factory, this sets up parameters for log-in credentials and sets up a connection to query from here
 * 
 */


public class DatabaseCredentials {

	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static final String USERNAME = "";
	public static final String PASSWORD = "1234";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;


	/**
	 * 
	 * @returns a connection to database
	 */
	
	public static Connection connection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("Connection Error! ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
		
		
	}


	
}
