package Model;

import java.sql.*;

/*
 * DBConnector class uses a static method to get a connection to the database on the local server.
 * This class is called in:
 * DBInput
 * DBOutput
 */

public class DBConnector
{
	
	// declare and initialize local database connection parameters
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/flexbase_schema";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Root1234";
    
    // static method to connect to DB on local server and return the connection
    
    public static Connection connectDB() throws SQLException
    {
    	Connection con = null;
    	
    	try
    	{
        	// get DB driver
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
        	// try connection to DB
            try
            {
            	con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
           	}
            
            // catch DB connection error
            catch (SQLException e)
            {
                System.err.println("Database error: " + e.getMessage());
            }
    	}
    	
    	// catch driver error
    	catch (ClassNotFoundException e)
    	{
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    	
    	// if connection fails and variable con is not updated, return error
    	if (con == null) throw new SQLException("Failed to connect to database.");
    	
    	return con;
    }
}


// Code from original DatabaseController class for reference

//try-catch statement to connect to database and input habits
// catches database driver and connection error
//try {
//    
//	// get DB driver
//	Class.forName("com.mysql.cj.jdbc.Driver");
//
//	// connect to DB
//    try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//         PreparedStatement stmt = con.prepareStatement(query)) {
//
//        // Set parameters
//        stmt.setString(1, habitName);
//        stmt.setInt(2, habitPriority);
//
//        // Optional: for now, we’ll set null for start and end times
//        stmt.setNull(3, Types.TIME);
//        stmt.setNull(4, Types.TIME);
//
//        stmt.executeUpdate();
//        
//        System.out.println("✅ Habit inserted: " + habitName);
//    }
//} catch (SQLException e) {
//    System.err.println("❌ Database error: " + e.getMessage());
//} catch (ClassNotFoundException e) {
//    System.err.println("❌ JDBC Driver not found: " + e.getMessage());
//}
