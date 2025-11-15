package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import Controller.DatabaseController;

/**
 * The {@code HabitPage} class provides a simple graphical interface for users
 * to add and manage personal habits along with their priority levels.
 * <p>
 * It allows users to:
 * <ul>
 *     <li>Enter a habit name and its priority.</li>
 *     <li>Add the habit to a scrollable list displayed at the bottom of the window.</li>
 * </ul>
 * 
 * The UI is built using Swing components and follows a clean, minimal layout.
 * </p>
 * 
 * Example:
 * <pre>
 *     HabitPage page = new HabitPage();
 *     page.show();
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

        // Title Label
        JLabel titleLabel = new JLabel("Add a New Habit");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setBounds(280, 40, 400, 40);
        frame.add(titleLabel);

        // Habit Label
        JLabel habitLabel = new JLabel("Habit:");
        habitLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        habitLabel.setBounds(200, 160, 100, 30);
        frame.add(habitLabel);

        // Habit TextField
        JTextField habitField = new JTextField();
        habitField.setBounds(300, 160, 300, 30);
        frame.add(habitField);

        // Priority Label
        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        priorityLabel.setBounds(200, 210, 100, 30);
        frame.add(priorityLabel);
       
        
        //Description Label
        JLabel hoursLabel = new JLabel("Hours:");
        hoursLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        hoursLabel.setBounds(200, 260, 120, 30);
        frame.add(hoursLabel);
        
      //Description TextField
        JTextField hoursField = new JTextField();
        hoursField.setBounds(300, 260, 300, 30);
        frame.add(hoursField);


        // Priority TextField
        JTextField priorityField = new JTextField();
        priorityField.setBounds(300, 210, 300, 30);
        frame.add(priorityField);

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

       
    
    
    addButton.addActionListener(e -> {
        String habit = habitField.getText().trim();
        String priorityText = priorityField.getText().trim();
        String hours = hoursField.getText().trim();

        if (habit.isEmpty() || priorityText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill out both fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int priority;
        try {
            priority = Integer.parseInt(priorityText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Priority must be a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save to database
        DatabaseController.insertHabit(habit, priority);

       
        // Display habit on the screen
/*
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemPanel.setBackground(new Color(240, 240, 240));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        itemPanel.add(new JLabel("Habit: " + habit + "    Priority: " + priority));

        habitListPanel.add(itemPanel);
        habitListPanel.revalidate();
        habitListPanel.repaint();

        // Clear input fields
        habitField.setText("");
        priorityField.setText("");

        JOptionPane.showMessageDialog(frame, "Habit added successfully!");
*/
        //                TESTING NEW CARD
        Color cardColor = new Color(0, 0, 0);
        //Makes it Light Green for low prio
        if (priority <= 3) {
        	cardColor = new Color(152, 251, 152);
        	
        }
        //Makes it Blue for mid prio
        else if (priority <= 6) {
        	cardColor = new Color(135, 206, 250);
        }
        //Purple for high prio
        else {
        	cardColor = new Color(186, 85, 211);
        }
        
        HabitCard card = new HabitCard(String.valueOf(priority), habit, hours, cardColor);
        
        habitListPanel.add(card);
        
        //Spacing
        habitListPanel.add(Box.createVerticalStrut(8));
        
        habitListPanel.revalidate();
        habitListPanel.repaint();
    });

    }
    
    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
