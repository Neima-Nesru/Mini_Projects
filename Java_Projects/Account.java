import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Account extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastName;
	private JPasswordField passwordField;
	private JPasswordField confirmpasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Account frame = new Account();

					frame.setTitle("Grading Management System");
					frame.setVisible(true);
					
					//frame.setResizable(false);
					ImageIcon icon = new ImageIcon("logo.jpg");
					frame.setIconImage(icon.getImage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Account() {
		setForeground(new Color(128, 128, 128));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 286);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 0));
		panel.setBorder(new TitledBorder(null, "Create Account", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 348, 228);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 21, 80, 24);
		panel.add(lblNewLabel);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		lblLastName.setBounds(10, 56, 80, 24);
		panel.add(lblLastName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		lblPassword.setBounds(10, 91, 80, 28);
		panel.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		lblConfirmPassword.setBounds(10, 130, 122, 24);
		panel.add(lblConfirmPassword);
		
		firstName = new JTextField();
		firstName.setBounds(142, 24, 179, 20);
		panel.add(firstName);
		firstName.setColumns(10);
		
		lastName = new JTextField();
		lastName.setColumns(10);
		lastName.setBounds(142, 59, 179, 20);
		panel.add(lastName);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname, lname, pass, confirm;
				fname = firstName.getText();
				lname = lastName.getText();
				pass = passwordField.getText();
				confirm = confirmpasswordField.getText();
				
				try {
					
					if (fname.length() == 0 || lname.length() == 0 || pass.length() == 0 || confirm.length() == 0)
					{
						JOptionPane.showMessageDialog(null, "This field can't be empty!");
						
					}
					else if (pass.equals(confirm) == false)
					{
						JOptionPane.showMessageDialog(null, "Invalid password!");
						passwordField.setText("");
						confirmpasswordField.setText("");
					}
					else if (pass.length() < 8) {
						JOptionPane.showMessageDialog(null, "Weak Password! password must be at least 8 characters long");
						passwordField.setText("");
						confirmpasswordField.setText("");
					}
					else
					{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gradesystem", "root","");
						PreparedStatement prp = con.prepareStatement("insert into login(Name,Password)values(?,?)");
						prp.setString(1, fname+" "+lname);
						prp.setString(2, pass);
						
						prp.executeUpdate();
						
						JOptionPane.showMessageDialog(null, "Created Successfully!");
						GradeCalc calc = new GradeCalc();
						calc.setVisible(true);
						
						firstName.setText("");
						lastName.setText("");
						passwordField.setText("");
						confirmpasswordField.setText("");
						
					}

					
					}
					
					
				catch (Exception ex) {
					
				}
			}
		});
		btnNewButton.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		btnNewButton.setBounds(58, 191, 107, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
			
		});
		btnNewButton_1.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		btnNewButton_1.setBounds(194, 191, 107, 23);
		panel.add(btnNewButton_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(142, 96, 179, 20);
		panel.add(passwordField);
		
		confirmpasswordField = new JPasswordField();
		confirmpasswordField.setBounds(142, 133, 179, 20);
		panel.add(confirmpasswordField);
	}
}
