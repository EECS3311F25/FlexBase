package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import Model.*;

import java.sql.*;

/**
 * This is the UI of what happens the user clicks the 'create new user' button
 */
public class CreateUserPage {

	private JFrame frame;

	public CreateUserPage() {
		frame = new JFrame("FlexBase - Create User");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(null);

		// title label
		JLabel titleLabel = new JLabel("Create a User");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		titleLabel.setBounds(330, 40, 400, 40);
		frame.add(titleLabel);

		// username label
		JLabel userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		userLabel.setBounds(200, 160, 100, 30);
		frame.add(userLabel);

		// input for username
		JTextField userInput = new JTextField();
		userInput.setBounds(330, 150, 250, 30);
		frame.add(userInput);

		// password label
		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		passLabel.setBounds(200, 200, 120, 30);
		frame.add(passLabel);

		// input for password
		JPasswordField passInput = new JPasswordField();
		passInput.setBounds(330, 200, 250, 30);
		frame.add(passInput);

		// confirm password label
		JLabel confirmPassLabel = new JLabel("Confirm Password:");
		confirmPassLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		confirmPassLabel.setBounds(160, 250, 160, 30);
		frame.add(confirmPassLabel);

		// input for password
		JPasswordField confirmPassInput = new JPasswordField();
		confirmPassInput.setBounds(330, 250, 250, 30);
		frame.add(confirmPassInput);

		// button to create the account
		JButton createAccButton = new JButton("Create Account");
		createAccButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		createAccButton.setBounds(300, 320, 200, 40);
		frame.add(createAccButton);

		// button to go back to main page
		JButton backButton = new JButton("Go Back");
		backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		backButton.setBounds(40, 20, 100, 40);
		
		// had to do this because it kept on having a smaller box
		// surround the text that says "Go Back".
		backButton.setFocusPainted(false);
		frame.add(backButton);

		// back button should go BACK to the login page
		backButton.addActionListener(e -> {
			// close the current window
			frame.dispose();
			new LoginPage().show();
		});

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
				if (!users.next() && userExists == false) this.enterUser(username, password);
			}
			
			catch (SQLException error)
			{
				System.out.println(error);
			}

		});

	}
	
	// method to create user in DB
	private void enterUser(String username, String password)
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

	public void show() {
		frame.setVisible(true);
	}
}
