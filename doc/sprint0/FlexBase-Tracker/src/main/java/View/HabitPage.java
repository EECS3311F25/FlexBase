package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

        // Priority TextField
        JTextField priorityField = new JTextField();
        priorityField.setBounds(300, 210, 300, 30);
        frame.add(priorityField);

        // Add Habit Button
        JButton addButton = new JButton("Add Habit");
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        addButton.setFocusPainted(false);
        addButton.setBounds(340, 280, 120, 40);
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
            String priority = priorityField.getText().trim();

            if (habit.isEmpty() || priority.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out both fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create a new label or small box for the habit
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
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
