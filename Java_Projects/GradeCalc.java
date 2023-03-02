import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JToggleButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class GradeCalc extends JFrame {

	private JPanel contentPane;
	private JTextField CourseTitle;
	private JTextField CreditHour;
	private JTextField Percentage;
	private JTable gradeTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GradeCalc frame = new GradeCalc();

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
	public GradeCalc() {
		setForeground(new Color(192, 192, 192));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 368, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 0));
		panel.setBounds(10, 11, 334, 285);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Course Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCourseTitle = new JLabel("Course Title");
		lblCourseTitle.setFont(new Font("Lucida Fax", Font.BOLD, 11));
		lblCourseTitle.setBounds(22, 54, 80, 14);
		panel.add(lblCourseTitle);
		
		JLabel lblPercentage = new JLabel("Percentage");
		lblPercentage.setFont(new Font("Lucida Fax", Font.BOLD, 11));
		lblPercentage.setBounds(22, 141, 80, 14);
		panel.add(lblPercentage);
		
		JLabel lblCreditHour = new JLabel("Credit Hour");
		lblCreditHour.setFont(new Font("Lucida Fax", Font.BOLD, 11));
		lblCreditHour.setBounds(22, 98, 80, 14);
		panel.add(lblCreditHour);
		
		CourseTitle = new JTextField();
		CourseTitle.setColumns(10);
		CourseTitle.setBounds(112, 51, 205, 20);
		panel.add(CourseTitle);
		
		CreditHour = new JTextField();
		CreditHour.setColumns(10);
		CreditHour.setBounds(112, 95, 205, 20);
		panel.add(CreditHour);
		
		Percentage = new JTextField();
		Percentage.setColumns(10);
		Percentage.setBounds(112, 138, 205, 20);
		panel.add(Percentage);
		
		
		
		JButton btnNewButton_1 = new JButton("Enter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				String course, crhr, percentage, status = "";
				double sum = 0, gpa = 1, cgpa = 1, grade = 1, gpt = 1;
				int totalcr = 0;
				
				course = CourseTitle.getText();
				crhr = CreditHour.getText();
				percentage = Percentage.getText();
				
				
				try {
					if (course.length() == 0 || crhr.length() == 0 ||  percentage.length() == 0)
					{
						JOptionPane.showConfirmDialog(null, "Empty Field!!!");
					}
					else {
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gradesystem","root","");
						PreparedStatement prp = con.prepareStatement("insert into gradereport(Course_Title, Credit_Hour, Percentage, Grade, Grade_point)values (?,?,?,?,?)");
						
						prp.setString(1, course);
						prp.setString(2, crhr);
						prp.setString(3, percentage);
						
						double percent = Double.parseDouble(percentage);
						int cr = Integer.parseInt(crhr);
						
					
								if (percent >= 92)
								{
									prp.setString(4, "A+");
									gpt = cr * 4;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 86 && percent <= 91)
								{
									prp.setString(4, "A");
									gpt = cr * 4;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 80 && percent <= 85)
								{
									prp.setString(4,"A-");
									gpt = cr * 3.75;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 74 && percent <= 79)
								{
									prp.setString(4, "B+");
									gpt = cr * 3.5;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 68 && percent <= 73)
								{
									prp.setString(4, "B");
									gpt = cr * 3;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 62 && percent <= 67)
								{
									prp.setString(4, "B-");
									gpt = cr * 2.75;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 56 && percent <= 61)
								{
									prp.setString(4, "C+");
									gpt = cr * 2.5;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 50 && percent <= 55)
								{
									prp.setString(4, "C");
									gpt = cr * 2;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 44 && percent <= 49)
								{
									prp.setString(4, "C-");
									gpt = cr * 1.75;
									prp.setDouble(5, gpt);
								}
								else if (percent >= 38 && percent <= 43)
								{
									prp.setString(4, "D+");
									gpt = cr * 1.25;
									prp.setDouble(5, gpt);
								}
								
								else if (percent >= 32 && percent <= 37)
								{
									prp.setString(4, "D");
									gpt = cr * 1;
									prp.setDouble(5, gpt);
								}
								else
								{
									prp.setString(4, "F");
									gpt = cr * 0;
									prp.setDouble(5, gpt);
								}
								prp.executeUpdate();
						JOptionPane.showMessageDialog(null, "Recorded Successfully!");
						
							CourseTitle.setText("");
							CreditHour.setText("");
							Percentage.setText("");
							CourseTitle.requestFocus(true);
							
					}
					
				}
				catch (Exception ex) {
					
				}
			}
			
		});
		btnNewButton_1.setFont(new Font("Lucida Fax", Font.BOLD, 14));
		btnNewButton_1.setBounds(133, 203, 80, 34);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Clear");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CourseTitle.setText("");
				CreditHour.setText("");
				Percentage.setText("");
				
			}
		});
		btnNewButton_2.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		btnNewButton_2.setBounds(235, 205, 89, 33);
		panel.add(btnNewButton_2);
		
		
		
		JButton btnNewButton_3 = new JButton("Display");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				GradeResuts summary = new GradeResuts();
				summary.setVisible(true);
				
				   
				  
			}
		});
		btnNewButton_3.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		btnNewButton_3.setBounds(10, 205, 101, 33);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.setBounds(10, 249, 314, 25);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnNewButton.setFont(new Font("Lucida Fax", Font.BOLD, 13));
	}
}
