package Controller;

import java.sql.*;

import javax.swing.*;

import Model.DBInput;
import Model.DBOutput;

public class RegisterController
{
	public static String register(JFrame frame, JButton createAccButton, String username, String password, String confirmPassword)
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
					if (users.getString(2).equals(username))
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
					try { DBInput.input(query); 
							String userIDQuery = "SELECT USER_ID FROM USER_INFO WHERE USER_NAME = '" + username + "';";
							ResultSet user = DBOutput.getData(userIDQuery);
							
							if (user != null && user.next()) {
								userID = user.getString(1);
							}
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
