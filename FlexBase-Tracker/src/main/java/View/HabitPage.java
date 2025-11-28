package View;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.DBInput;
import Model.DBOutput;
import Model.UserSession;

/**
 * The {@code HabitPage} class provides a simple graphical interface for users
 * to add and manage personal habits along with their priority levels.
 * <p>
 * It allows users to:
 * <ul>
 * <li>Enter a habit name and its priority.</li>
 * <li>Add the habit to a scrollable list displayed at the bottom of the
 * window.</li>
 * </ul>
 * 
 * The UI is built using Swing components and follows a clean, minimal layout.
 * </p>
 * 
 * Example:
 * 
 * <pre>
 * HabitPage page = new HabitPage();
 * page.show();
 * </pre>
 * 
 * This will launch the habit tracking window.
 */

public class HabitPage {

	private JFrame frame;
	private JPanel habitListPanel; // panel to show added habits

	public HabitPage() {
		frame = new JFrame("FlexBase - Add Habit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(null);

		// Back button
		JButton backButton = new JButton("Go Back");
		backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		backButton.setFocusPainted(false);
		backButton.setBounds(10, 10, 100, 35);
		frame.add(backButton);

		// Title Label
		JLabel titleLabel = new JLabel("Add a New Habit");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		titleLabel.setBounds(280, 40, 400, 40);
		frame.add(titleLabel);

		// Habit Label
		JLabel habitLabel = new JLabel("Habit:");
		habitLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		habitLabel.setBounds(200, 110, 100, 30);
		frame.add(habitLabel);

		// Priority Label
		JLabel priorityLabel = new JLabel("Priority:");
		priorityLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		priorityLabel.setBounds(200, 160, 100, 30);
		frame.add(priorityLabel);

		// Description Label
		JLabel startTimeLabel = new JLabel("Start Time:");
		startTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		startTimeLabel.setBounds(200, 210, 120, 30);
		frame.add(startTimeLabel);

		JLabel endTimeLabel = new JLabel("End Time:");
		endTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		endTimeLabel.setBounds(200, 260, 120, 30);
		frame.add(endTimeLabel);

		// Habit TextField
		JTextField habitField = new JTextField();
		habitField.setBounds(300, 110, 300, 30);
		frame.add(habitField);

		// Priority TextField
		JTextField priorityField = new JTextField();
		priorityField.setBounds(300, 160, 300, 30);
		frame.add(priorityField);

		// Description TextField
		JTextField startTimeField = new JTextField();
		startTimeField.setBounds(300, 210, 300, 30);
		frame.add(startTimeField);

		// Description TextField
		JTextField endTimeField = new JTextField();
		endTimeField.setBounds(300, 260, 300, 30);
		frame.add(endTimeField);

		// Add Habit Button
		JButton addButton = new JButton("Add Habit");
		addButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		addButton.setFocusPainted(false);
		addButton.setBounds(340, 300, 120, 40);
		frame.add(addButton);

		// work in progress
//        ImageIcon habitIcon = new ImageIcon(HabitPage.class.getResource("/images/Priorities_To_Do_List.png"));
//        Image img = habitIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
//        habitIcon = new ImageIcon(img);
//
//        JLabel imageLabel = new JLabel(habitIcon);
//        imageLabel.setBounds(620, 160, 150, 150);
//        frame.add(imageLabel);

		habitListPanel = new JPanel();
		habitListPanel.setLayout(new BoxLayout(habitListPanel, BoxLayout.Y_AXIS));
		habitListPanel.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(habitListPanel);
		scrollPane.setBounds(100, 350, 600, 180);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Your Habits"));
		frame.add(scrollPane);

		backButton.addActionListener(e -> {
			frame.dispose(); // close current habit window
			new HomePage().show(); // open the Home page
		});

		addButton.addActionListener(e -> {
			String habit = habitField.getText().trim();
			String priorityText = priorityField.getText().trim();
			String start = startTimeField.getText().trim();
			String end = endTimeField.getText().trim();

			if (habit.isEmpty() || priorityText.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please fill out both fields.", "Missing Info",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			int priority;
			try {
				priority = Integer.parseInt(priorityText);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Priority must be a number.", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Save to database
			String query = "INSERT INTO HABIT (USER_ID, HABIT_NAME, HABIT_PRIORITY, HABIT_TIME_START, HABIT_TIME_END) VALUES ("
					+ UserSession.getID() + ", '" + habit + "', " + priority + ", '" + start + "', '" + end + "');";

			try {
				DBInput.input(query);
			} catch (SQLException error) {
				System.out.println(error);
			}

			// Display habit on the screen
			/*
			 * JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			 * itemPanel.setBackground(new Color(240, 240, 240));
			 * itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			 * itemPanel.add(new JLabel("Habit: " + habit + "    Priority: " + priority));
			 * 
			 * habitListPanel.add(itemPanel); habitListPanel.revalidate();
			 * habitListPanel.repaint();
			 * 
			 * // Clear input fields habitField.setText(""); priorityField.setText("");
			 * 
			 * JOptionPane.showMessageDialog(frame, "Habit added successfully!");
			 */
			// TESTING NEW CARD
			Color cardColor = new Color(0, 0, 0);
			// Makes it Light Green for low prio
			if (priority <= 3) {
				cardColor = new Color(152, 251, 152);

			}
			// Makes it Blue for mid prio
			else if (priority <= 6) {
				cardColor = new Color(135, 206, 250);
			}
			// Purple for high prio
			else {
				cardColor = new Color(186, 85, 211);
			}

			HabitCard card = new HabitCard(String.valueOf(priority), habit, start, cardColor);

			habitListPanel.add(card);

			// Spacing
			habitListPanel.add(Box.createVerticalStrut(8));

			habitListPanel.revalidate();
			habitListPanel.repaint();
		});

		loadHabit();
	}

	public void show() {
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	private void loadHabit() {
    	int currID = UserSession.getID();
    	String query = "SELECT * FROM HABIT WHERE USER_ID = " + currID + ";";
    	
    	try {
    		ResultSet us = DBOutput.getData(query);
    		while (us.next()) {
    			String habitTitle = us.getString("HABIT_NAME");
    			int priority = us.getInt("HABIT_PRIORITY");
    	        String start = us.getString("HABIT_TIME_START");
    	        String end = us.getString("HABIT_TIME_END");
    	        
    	        // get our card colors back
    	        Color cardColor;
                if (priority <= 3) cardColor = new Color(152, 251, 152);
                else if (priority <= 6) cardColor = new Color(135, 206, 250);
                else cardColor = new Color(186, 85, 211);
                
                HabitCard card = new HabitCard(String.valueOf(priority), habitTitle, start, cardColor);
                habitListPanel.add(card);
                habitListPanel.add(Box.createVerticalStrut(8));
    		}
    		
    		habitListPanel.revalidate();
    		habitListPanel.repaint();
    		
    	} catch (SQLException e) {
    		System.out.println("Failed to get user's habits: " + e.getMessage());
    	}
    }
}
