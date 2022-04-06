package com.iffi;

public class DatabaseCredentials {
	/**
	 * Connection parameters that are necessary for CSE's configuration, from the lab assignments 9 and 10
	 * 
	 * 
	 */
	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static final String USERNAME = "mherz";
	public static final String PASSWORD = "b1tTvHCn";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

}
