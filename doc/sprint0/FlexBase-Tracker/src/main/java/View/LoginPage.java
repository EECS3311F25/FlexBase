package View;

import javax.swing.*;
import java.awt.*;

/**
 * This is the login page, this is what the user will see the first time they run the app.
 */
public class LoginPage {

    private JFrame frame;

    public LoginPage() {
        frame = new JFrame("FlexBase - Habit Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("FlexBase - Habit Tracker");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setBounds(250, 40, 400, 40);
        frame.add(titleLabel);

        JButton loginButton = new JButton("Enter Habit");
        loginButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        loginButton.setFocusPainted(false);
        loginButton.setBounds(150, 250, 150, 50);
        frame.add(loginButton);

//        JButton createUserButton = new JButton("Create New User");
//        createUserButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        createUserButton.setFocusPainted(false);
//        createUserButton.setBounds(150, 350, 150, 50);
//        frame.add(createUserButton);

        ImageIcon calendarIcon = new ImageIcon(LoginPage.class.getResource("/images/2025Cal.png"));
        Image img = calendarIcon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        calendarIcon = new ImageIcon(img);

        JLabel calendarLabel = new JLabel(calendarIcon);
        calendarLabel.setBounds(400, 120, 350, 400);
        frame.add(calendarLabel);

       
        loginButton.addActionListener(e -> {
            frame.dispose();            // close current login window
            new HabitPage().show();     // open the Habit page
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}

