package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import Model.DBOutput;

// Controller class for LoginPage view
public class LoginController
{
	// static method takes current window (frame), and username and password parameters
	// validates user login info with DB and allows user to enter home page
	public static String login(JFrame frame, String username, String password)
	{
	        String userID = "";
	    	
	        // check DB for user info
	        ResultSet userInfo = DBOutput.getData("select * from user_info");
	        
	        try
	        {
	        	// sort through rows (entries) in user_info DB table
	        	while(userInfo.next())
	            {
			    	// check if user exists in DB					// if user exists, check if password belongs to same user
	        		if (userInfo.getString(2).equals(username)) if (userInfo.getString(3).equals(password)) userID = userInfo.getString(1);
			    }
	        }
	        catch (SQLException error)
	        {
	        	System.out.println("SQL ERROR!");
	        }
		
		return userID;
	}
}
