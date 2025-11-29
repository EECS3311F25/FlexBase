package View;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.DBConnector;

/**
 * Displays a weekly habit calendar (Mon–Sun) with a 24-hour view.
 * Loads habits from the database and automatically distributes them
 * across days based on priority and a daily 5-hour limit.
 */
public class WeeklyPlanner extends JFrame {

    // Represents a habit entry
    public static class Habit {
        String name;
        int priority;   // 1 = low, 5 = high
        int dayIndex;   // 0 = Mon ... 6 = Sun
        int startHour;  // 0–23
        int endHour;    // 1–24

        Habit(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return name + " (" + startHour + ":00–" + endHour + ":00, P" + priority + ")";
        }
    }

    private final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private final JPanel[] dayPanels = new JPanel[7];  // store day columns

    /**
     * Creates the calendar window and plots habits.
     */
    public WeeklyPlanner(List<Habit> habits) {
        setTitle("Weekly 24-Hour Habit Calendar");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Calendar title
        JLabel title = new JLabel("My Weekly Habit Calendar", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(30, 30, 60));
        add(title, BorderLayout.NORTH);

        // Top bar with a back button
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(new Color(240, 240, 255));
        JButton backButton = new JButton("Back to Habits");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        topBar.add(backButton);
        add(topBar, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            dispose(); // close calendar
            new HabitPage().show(); // return to habit page
        });

        // Main calendar grid
        JPanel weekPanel = new JPanel(new GridLayout(1, 7, 6, 6));
        weekPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        weekPanel.setBackground(new Color(240, 240, 255));
        add(weekPanel, BorderLayout.CENTER);

        // Create each day column
        for (int i = 0; i < days.length; i++) {
            JPanel dayPanel = new JPanel(new GridLayout(24, 1, 1, 1));
            dayPanel.setBackground(Color.WHITE);

            // Add hourly blocks
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

        // Distribute and plot habits automatically
        distributeAndPlotHabits(habits);

        getContentPane().setBackground(new Color(240, 240, 255));
        setVisible(true);
    }

    /**
     * Distributes habits across days based on priority.
     * Each day can have a maximum of 5 hours of habits.
     */
    private void distributeAndPlotHabits(List<Habit> habits) {
        // Sort by priority descending
        habits.sort((h1, h2) -> Integer.compare(h2.priority, h1.priority));

        int[] usedHoursPerDay = new int[7];
        int maxHoursPerDay = 5;
        int defaultHabitDuration = 2; // assume each habit is 2 hours

        for (Habit habit : habits) {
            for (int day = 0; day < 7; day++) {
                if (usedHoursPerDay[day] + defaultHabitDuration <= maxHoursPerDay) {
                    habit.dayIndex = day;
                    habit.startHour = usedHoursPerDay[day];
                    habit.endHour = habit.startHour + defaultHabitDuration;
                    usedHoursPerDay[day] += defaultHabitDuration;
                    plotHabit(habit);
                    break;
                }
            }
        }
    }

    /**
     * Colors and labels the time blocks for a habit.
     */
    private void plotHabit(Habit habit) {
        if (habit.dayIndex < 0 || habit.dayIndex > 6) return;

        JPanel dayPanel = dayPanels[habit.dayIndex];

        // Assign color based on priority
        Color bg = switch (habit.priority) {
            case 5 -> new Color(255, 180, 180);
            case 4 -> new Color(255, 210, 160);
            case 3 -> new Color(255, 240, 140);
            case 2 -> new Color(200, 255, 200);
            default -> new Color(220, 255, 220);
        };

        // Update the relevant hourly blocks
        for (int hour = habit.startHour; hour < habit.endHour && hour < 24; hour++) {
            Component comp = dayPanel.getComponent(hour);
            if (comp instanceof JPanel block) {
                block.setBackground(bg);
                JLabel label = new JLabel(" " + habit.name, SwingConstants.LEFT);
                label.setFont(new Font("SansSerif", Font.PLAIN, 11));
                block.add(label, BorderLayout.CENTER);
            }
        }
    }

    /**
     * Fetch all habits from the database.
     */
    public static List<Habit> loadHabitsFromDB() {
        List<Habit> habits = new ArrayList<>();
        try (Connection conn = DBConnector.connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT habit_name, habit_priority FROM habit")) {

            while (rs.next()) {
                String name = rs.getString("habit_name");
                int priority = rs.getInt("habit_priority");
                habits.add(new Habit(name, priority));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habits;
    }

    public static void main(String[] args) {
        List<Habit> habits = loadHabitsFromDB();
        SwingUtilities.invokeLater(() -> new WeeklyPlanner(habits));
    }
}
