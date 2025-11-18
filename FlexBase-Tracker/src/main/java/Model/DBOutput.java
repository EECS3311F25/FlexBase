package Model;

import java.sql.*;

/*
 * DBOutput class uses a static method to fetch required data from the local database and returns it to the class it's called in.
 * This class is called in:
 * RegisterController
 * LoginController
 */

public class DBOutput
{
	
	// static method to fetch data from DB and return rows as ResultSet
	public static ResultSet getData(String query)
	{
		ResultSet rs = null;
		
		// attempt statement execution
		try
		{
			// Connect to DB
			// load and execute output SQL query
			PreparedStatement stmt = DBConnector.connectDB().prepareStatement(query);
			rs = stmt.executeQuery();
		}
		
		// catch DB access errors
		catch (SQLException e)
		{
			System.err.println("Failed to access Database: " + e);
		}
		
		return rs;
	}

}
