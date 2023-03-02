import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.*;

import javax.swing.JTextField;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;



public class GradeResuts extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField gpaTF;
	private JTextField statusTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GradeResuts frame = new GradeResuts();

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
	public GradeResuts() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Grade Report", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 414, 397);
		contentPane.add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBackground(new Color(192, 192, 192));
		table.setColumnSelectionAllowed(true);
		table.setBounds(10, 54, 394, 235);
		panel.add(table);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gradesystem","root","");
			PreparedStatement prp = con.prepareStatement("select * from gradereport");
			ResultSet rs = prp.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			JLabel lblNewLabel = new JLabel("Grade Report");
			lblNewLabel.setFont(new Font("Lucida Fax", Font.BOLD, 15));
			lblNewLabel.setBounds(129, 13, 172, 29);
			panel.add(lblNewLabel);
			
			JButton btnNewButton_1_1 = new JButton("Exit");
			btnNewButton_1_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			btnNewButton_1_1.setFont(new Font("Lucida Fax", Font.BOLD, 13));
			btnNewButton_1_1.setBounds(291, 357, 113, 29);
			panel.add(btnNewButton_1_1);
			
			gpaTF = new JTextField();
			gpaTF.setColumns(10);
			gpaTF.setBounds(82, 318, 174, 20);
			panel.add(gpaTF);
			gpaTF.setEditable(false);
			
			JLabel lblNewLabel_1 = new JLabel("GPA");
			lblNewLabel_1.setFont(new Font("Lucida Fax", Font.BOLD, 12));
			lblNewLabel_1.setBounds(10, 320, 46, 14);
			panel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Status");
			lblNewLabel_1_1_1.setFont(new Font("Lucida Fax", Font.BOLD, 12));
			lblNewLabel_1_1_1.setBounds(10, 357, 62, 14);
			panel.add(lblNewLabel_1_1_1);
			
			statusTF = new JTextField();
			statusTF.setEditable(false);
			statusTF.setColumns(10);
			statusTF.setBounds(82, 354, 174, 20);
			panel.add(statusTF);
			
			JButton btnNewButton_1_1_1 = new JButton("Calculate");
			btnNewButton_1_1_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//calculate gpa and cgpa
					double sum1, sum2, gpa;
				
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gradesystem","root","");
						
						PreparedStatement prp = con.prepareStatement("select sum(Grade_point) as totalGpt, sum(Credit_Hour) as totalCrhr from gradereport");
						ResultSet rs = prp.executeQuery();
						
						
						if (rs.next()) {
							
							sum2 = rs.getDouble("totalGpt");
							sum1 = rs.getDouble("totalCrhr");
							
							gpa = sum2 / sum1;
						
							
							gpaTF.setText(Double.toString(gpa));
						if (gpa == 4) {
							
							statusTF.setText("Execellent!!!");
							
						}
						else if (gpa >= 3.5 && gpa < 4) {
							statusTF.setText("Very Good!!!");
						}
						else if (gpa >= 3 && gpa <3.5) {
							statusTF.setText("Good!!!");
						}
						else if (gpa > 1.75 && gpa < 3) {
							statusTF.setText("Fair!!!");
						}
						else if (gpa > 1.5 && gpa < 1.75) {
							statusTF.setText("Warning!!!");
						}
						else if ( gpa > 1 && gpa < 1.5) {
							statusTF.setText("Academic dismissal!!!");
						}
						else {
							statusTF.setText("Complete dismissal!!!");
						}
							
						}
						else {
							JOptionPane.showMessageDialog(null, "Couldn't Complete");
						}
										
					}
					catch (Exception ex) {
						
					}
					
				}
			});
			btnNewButton_1_1_1.setFont(new Font("Lucida Fax", Font.BOLD, 13));
			btnNewButton_1_1_1.setBounds(291, 313, 113, 29);
			panel.add(btnNewButton_1_1_1);
			
		}
		catch (Exception ex) {
			
		}
		
	    
	}
}
