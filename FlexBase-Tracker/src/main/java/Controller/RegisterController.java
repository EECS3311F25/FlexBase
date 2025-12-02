package Controller;

import java.sql.*;

import javax.swing.*;

import Model.DBInput;
import Model.DBOutput;

// Controller class for the CreateUserPage view 
public class RegisterController
{
	
//	static method takes current window (frame), username, password and confirmPassword parameters
//	validates user registration info, creates new user account in DB and allows user to enter home page
	public static String register(JFrame frame, String username, String password, String confirmPassword)
	{
			String userID = "";
			
			// if not all fields are filled throw a warning
			// and ask user to fill them in
			if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Missing Info",
						JOptionPane.WARNING_MESSAGE);
				return userID;
			}

			// if the passwords aren't the same warn the user
			if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(frame, "Passwords are not the same!", "Error",
						JOptionPane.WARNING_MESSAGE);
				return userID;
			}
			
			// result set to sort through already created usernames to check if user input is unique
			ResultSet users = DBOutput.getData("select * from user_info");
			
			boolean userAlreadyExists = false;
			
			try
			{				
				// username input verification
				while (users.next())
				{	
					// if user already exists in DB, ask user to make new username
					if (users.getString("user_name").equals(username))
					{
						JOptionPane.showMessageDialog(frame, "User already exists!");
						userAlreadyExists = true;
						break;
					}
				}
				
				// if no users in DB/the user doesn't exist in DB, create new user
				if (!userAlreadyExists)
				{
					// DB query with user info parameters
					String query = "insert into user_info (user_name, user_pass) values"
							+ "('"+ username + "', '" + password + "');";
					
					// input user data in DB
					try {
						DBInput.input(query);
						
						// get userID to pass into HomePage
						String userIDQuery = "SELECT USER_ID FROM USER_INFO WHERE USER_NAME = '" + username + "';";
						ResultSet user = DBOutput.getData(userIDQuery);
							
						if (user != null && user.next()) userID = user.getString("user_id");
					}
					catch (SQLException error) { System.out.println(error); }
				}
			}
			
			catch (SQLException error)
			{
				System.out.println(error);
			}
			
			return userID;
	}
}
