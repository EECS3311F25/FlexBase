package Controller;

import java.sql.*;

import javax.swing.*;

import Model.DBInput;
import Model.DBOutput;
import View.HomePage;

public class RegisterController
{
	
	public static void register(JFrame frame, JButton createAccButton, JTextField userInput, JPasswordField passInput, JPasswordField confirmPassInput)
	{
		// fields storing our inputs
				createAccButton.addActionListener(e -> {
					String username = userInput.getText().trim();
					String password = new String(passInput.getPassword());
					String confirmPassword = new String(confirmPassInput.getPassword());
					
					
					
					// if not all fields are filled throw a warning
					// and ask user to fill them in
					if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Missing Info",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					// if the passwords aren't the same warn the user
					if (!password.equals(confirmPassword)) {
						JOptionPane.showMessageDialog(frame, "Passwords are not the same!", "Error",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					// result set to sort through already created usernames to check if user input is unique
					ResultSet users = DBOutput.getData("select * from user_info");
					boolean userExists = false;
					
					try
					{				
						// username input verification
						while (users.next())
						{	
							// if user already exists in DB, ask user to make new username
							if (users.getString(2).equals(username))
							{
								JOptionPane.showMessageDialog(frame, "User aleady exists!");
								userExists = true;
								break;
							}
						}
						
						// if no users in DB/the user doesn't exist in DB, create new user
						if (!users.next() && userExists == false)
						{
							// DB query with user info parameters
							String query = "insert into user_info (user_name, user_pass) values"
									+ "('"+ username + "', '" + password + "');";
							
							// input user data in DB
							try { DBInput.input(query); }
							catch (SQLException error) { System.out.println(error); }
							
							
							// let the user know their account's been created successfully
							JOptionPane.showMessageDialog(frame, "Account has been created successfully.");
							// go to login page
							// close the current window
							frame.dispose();
							new HomePage().show();
						}
					}
					
					catch (SQLException error)
					{
						System.out.println(error);
					}

				});
	}
}
