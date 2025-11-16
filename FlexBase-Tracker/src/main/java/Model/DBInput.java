package Model;

import java.sql.*;

/*
 * DBInput class uses a static method to pass input parameters into the local database.
 * This class is called in:
 * + 
 */

public class DBInput
{
	
	// static method to input queries into connected database
	public static void input(String inputQuery, Connection con) throws SQLException
	{
		// attempt statement execution
		try
		{
			// load and execute input SQL query
			PreparedStatement stmt = con.prepareStatement(inputQuery);
			stmt.executeUpdate();
            
			// verify successful DB input in console
            System.out.println("Succesful input!");
		}
		
		// catch DB access errors
		catch (SQLException e)
		{
			System.err.println("Failed to access Database: " + e);
		}
	}
	
}
