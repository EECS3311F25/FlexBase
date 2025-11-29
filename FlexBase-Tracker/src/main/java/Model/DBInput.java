package Model;

import java.sql.*;

/*
 * DBInput class uses a static method to pass input parameters into the local database.
 * This class is called in:
 * RegisterController
 */

public class DBInput
{
	
	// static method to input queries into connected database
	public static void input(String query) throws SQLException
	{
		// attempt statement execution
		try
		{
			// Connect to DB
			// load and execute input SQL query
			PreparedStatement stmt = DBConnector.connectDB().prepareStatement(query);
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