package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class EditDialog extends JDialog {
	private JTextField nameField;
	private JTextField hoursField;
	
	private boolean done = false;
	
	public EditDialog(JFrame frame, String name, String hours) {
		super(frame, "Edit Habit", true);
		
		nameField = new JTextField(name, 15);
		hoursField = new JTextField(hours, 5);
		
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(e -> {
			done = true;
			dispose();
		});
		
		setLayout(new GridLayout(3,2,10,10));
		add(new JLabel("Name:"));
		add(nameField);
		add(new JLabel("Hours:"));
		add(hoursField);
		add(saveBtn);
		
		pack();
		setLocationRelativeTo(frame);
	}
	
	public boolean isDone() { return done; }
	public String getNewName() { return nameField.getText(); }
	public String getNewHours() { return hoursField.getText(); }
	
}
