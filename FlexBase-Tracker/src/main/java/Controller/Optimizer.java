package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.DBInput;
import Model.DBOutput;

/*
 * Optimizer class implements algorithms to reorganize the user's schedule to be more efficient and healthy.
 * This class is called in:
 * + 
 */

public class Optimizer
{
	
	// static method Optimize() takes userID as a parameter and pulls the respective user's habits from DB
	// then reorganizes habits by priority/weight * totaltime algorithm and inputs new opitmized habits into DB
	public static void Optimize(String userID) throws SQLException
	{
		// pull all of the user's habits as result set to iterate through
		String outQuery = "select * from habit where user_id = '" + userID + "';";
		ResultSet rs = DBOutput.getData(outQuery);
		
		int totalTime = 23;
		int size = 0;
		
		// count # of rows and reset rs pointer
		while (rs.next()) size++;
		rs = DBOutput.getData(outQuery);
		
		String[] habitName = new String[size];
		int[] habitID = new int[size];
		
		int weight = 0;
		int[] priority = new int[size];
		
		double[] idealHoursDouble = new double[size];
		int[] idealHours = new int[size];
		
		String[] startTime = new String[size];
		String[] endTime = new String[size];
		
		int index = 0;
		
		// fill arrays with user's habit details in order to maintain parallel array values
		while (rs.next())
		{
			habitName[index] = rs.getString(3);
			habitID[index] = rs.getInt(1);
			
			priority[index] = rs.getInt(4);
			weight += priority[index];
			
//			totalTime += parseHour(rs.getTime(6).toString()) - parseHour(rs.getTime(5).toString());
			
			index++;
		}
		
		// loop adds hours while maintaining parallel array values; then inputs optimized habits one at a time to DB
		for (int i = 0; i < index; i++)
		{
			idealHoursDouble[i] = ((double) priority[i]/weight)*totalTime;
			idealHours[i] = (int) idealHoursDouble[i];
		}
		
		// initializing variables for algorithm to store start/end time
		int current = 0;
		String lastEndTime = "00:00:00";
		
		// make copy of priority array to manipulate
		int[] tempPrio = new int[priority.length];
		for (int i = 0; i < priority.length; i++)
		{
			tempPrio[i] = priority[i];
		}
		
		int sum = 1;
		
		// loop finds highest priority habit, assigns it earliest start time
		// and end time based on ideal hours/duration, then removes it from copy array
		// continues to do so for all of the user's habits
		// loops while there are still habits (found by priority in copy array)
		while (sum != 0)
		{
			sum = 0;
			int maxPrio = 0;
			
			// find max priority
			for (int i = 0; i < tempPrio.length; i++)
			{
				if (tempPrio[i] != 0 && tempPrio[i] > maxPrio) { maxPrio = tempPrio[i]; current = i; }
			}
			
			// assign start and end time for habit with highest priority
			startTime[current] = (parseHour(lastEndTime)) + ":00:00";
			endTime[current] = (parseHour(startTime[current]) + idealHours[current]) + ":00:00";
			lastEndTime = endTime[current];
			
			// remove handled element from copy array
			tempPrio[current] = 0;
			
			// count to see if there are still habits to assort
			for (int i = 0; i < tempPrio.length; i++) {
			    sum += tempPrio[i];
			}
		}
		
		// create query to input newly sorted optimized habits into DB
		String inQuery = "";
		
		// reset previously optimized habits for user
		DBInput.input("delete from optimized_habit where user_id = '" + userID + "';");
		
		for (int i = 0; i < index; i++)
		{			
			inQuery = "insert into OPTIMIZED_HABIT (o_habit_id, user_id, o_habit_name, o_habit_priority, o_habit_time_start, o_habit_time_end) values"
					+ "('" + habitID[i] + "', '" + userID + "', '" + habitName[i] + "', '" + priority[i] + "', '" + startTime[i] + "', '" + endTime[i] + "');";
			DBInput.input(inQuery);
		}
		
	}
	
	// method to convert from "hh:mm:ss" format to integer for hours of the day
	static private int parseHour(String timeStr) {
        try {
            String[] parts = timeStr.split(":");
            if (parts.length == 3) {
                return Integer.parseInt(parts[0]); // use hour
            }
        } catch (Exception e) {
            // fallback if parsing fails
        }
        return 0;
    }
	
}
