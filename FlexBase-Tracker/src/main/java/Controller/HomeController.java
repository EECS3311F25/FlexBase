package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.DBOutput;

// Controller class for the HomePage view
public class HomeController
{
	// static method takes primary key USER_ID as a parameter
	// returns associated username for display on homepage
	public static String getUser(String userID)
	{
		String usernameQuery = "SELECT USER_NAME FROM user_info WHERE USER_ID = '" + userID + "';";
        ResultSet user = DBOutput.getData(usernameQuery);
        String username = "";
        
        try {
        	if (user != null && user.next()) {
        		username = user.getString("USER_NAME");
        	}
        } catch (SQLException error) { System.out.println(error); }
        
        return username;
	}
}
