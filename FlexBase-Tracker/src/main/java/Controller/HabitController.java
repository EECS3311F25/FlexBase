package Controller;

import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import Model.*;

// Controller class for the HabitPage view
public class HabitController
{
	// static method takes current window, habit details (habit name, priority level, start time, end time) as parameters and inputs to DB
	// returns priority as int
	public static int inputHabit(JFrame frame, String userID, String habit, String priorityText, String start, String end)
	{
		int priority = 0;
		
		if (habit.isEmpty() || priorityText.isEmpty() || start.isEmpty() || end.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return priority;
        }
		
        try {
            priority = Integer.parseInt(priorityText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Priority must be a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return priority;
        }

        // Save to database
        String query = "insert into habit (user_id, habit_name, habit_priority, habit_time_start, habit_time_end) values"
				+ "('" + userID + "', '"+ habit + "', '" + priority + "', '"+ start + "', '" + end + "');";
        
		try { DBInput.input(query); }
		catch (SQLException error) { System.out.println(error); }
		
		return priority;
	}
	
	
	// static method to fetch all habits for a given userID
    public static List<String> outputHabits(String userID) {
        List<String> habits = new ArrayList<>();
        String query = "SELECT habit_name, habit_priority, habit_time_start, habit_time_end FROM habit WHERE user_id = '" + userID + "';";

        try (Connection conn = DBConnector.connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String habitName = rs.getString("habit_name");
                int priority = rs.getInt("habit_priority");
                String start = rs.getString("habit_time_start");
                String end = rs.getString("habit_time_end");

                String habitInfo = "Habit: " + habitName + " | Priority: " + priority +
                                   " | Start: " + start + " | End: " + end;
                habits.add(habitInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving habits from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return habits;
    }
	
	
	
	
}
