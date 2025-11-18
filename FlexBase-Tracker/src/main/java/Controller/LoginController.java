package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import Model.DBOutput;

/**
 * This is what will happen when the user will click the login button
 * basically the reactions to button will be captured here.
 *
 * for example: there has to be validation check - when i under my username and password.
 */
public class LoginController
{
	
	// static method takes current window (frame), button that calls this method, user and pass parameters
	// validates user login info and allows user to enter home page
	public static boolean login(JFrame frame, JButton loginButton, String username, String password)
	{
	        boolean userExists = false;
	    	
	        // check DB for user info
	        ResultSet userInfo = DBOutput.getData("select * from user_info");
	        
	        try
	        {
	        	// sort through rows (entries) in user_info DB table
	        	while(userInfo.next())
	            {
			    	// check if user exists in DB					// if user exists, check if password belongs to same user
	        		if (userInfo.getString(2).equals(username)) if (userInfo.getString(3).equals(password)) userExists = true;
			    }
	        }
	        catch (SQLException error)
	        {
	        	System.out.println("SQL ERROR!");
	        }
		
		return userExists;
	}
}
