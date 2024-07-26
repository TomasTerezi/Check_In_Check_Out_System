import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class SelectUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static int LoggedUserID = 2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectUser frame = new SelectUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public SelectUser() {
		setBounds(new Rectangle(0, 0, 200, 200));
		setTitle("Select User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 426);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		RoundedBorder AdminUserButton = new RoundedBorder("Admin", 30);
		AdminUserButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		AdminUserButton.setForeground(new Color(255, 255, 255));
		AdminUserButton.setBorder(null);
		AdminUserButton.setBackground(new Color(39, 85, 160));
		AdminUserButton.setMaximumSize(new Dimension(200, 100));
		AdminUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LogIn().setVisible(true);

			}
		});

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("backgrounds\\admin-alt.png"));
		lblNewLabel_1.setBounds(454, 122, 45, 52);
		contentPane.add(lblNewLabel_1);
		AdminUserButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		AdminUserButton.setBounds(490, 122, 139, 52);
		contentPane.add(AdminUserButton);

		RoundedBorder UserButton = new RoundedBorder("User", 30);
		UserButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		UserButton.setForeground(new Color(255, 255, 255));
		UserButton.setBackground(new Color(39, 85, 160));
		UserButton.setBorder(null);
		UserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LogIn().setVisible(true);
			}
		});
		UserButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		UserButton.setBounds(490, 228, 139, 52);
		contentPane.add(UserButton);

		RoundedPanel panel = new RoundedPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(33, 41, 92));
		panel.setBounds(0, 0, 1, 1);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Check-In/ Out System");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(49, 239, 241, 22);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("backgrounds\\reception (1).png"));
		lblNewLabel_3.setBounds(101, 69, 189, 143);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("backgrounds\\user.png"));
		lblNewLabel_2.setBounds(448, 228, 32, 52);
		contentPane.add(lblNewLabel_2);

				try {
					BufferedImage bufferedImage = ImageIO.read(new File("D:\\Echlipse-workspace\\CheckIn_CheckOut_System\\backgrounds\\reception (1).png"));
					Image image = bufferedImage.getScaledInstance(800, 500, Image.SCALE_DEFAULT);
					
					ImageIcon icon = new ImageIcon(image);
					JFrame frame = new JFrame();
					frame.getContentPane().setLayout(new FlowLayout());
					frame.setSize(150, 150);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	}
	
}
