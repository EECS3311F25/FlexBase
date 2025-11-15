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
	
	public HabitCard(String number, String name, Color bgColor) {
		this.bgColor = bgColor;
		
		//So card stays consisten sized
		setPreferredSize(new Dimension(550, 80));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		
		//To not automatically fill the background
		setOpaque(false);
		
		//Card layout, using 10 pixel gap between segments
		setLayout(new BorderLayout(10, 0));
		
		//Padding
		setBorder(new EmptyBorder(10,15,10,15));
		
		//Centering/Sizing and cleanin up left segment, adding to card
		JLabel numLabel = new JLabel(number);
		numLabel.setFont(new Font("SansSerif", Font.BOLD,18));
		numLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		numLabel.setPreferredSize(new Dimension(40, 40));
		
		add(numLabel, BorderLayout.WEST);
		
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		add(nameLabel, BorderLayout.CENTER);
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
	

