package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class HabitCard extends JPanel {

    private Color bgColor;
    private boolean compactMode;

    // Habit page card constructor
    public HabitCard(String number, String name, String hours, Color bgColor) {
        this.bgColor = bgColor;
        this.compactMode = false;
        buildFullCard(number, name, hours);
    }

    // Weekly card constructor
    public HabitCard(int priority, String name, int startHour, int endHour, Color bgColor, boolean compact) {
        this.bgColor = bgColor;
        this.compactMode = compact;

        String hoursText = makeRangeText(startHour, endHour);
        buildCompactCard(String.valueOf(priority), name, hoursText);
    }


    //for habit page
    private void buildFullCard(String number, String name, String hoursText) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        int height = 80;
        int vPad = 10;
        int hPad = 15;

        setPreferredSize(new Dimension(550, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        setBorder(new EmptyBorder(vPad, hPad, vPad, hPad));

        Font titleFont = new Font("SansSerif", Font.PLAIN, 12);
        Font numFont = new Font("SansSerif", Font.BOLD, 18);
        Font nameFont = new Font("SansSerif", Font.PLAIN, 16);
        Font hoursFont = new Font("SansSerif", Font.PLAIN, 18);

        JPanel numPanel = new JPanel();
        numPanel.setOpaque(false);
        numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.Y_AXIS));
        numPanel.setPreferredSize(new Dimension(40, 40));

        JLabel titleLabel = new JLabel("Priority");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(numFont);

        numPanel.add(titleLabel);
        numPanel.add(numberLabel);
        add(numPanel);

        add(Box.createHorizontalStrut(10));

        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(nameFont);
        namePanel.add(nameLabel);

        add(namePanel);
        add(Box.createHorizontalGlue());

        JLabel hoursLabel = new JLabel(hoursText);
        hoursLabel.setFont(hoursFont);
        add(hoursLabel);

        add(Box.createHorizontalStrut(10));
    }


    //for weekly calendar
    private void buildCompactCard(String number, String name, String hoursText) {

        setOpaque(false);
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        //height and the padding
        int height = 32;
        int vPad = 3;
        int hPad = 7;

        setPreferredSize(new Dimension(400, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        setBorder(new EmptyBorder(vPad, hPad, vPad, hPad));
        //different fonts for all types of information
        Font titleFont = new Font("SansSerif", Font.PLAIN, 9);
        Font numFont = new Font("SansSerif", Font.BOLD, 11);
        Font nameFont = new Font("SansSerif", Font.PLAIN, 12);
        Font hoursFont = new Font("SansSerif", Font.PLAIN, 10);

        
        JPanel numPanel = new JPanel();
        numPanel.setOpaque(false);
        numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.Y_AXIS));
        numPanel.setPreferredSize(new Dimension(24, 20));

        JLabel pLabel = new JLabel("P");
        pLabel.setFont(titleFont);
        pLabel.setForeground(new Color(80, 80, 80));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(numFont);

        
        numPanel.add(pLabel);
        numPanel.add(numberLabel);
        add(numPanel);

        add(Box.createHorizontalStrut(6));

        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(nameFont);
        namePanel.add(nameLabel);

        
        add(namePanel);
        add(Box.createHorizontalGlue());

        JLabel hoursLabel = new JLabel(hoursText);
        hoursLabel.setFont(hoursFont);
        hoursLabel.setForeground(new Color(80, 80, 80));

        add(hoursLabel);

        add(Box.createHorizontalStrut(6));
    }


    //to calcuate time range
    private String makeRangeText(int start, int end) {
    	
    	//defaults 2 hours past start time without going past 24th hour
        if (end <= start) {
            end = start + 2;
            if (end > 24) end = 24;
        }

        int duration = end - start;
        

        return duration + "hrs";
    }


    //painting the boxes (differently depending on weekly or habit page)
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        int radius;
        if (compactMode) {
            radius = 10;
        } else {
            radius = 20;
        }

        
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();

        super.paintComponent(g);
    }
}