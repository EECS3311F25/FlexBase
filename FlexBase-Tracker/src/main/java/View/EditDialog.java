package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialog class for the generation of a specific dialog box for editing HabitCards
 * 
 * Used on the HabitPage app
 * 
 * Handles habit values currently attached to HabitCard that was interacted with (Old__),
 * then can take new input (new__), which are then used for editing the database.
 * 
 * Takes the frame, the habit name, priority, start time, and end time.
 */

class EditDialog extends JDialog {
	//F for field to differentiate from the inputs
	private JTextField nameF;
	private JTextField priorityF;
	private JTextField startF;
	private JTextField endF;
	
	
	private boolean done = false;
	
	public EditDialog(JFrame frame, String name, String priority, String start, String end) {
		super(frame, "Edit Habit", true);
		
		nameF = new JTextField(name, 15);
		priorityF = new JTextField(priority, 5);
		startF = new JTextField(start, 5);
		endF = new JTextField(end, 5);
		
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(e -> {
			done = true;
			dispose();
		});
		
		
		//Creates the dialog with the different inputs and the save button.
		setLayout(new GridLayout(5,2,10,10));
		add(new JLabel("Name:"));
		add(nameF);
		add(new JLabel("Priority:"));
		add(priorityF);
		add(new JLabel("Start hour:"));
		add(startF);
		add(new JLabel("End hour:"));
		add(endF);
		add(saveBtn);
		
		pack();
		setLocationRelativeTo(frame);
	}
	
	

	public boolean isDone() { 
		return done; 
		}
	
	public String getNewName() { 
		return nameF.getText().trim(); 
		}
	public String getNewPriority() {
		return priorityF.getText().trim(); 
		}
	public String getNewStart() { 
		return startF.getText().trim(); 
		}
	public String getNewEnd() { 
		return endF.getText().trim(); 
		}
	
}

