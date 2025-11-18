package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Controller.DatabaseController;

public class WeeklyCalendarPage {

    private JFrame frame;

    public WeeklyCalendarPage() {
        frame = new JFrame("Weekly Habit Calendar");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Weekly Calendar (Mon – Sun)", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        // Calendar grid panel
        JPanel calendarPanel = new JPanel(new GridLayout(1, 7));
        frame.add(calendarPanel, BorderLayout.CENTER);

        // Day names
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        // Get habits from database
        List<String[]> habits = DatabaseController.getAllHabits(); 
        // Expected: each item -> [habitName, priority]

        for (String day : days) {

            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
            dayPanel.setBorder(BorderFactory.createTitledBorder(day));

            // add habits inside each day box
            for (String[] habitRow : habits) {
                String habitName = habitRow[0];
                String priority = habitRow[1];

                JLabel habitLabel = new JLabel("• " + habitName + " (P: " + priority + ")");
                habitLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                dayPanel.add(habitLabel);
            }

            calendarPanel.add(dayPanel);
        }
    }

    public void show() {
        frame.setVisible(true);
    }
}
