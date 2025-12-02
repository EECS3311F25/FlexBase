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
		
		int current = 0;
		String lastEndTime = "00:00:00";
		int[] tempPrio = priority;
		int sum = 1;
		
		while (sum != 0)
		{
			sum = 0;
			int maxPrio = 0;
			
			for (int i = 0; i < tempPrio.length; i++)
			{
				if (tempPrio[i] != 0 && tempPrio[i] > maxPrio) { maxPrio = tempPrio[i]; current = i; }
			}

			startTime[current] = (parseHour(lastEndTime)) + ":00:00";
			endTime[current] = (parseHour(startTime[current]) + idealHours[current]) + ":00:00";
			lastEndTime = endTime[current];
			
			tempPrio[current] = 0;
			
			for (int i = 0; i < tempPrio.length; i++) {
			    sum += tempPrio[i];
			}
		}
		
		String inQuery = "";
		
		for (int i = 0; i < index; i++)
		{
			inQuery = "insert into OPTIMIZED_HABIT (user_id, o_habit_name, o_habit_priority, o_habit_time_start, o_habit_time_end) values"
					+ "('" + userID + "', '" + habitName[i] + "', '" + priority[i] + "', '" + startTime[i] + "', '" + endTime[i] + "');";
			DBInput.input(inQuery);
		}
		
	}
	
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
