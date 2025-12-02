package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		
		double[] idealHours = new double[size];
		
		// use this (with updating in the following loop) to store hours, in case we want to use it for comparison in the future
//		int[] hours = new int[rs.getFetchSize()];
//		int[] startTime = new int[rs.getFetchSize()];
//		int[] endTime = new int[rs.getFetchSize()];
		
		int index = 0;
		
		// fill arrays with user's habit details in order to maintain parallel array values
		while (rs.next())
		{
			habitName[index] = rs.getString(3);
			
			priority[index] = rs.getInt(4);
			weight += priority[index];
			
			// use this to store hours, in case we want to use it for comparison in the future
//			startTime[index] = rs.getInt(5);
//			endTime[index] = rs.getInt(6);
//			hours[index] = endTime[index] = startTime[index];
			
			index++;
		}
		
		String inQuery = "";
		
		// loop adds hours while maintaining parallel array values; then inputs optimized habits one at a time to DB
		for (int i = 0; i < index; i++)
		{
			idealHours[i] = ((double) priority[i]/weight)*totalTime;
//			System.out.println(priority[i] +" "+ weight +" "+ idealHours[i]);
			
			inQuery = "insert into OPTIMIZED_HABIT (user_id, o_habit_name, o_habit_priority, o_habit_hours) values"
					+ "('" + userID + "', '" + habitName[i] + "', '" + priority[i] + "', '" + (int) idealHours[i] + "');";
			DBInput.input(inQuery);
		}
	}
	
}
