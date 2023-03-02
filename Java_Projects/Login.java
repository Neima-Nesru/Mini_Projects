import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.ImageIcon;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField nametextField;
	private JPasswordField passwordField;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 368, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Login Page", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 334, 214);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 32, 83, 33);
		panel.add(lblNewLabel);
		
		nametextField = new JTextField();
		nametextField.setBounds(103, 39, 203, 20);
		panel.add(nametextField);
		nametextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 76, 75, 38);
		panel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(103, 86, 203, 20);
		panel.add(passwordField);
		
		JButton newAccbutton = new JButton("Create new Account");
		newAccbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Account acc = new Account();
					acc.setVisible(true);
					
				}
				catch (Exception ex) {
					
				}
			}
		});
		newAccbutton.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		newAccbutton.setBounds(83, 180, 203, 23);
		panel.add(newAccbutton);
		
		JButton loginbutton = new JButton("Login");
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gradesystem", "root","");
					PreparedStatement prp = con.prepareStatement("select * from login where Name=? and Password=?");
					prp.setString(1,  nametextField.getText());
					prp.setString(2, passwordField.getText());
					ResultSet rs = prp.executeQuery();
					
				if (rs.next())
					{
					     GradeCalc grade = new GradeCalc();
					     grade.setVisible(true);
					    setVisible(false);
					}
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect username or Password!");
					nametextField.setText("");
					passwordField.setText("");
				}
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		loginbutton.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		loginbutton.setBounds(142, 128, 89, 23);
		panel.add(loginbutton);
		
		JLabel lblNewLabel_2 = new JLabel("or");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(178, 155, 24, 14);
		panel.add(lblNewLabel_2);
	}
}
