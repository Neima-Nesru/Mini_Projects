import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Front extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Front frame = new Front();
					frame.setVisible(true);
				ImageIcon image = new ImageIcon("logo.jpg");
				frame.setIconImage(image.getImage());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Front() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 0));
		panel.setBounds(10, 11, 414, 239);
		contentPane.add(panel);
		
		JButton btnNewButton = new JButton("About");
		btnNewButton.setBounds(44, 205, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This is simple GUI Application to calculate GPA\nFeatures:\n-Calculates GPA \n-Tells your status\n\nENJOY IT!! Thank You for Choosing our APP");;
				
			}
		});
		panel.setLayout(null);
		btnNewButton.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		panel.add(btnNewButton);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(179, 205, 89, 23);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login log = new Login();
				log.setVisible(true);
			}
		});
		btnOpen.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		panel.add(btnOpen);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(302, 206, 89, 23);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Lucida Fax", Font.BOLD, 13));
		panel.add(btnExit);
		
		JLabel lblNewLabel = new JLabel("Grade Management System ");
		lblNewLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 15));
		lblNewLabel.setBounds(70, 11, 271, 67);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to our GUI App");
		lblNewLabel_1.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		lblNewLabel_1.setBounds(80, 89, 231, 34);
		panel.add(lblNewLabel_1);
	}
}
