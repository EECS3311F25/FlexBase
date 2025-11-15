package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Makes a card on the screen that displays each habit
 * Each card shows:
 * A number for priority,
 * The name of the habit
 * The hours ****** to do ******
 * The description ***** to do ******
 */

class HabitCard extends JPanel {
	private final Color bgColor;
	
	public HabitCard(String number, String name, String hours, Color bgColor) {
		this.bgColor = bgColor;
		
		
		//So card stays consistent sized
		setPreferredSize(new Dimension(550, 80));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		
		//To not automatically fill the background
		setOpaque(false);
		
		//Card layout, using 10 pixel gap between segments
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//Padding
		setBorder(new EmptyBorder(10,15,10,15));
		
		
		// NUM LABEL
		//Centering/Sizing and cleanin up left segment, adding to card
		
		JPanel numPanel = new JPanel();
		numPanel.setOpaque(false);
		numPanel.setLayout(new BoxLayout(numPanel,BoxLayout.Y_AXIS));
		
		JLabel numTitle = new JLabel("Priority:");
		numTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
		numTitle.setForeground(Color.DARK_GRAY);
		
		JLabel numValue = new JLabel(number);
		numValue.setFont(new Font("SansSerif", Font.BOLD,18));
		numValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		numPanel.add(numTitle);
		numPanel.add(numValue);
		numPanel.setPreferredSize(new Dimension(40, 40));
		
		add(numPanel);
		
		add(Box.createHorizontalStrut(10));
		add(Box.createHorizontalGlue());
		
		// NAME PANEL
		
		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.Y_AXIS));
		
		JLabel nameTitle = new JLabel("Name:");
		nameTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
		nameTitle.setForeground(Color.DARK_GRAY);
		
		JLabel nameValue=new JLabel(name);
		nameValue.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		
		namePanel.add(nameValue);
		
		add(namePanel);
		
		//To help make sure all elements are centered right, this helps the right ones from being too far right
		add(Box.createHorizontalGlue());
		
		
		
		// HOURS LABEL
		JLabel hoursLabel = new JLabel(hours);
		hoursLabel.setFont(new Font("SansSerif", Font.BOLD,18));
		hoursLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		hoursLabel.setPreferredSize(new Dimension(40,40));
		
		add(hoursLabel);
		
		add(Box.createHorizontalStrut(10));
		
	}
	
	/**
	 * Paints the background and rounds the edges to make a smooth looking box for the card
	 * 
	 * @param g the Graphics used to paint
	 */
		@Override
		protected void paintComponent(Graphics g) {
			int roundness = 24;
			Graphics2D g2D = (Graphics2D) g.create();
			
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2D.setColor(bgColor);
			g2D.fillRoundRect(0, 0, getWidth(), getHeight(), roundness, roundness);
			
			g2D.dispose();
			
			super.paintComponent(g);
			
			
		}
	}
	

