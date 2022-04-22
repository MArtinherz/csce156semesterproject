package com.iffi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Martin Herz, Michael Endacott
 * 
 * This class removes all instances of records within a table.
 * @param Query that deletes all records from table
 *
 */

public class DeleteRecords {

	
	/**
	 * 
	 * @param query
	 * Remove Record class strips all records from a given table
	 */
	
	protected static void RemoveRecord(String query) {
		Connection conn = DatabaseCredentials.connection();
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
		}
		catch(SQLException e) {
			System.out.println("SQL Error, couldn't drop table!");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			ps.close();
			conn.close();
		}
		catch (SQLException e){
			System.out.println("Couldn't close!");
			throw new RuntimeException(e);
		}
	}
}
