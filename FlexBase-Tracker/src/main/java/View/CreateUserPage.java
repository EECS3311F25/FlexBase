package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import Controller.*;

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
		
		// button click - attempt to create user
		createAccButton.addActionListener(e -> {
			String username = userInput.getText().trim();
			String password = new String(passInput.getPassword());
			String confirmPassword = new String(confirmPassInput.getPassword());
			
			// call to RegisterController class to handle create-user logic
			String userID = RegisterController.register(frame, createAccButton, username, password, confirmPassword);
			
			// if user doesn't already exist in database and inputs are valid
			if (userID != "")
			{
				// let the user know their account's been created successfully
				JOptionPane.showMessageDialog(frame, "Account has been created successfully.");
				// go to login page
				// close the current window
				frame.dispose();
				new HomePage(userID).show();
			}
			
		});
	}

	public void show() {
		frame.setVisible(true);
	}
}
