import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Component;

public class LogIn extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField u_name;
	private JPasswordField pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setResizable(false);
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 412);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Enter credintials");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		lblNewLabel.setBounds(450, 30, 201, 30);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblNewLabel_1.setBounds(390, 120, 109, 30);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(390, 210, 109, 30);
		contentPane.add(lblNewLabel_1_1);

		u_name = new JTextField();
		u_name.setBounds(519, 120, 170, 30);
		contentPane.add(u_name);
		u_name.setColumns(10);

		RoundedBorder LogInButton = new RoundedBorder("Log In", 30);
		LogInButton.setForeground(new Color(255, 255, 255));
		LogInButton.setBackground(new Color(39, 85, 160));
		LogInButton.setBorder(null);
		LogInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = u_name.getText();
				String passw = new String(pass.getPassword());

				PreparedStatement ps;
				ResultSet admin, user;

				String adminQuery = "SELECT name,password FROM admin WHERE name=? AND password=?";
				String userQuery = "SELECT user_id,username,password FROM accounts WHERE username=? AND password=?";

				try {

					ps = DbConnection.getConnection().prepareStatement(adminQuery);

					ps.setString(1, name);
					ps.setString(2, passw);

					admin = ps.executeQuery();

					ps = DbConnection.getConnection().prepareStatement(userQuery);

					ps.setString(1, name);
					ps.setString(2, passw);

					user = ps.executeQuery();

					if (admin.next()) {
						JOptionPane.showMessageDialog(null, "Logged in as Admin!");
						setVisible(false);
						new EmployeeManagement().setVisible(true);
					} else if (user.next()) {
						JOptionPane.showMessageDialog(null, "Logged in as User!");
						setVisible(false);
						int id = user.getInt("user_id");
						SelectUser.LoggedUserID = id;
						new UserManagement().setVisible(true);

						String queryCheck = "SELECT * FROM history WHERE DATE(Check_in) = CURDATE() AND user_id = ?";
						try (PreparedStatement psCheck = DbConnection.getConnection().prepareStatement(queryCheck)) {
							psCheck.setInt(1, id);
							ResultSet found = psCheck.executeQuery();

							if (!found.next()) {
								String queryInsert = "INSERT INTO history (user_id) VALUES (?)";
								try (PreparedStatement psInsert = DbConnection.getConnection()
										.prepareStatement(queryInsert)) {
									psInsert.setInt(1, id);
									psInsert.executeUpdate();
								}
							}
						} catch (Exception ee) {
							ee.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Login failed!");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, "Login failed!");
				}
			}
		});
		LogInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		LogInButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		LogInButton.setBounds(580, 300, 109, 37);
		contentPane.add(LogInButton);

		RoundedBorder BackButton = new RoundedBorder("Back", 30);
		BackButton.setForeground(new Color(255, 255, 255));
		BackButton.setBorder(null);
		BackButton.setBackground(new Color(39, 85, 160));
		BackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new SelectUser().setVisible(true);
			}
		});
		BackButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		BackButton.setBounds(390, 300, 109, 37);
		contentPane.add(BackButton);

		pass = new JPasswordField();
		pass.setBounds(519, 210, 170, 30);
		contentPane.add(pass);

		RoundedPanel panel = new RoundedPanel();
		panel.setBackground(new Color(33, 41, 92));
		panel.setBounds(0, 0, 360, 375);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblLogIn = new JLabel("Log In");
		lblLogIn.setForeground(new Color(255, 255, 255));
		lblLogIn.setBackground(new Color(240, 240, 240));
		lblLogIn.setBounds(135, 30, 96, 29);
		panel.add(lblLogIn);
		lblLogIn.setFont(new Font("Trebuchet MS", Font.BOLD, 24));

		JLabel lbl_log_in_image = new JLabel("");
		lbl_log_in_image.setAlignmentX(20.0f);
		lbl_log_in_image.setIcon(new ImageIcon("backgrounds\\login.png"));
		lbl_log_in_image.setBounds(108, 101, 340, 164);
		panel.add(lbl_log_in_image);
	}
}
