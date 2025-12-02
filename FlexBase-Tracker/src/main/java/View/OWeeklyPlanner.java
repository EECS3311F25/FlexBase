package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Controller.HabitController;

public class OWeeklyPlanner extends JFrame {

    private String userID;
    private final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private final JPanel[] dayPanels = new JPanel[7];
    private final List<Habit> userHabits = new ArrayList<>();

    // Represents a habit entry
    public static class Habit {
        String name;
        int priority;
        int dayIndex;
        int startHour;
        int endHour;

        Habit(String name, int priority, String start, String end) {
            this.name = name;
            this.priority = priority;

            try {
                this.startHour = Integer.parseInt(start);
            } catch (NumberFormatException e) {
                this.startHour = 0;
            }
            try {
                this.endHour = Integer.parseInt(end);
            } catch (NumberFormatException e) {
                this.endHour = this.startHour + 2; // default 2 hours if invalid
            }
        }
    }

    public OWeeklyPlanner(String userID) {
        this.userID = userID;

        setTitle("Weekly Habit Calendar");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("My Weekly Habit Calendar", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(30, 30, 60));
        add(title, BorderLayout.NORTH);

        // Top bar with Back button
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(new Color(240, 240, 255));
        JButton backButton = new JButton("Back to Habits");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        topBar.add(backButton);
        add(topBar, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            dispose();
            new HabitPage(userID).show(); // back to HabitPage
        });

        // Main calendar grid
        JPanel weekPanel = new JPanel(new GridLayout(1, 7, 6, 6));
        weekPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        weekPanel.setBackground(new Color(240, 240, 255));
        add(weekPanel, BorderLayout.CENTER);

        // Create day panels
        for (int i = 0; i < days.length; i++) {
            JPanel dayPanel = new JPanel(new GridLayout(24, 1, 1, 1));
            dayPanel.setBackground(Color.WHITE);
            for (int hour = 0; hour < 24; hour++) {
                JPanel hourBlock = new JPanel(new BorderLayout());
                hourBlock.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
                hourBlock.setBackground(Color.WHITE);
                JLabel timeLabel = new JLabel(String.format("%02d:00", hour));
                timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
                timeLabel.setForeground(new Color(120, 120, 120));
                hourBlock.add(timeLabel, BorderLayout.WEST);
                dayPanel.add(hourBlock);
            }
            JScrollPane scrollPane = new JScrollPane(dayPanel);
            scrollPane.setBorder(BorderFactory.createTitledBorder(days[i]));
            dayPanels[i] = dayPanel;
            weekPanel.add(scrollPane);
        }
        
        
        // Load all habits for this user
        List<String> userHabitsStr = HabitController.outputOptHabits(userID);
        for (String s : userHabitsStr) {
            String[] parts = s.split("\\|");
            if (parts.length == 4) {
                String name = parts[0].replace("Habit: ", "").trim();
                int priority = Integer.parseInt(parts[1].replace("Priority:", "").trim());
                String startStr = parts[2].replace("Start:", "").trim();
                String endStr   = parts[3].replace("End:", "").trim();
                int startHour = parseHour(startStr);
                int endHour   = parseHour(endStr);

                userHabits.add(new Habit(name, priority, String.valueOf(startHour), String.valueOf(endHour)));
            }
        }

        // Distribute habits across days
        distributeAndPlotHabits();

        getContentPane().setBackground(new Color(240, 240, 255));
        setVisible(true);
    }

    // Distribute habits across the week with 5-hour max per day
    private void distributeAndPlotHabits() {
        int[] usedHoursPerDay = new int[7];
        int maxHoursPerDay = 23;

        for (Habit habit : userHabits) {
            int duration = habit.endHour - habit.startHour;
            if (duration <= 0) duration = 2; // default 2 hours

            for (int day = 0; day < 7; day++) {
                if (usedHoursPerDay[day] + duration <= maxHoursPerDay) {
                    habit.dayIndex = day;
                    usedHoursPerDay[day] += duration;  // count towards 5-hour limit
                    plotHabit(habit);
                    break;
                }
            }
        }
    }

    
 // Converts DB time string "hh:mm:ss" into an integer hour 0–23
    private int parseHour(String timeStr) {
        try {
            String[] parts = timeStr.split(":");
            if (parts.length == 3) {
                return Integer.parseInt(parts[0]); // use hour
            }
        } catch (Exception e) {
            // fallback if parsing fails
        }
        return 0;
    }
    
    // Plot a habit on the day panel
    private void plotHabit(Habit habit) {
        if (habit.dayIndex < 0 || habit.dayIndex > 6) return;

        JPanel dayPanel = dayPanels[habit.dayIndex];

        // Color by priority
        Color cardColor = new Color(0, 0, 0);
			// Makes it Light Green for low prio
			if (habit.priority <= 3) {
				cardColor = new Color(152, 251, 152);

			}
			// Makes it Blue for mid prio
			else if (habit.priority <= 6) {
				cardColor = new Color(135, 206, 250);
			}
			// Purple for high prio
			else {
				cardColor = new Color(186, 85, 211);
			}

        for (int hour = habit.startHour; hour < habit.endHour && hour < 24; hour++) {
            Component comp = dayPanel.getComponent(hour);
            if (comp instanceof JPanel block) {
                block.setBackground(cardColor);
                JLabel label = new JLabel(" " + habit.name, SwingConstants.LEFT);
                label.setFont(new Font("SansSerif", Font.PLAIN, 11));
                block.add(label, BorderLayout.CENTER);
            }
        }
    }
}
