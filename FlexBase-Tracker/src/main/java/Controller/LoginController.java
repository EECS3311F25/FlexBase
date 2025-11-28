package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import Model.DBOutput;
import Model.UserSession;

/**
 * This is what will happen when the user will click the login button basically
 * the reactions to button will be captured here.
 *
 * for example: there has to be validation check - when i under my username and
 * password.
 */
public class LoginController {

	// static method takes current window (frame), button that calls this method,
	// user and pass parameters
	// validates user login info and allows user to enter home page
	public static boolean login(JFrame frame, JButton loginButton, String username, String password) {
		boolean userExists = false;

		// check DB for user info
		ResultSet userInfo = DBOutput.getData("select * from user_info");

		try {
			// sort through rows (entries) in user_info DB table
			while (userInfo.next()) {

				String dbUser = userInfo.getString(2);
				String dbPass = userInfo.getString(3);
				if (dbUser.equals(username) && dbPass.equals(password)) {

					int userID = userInfo.getInt("user_id");
					UserSession.setUser(userID, username);
					userExists = true;
					break;
				}
			}
		} catch (SQLException error) {
			System.out.println("SQL ERROR!");
		}

		return userExists;
	}
}
