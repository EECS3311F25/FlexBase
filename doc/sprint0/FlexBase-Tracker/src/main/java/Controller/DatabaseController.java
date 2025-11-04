package Controller;

import java.sql.*;

public class DatabaseController {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/flexbase_schema";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Root1234";

    // USING THIS METHOD IN THE UI CLASS CALLED HABITPAGE
    public static void insertHabit(String habitName, int habitPriority) {
    	String query = "INSERT INTO habit (HABIT_NAME, HABIT_PRIORITY, HABIT_TIME_START, HABIT_TIME_END) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

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
