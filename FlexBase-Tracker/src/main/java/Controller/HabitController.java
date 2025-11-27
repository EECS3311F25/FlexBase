package Controller;

import java.sql.SQLException;

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
	
}
