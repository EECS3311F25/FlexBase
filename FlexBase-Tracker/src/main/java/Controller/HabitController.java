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
		String updatedStart = changeTimeForm(start);
		String updatedEnd = changeTimeForm(end);
		
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
				+ "('" + userID + "', '"+ habit + "', '" + priority + "', '"+ updatedStart + "', '" + updatedEnd + "');";
        
		try { DBInput.input(query); }
		catch (SQLException error) { System.out.println(error); }
		
		return priority;
	}
	
	private static String changeTimeForm(String t) {
		boolean numeric = false;
		//empty time
		if (t == null) return "00:00:00";
		
		t = t.trim();
		
		//in case user enters it with that format already
		if (t.contains(":")) {
			return t;
		}
		//checks if its just a normal number
		numeric = true;
		for (int i = 0; i <t.length(); i++) {
			char c = t.charAt(i);
			if (c<'0' || c > '9') {
				numeric = false;
				break;
			}
		}
		//if it isnt a normal number either, return 0
		if(!numeric) {
			return "00:00:00";
			
		}
		//converts hours if everything else checked before is good
		int hour = 0;
		for(int i = 0; i < t.length();i++) {
			hour = hour * 10 + (t.charAt(i) - '0');
		}
		//if hour is too small or too large
		if(hour < 0 || hour >23) {
			return "00:00:00";
		}
		//converts to usable format for database
		//checks if hour is smaller or larger than 10 for the sake of adding a 0 before
		String hourString = (hour < 10 ? "0" : "") + hour;
		return hourString + ":00:00";
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
    
    public static int getHourasInt(String timeStr) {
    	
    	if(timeStr == null|| timeStr.isEmpty()) {
    		return 0;
    	}
        try {
            String[] parts = timeStr.split(":");
            if (parts.length == 3) {
                return Integer.parseInt(parts[0]); // use hour
            }
        } catch (Exception e) {
            //if the parse fails
        }
        return 0;
    }
    
    public static boolean updateHabit(JFrame frame, String userID, String oldName, String oldStart, String oldEnd, String newName, String newPriority, String newStart, String newEnd) {
    	
    	//checks if input is valid (non empty)
    	if(newName.isEmpty() || newPriority.isEmpty()|| newStart.isEmpty()||newEnd.isEmpty()) {
    		JOptionPane.showMessageDialog(frame, "Please enter information for all data", "Fill out Info", JOptionPane.WARNING_MESSAGE);
    		return false;
    	}
    	
    	String updatedStart = changeTimeForm(newStart);
    	String updatedEnd = changeTimeForm(newEnd);
    	//long query to update habit
    	String query = "UPDATE habit SET " +"HABIT_NAME = '" + newName + "', " + "HABIT_PRIORITY = '" + newPriority + "', " + "HABIT_TIME_START = '" + updatedStart + "', " + "HABIT_TIME_END = '" + updatedEnd + "' " + "WHERE user_id = '" + userID + "' " + "AND habit_name = '" + oldName + "' " + "AND HABIT_TIME_START = '" + oldStart + "' " + "AND HABIT_TIME_END = '" + oldEnd + "';";
    	
    	//Checks if it can actually put the query in, if not pops error dialog
    	try {
    		DBInput.input(query);
    		return true;
    	} catch (SQLException error) {
    		error.printStackTrace();
    		JOptionPane.showMessageDialog(frame,  "Could not update the habit in database", "database error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	
    }
	
    
}
