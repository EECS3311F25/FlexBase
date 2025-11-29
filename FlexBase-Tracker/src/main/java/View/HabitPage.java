package View;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import Model.DBInput;

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

        // Calendar Button
        JButton calendarButton = new JButton("Open Calendar");
        calendarButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        calendarButton.setFocusPainted(false);
        calendarButton.setBounds(640, 10, 140, 35);
        frame.add(calendarButton);

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
       
        // Start and End time Labels
        JLabel startTimeLabel = new JLabel("Start Time (hour):");
        startTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        startTimeLabel.setBounds(150, 210, 150, 30);
        frame.add(startTimeLabel);
        
        JLabel endTimeLabel = new JLabel("End Time (hour):");
        endTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        endTimeLabel.setBounds(150, 260, 150, 30);
        frame.add(endTimeLabel);
        
        // Text Fields
        JTextField habitField = new JTextField();
        habitField.setBounds(300, 110, 300, 30);
        frame.add(habitField);
        
        JTextField priorityField = new JTextField();
        priorityField.setBounds(300, 160, 300, 30);
        frame.add(priorityField);
        
        JTextField startTimeField = new JTextField();
        startTimeField.setBounds(300, 210, 300, 30);
        frame.add(startTimeField);
        
        JTextField endTimeField = new JTextField();
        endTimeField.setBounds(300, 260, 300, 30);
        frame.add(endTimeField);

        // Add Habit Button
        JButton addButton = new JButton("Add Habit");
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        addButton.setFocusPainted(false);
        addButton.setBounds(340, 300, 120, 40);
        frame.add(addButton);

        // Habit list panel
        habitListPanel = new JPanel();
        habitListPanel.setLayout(new BoxLayout(habitListPanel, BoxLayout.Y_AXIS));
        habitListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(habitListPanel);
        scrollPane.setBounds(100, 350, 600, 180);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Habits"));
        frame.add(scrollPane);
        
        // Go Back button action
        backButton.addActionListener(e -> {
            frame.dispose();
            new HomePage().show(); // your existing homepage
        });

        // ✅ Calendar Button Action (loads from DB)
        calendarButton.addActionListener(e -> {
            frame.dispose();
            // Load habits directly from the database
            List<WeeklyPlanner.Habit> habits = WeeklyPlanner.loadHabitsFromDB();
            SwingUtilities.invokeLater(() -> new WeeklyPlanner(habits));
        });

        // ✅ Add Habit button logic (now stores proper HH:MM:SS format)
        addButton.addActionListener(e -> {
            String habit = habitField.getText().trim();
            String priorityText = priorityField.getText().trim();
            String start = startTimeField.getText().trim();
            String end = endTimeField.getText().trim();

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

            // Parse numeric hours
            int startHour = parseHour(start);
            int endHour = parseHour(end);

            // ✅ Format proper SQL TIME strings
            String formattedStart = String.format("%02d:00:00", startHour);
            String formattedEnd = String.format("%02d:00:00", endHour);

            // Save to DB
            String query = "INSERT INTO habit (habit_name, habit_priority, habit_time_start, habit_time_end) VALUES "
                    + "('" + habit + "', '" + priority + "', '" + formattedStart + "', '" + formattedEnd + "');";
            try {
                DBInput.input(query);
                System.out.println("Inserted habit: " + habit + " (" + formattedStart + " - " + formattedEnd + ")");
            } catch (SQLException error) {
                System.out.println(error);
            }

            // Color-coded card for display
            Color cardColor;
            if (priority <= 3) cardColor = new Color(152, 251, 152);
            else if (priority <= 6) cardColor = new Color(135, 206, 250);
            else cardColor = new Color(186, 85, 211);

            HabitCard card = new HabitCard(String.valueOf(priority), habit, formattedStart, cardColor);
            habitListPanel.add(card);
            habitListPanel.add(Box.createVerticalStrut(8));
            habitListPanel.revalidate();
            habitListPanel.repaint();

            // Clear inputs
            habitField.setText("");
            priorityField.setText("");
            startTimeField.setText("");
            endTimeField.setText("");

            JOptionPane.showMessageDialog(frame, "Habit added successfully!");
        });
    }

    /**
     * Converts a time string like "8" or "08:00" into an integer hour.
     */
    private int parseHour(String time) {
        if (time == null || time.isEmpty()) return 0;
        try {
            if (time.contains(":")) {
                return Integer.parseInt(time.split(":")[0]);
            }
            return Integer.parseInt(time);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
