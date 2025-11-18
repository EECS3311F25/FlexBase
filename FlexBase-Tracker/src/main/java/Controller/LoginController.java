package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import Model.DBOutput;
import View.HomePage;

/**
 * This is what will happen when the user will click the login button
 * basically the reactions to button will be captured here.
 *
 * for example: there has to be validation check - when i under my username and password.
 */
public class LoginController
{
	
	public static void login(JFrame frame, JButton loginButton, JTextField userField, JTextField passwordField)
	{
		loginButton.addActionListener(e -> {
	    	String userName = userField.getText().trim();
	        String password = passwordField.getText().trim();
	        boolean userExists = false;
	    	
	        // check DB for user info
	        ResultSet userInfo = DBOutput.getData("select * from user_info");
	        
	        try
	        {
	        	while(userInfo.next())
	            {
			    	// check if user exists in DB					// if user exists, check if password belongs to same user
	        		if (userInfo.getString(2).equals(userName)) if (userInfo.getString(3).equals(password)) userExists = true;
			    }
	        }
	        catch (SQLException error)
	        {
	        	System.out.println("SQL ERROR!");
	        }
	        
	        
	        if(userExists) {
	    		frame.dispose();            // close current login window
	            new HomePage().show();     // open the Home page
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(frame, "No user with entered log-in information");
	    	}
	        
	    });
	}
}
