package Controller;

import java.sql.*;

public class DatabaseController {

	// Declare and initialize DB connection parameters
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/flexbase_schema";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "JarmaSQL13@!";

    // USING THIS METHOD IN THE UI CLASS CALLED HABITPAGE
    public static void insertHabit(String habitName, int habitPriority) {
    	
    	// create template with placeholders for SQL query to input habit attributes
    	String query = "INSERT INTO habit (HABIT_NAME, HABIT_PRIORITY, HABIT_TIME_START, HABIT_TIME_END) VALUES (?, ?, ?, ?)";

    	// try-catch statement to connect to database and input habits
    	// catches database driver and connection error
        try {
            
        	// get DB driver
        	Class.forName("com.mysql.cj.jdbc.Driver");

        	// connect to DB
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement stmt = con.prepareStatement(query)) {

                // Set parameters
                stmt.setString(1, habitName);
                stmt.setInt(2, habitPriority);

                // Optional: for now, we’ll set null for start and end times
                stmt.setNull(3, Types.TIME);
                stmt.setNull(4, Types.TIME);

                stmt.executeUpdate();
                
                System.out.println("✅ Habit inserted: " + habitName);
            }
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found: " + e.getMessage());
        }
    }
}
