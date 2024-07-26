import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class ChangePassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textContent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword(2);
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
	public ChangePassword(int ID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 410);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REQUEST FORM");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		lblNewLabel.setBounds(390, 40, 220, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Request type");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(390, 120, 128, 30);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBoxRequest = new JComboBox();
		comboBoxRequest.setModel(new DefaultComboBoxModel(new String[] {"----", "Username change", "Password change", "Email change"}));
		comboBoxRequest.setBounds(590, 121, 126, 30);
		contentPane.add(comboBoxRequest);
		
		JLabel lblNewLabel_1_1 = new JLabel("Content");
		lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(390, 190, 85, 30);
		contentPane.add(lblNewLabel_1_1);
		
		textContent = new JTextField();
		textContent.setBounds(590, 191, 126, 30);
		contentPane.add(textContent);
		textContent.setColumns(10);
		
		RoundedBorder btnSend = new RoundedBorder("Send", 25);
		btnSend.setForeground(new Color(255, 255, 255));
		btnSend.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		btnSend.setBackground(new Color(39, 85, 160));
		btnSend.setBorder(null);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "INSERT INTO check_in_out.requests (user_id,type,content) VALUES (?,?,?)";
				try {
					PreparedStatement ps = DbConnection.getConnection().prepareStatement(query);
					ps.setInt(1, ID);
					ps.setString(2, (String) comboBoxRequest.getSelectedItem());
					ps.setString(3, textContent.getText());
					ps.executeUpdate();
					
					textContent.setText("");
					comboBoxRequest.setSelectedItem("----");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSend.setBounds(590, 265, 126, 35);
		contentPane.add(btnSend);
		
		RoundedPanel panel = new RoundedPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(33, 41, 92));
		panel.setBounds(0, 0, 360, 375);
		contentPane.add(panel);
		
		JLabel lbl_Image = new JLabel("");
		lbl_Image.setIcon(new ImageIcon("backgrounds\\reset-password.png"));
		lbl_Image.setBounds(110, 57, 276, 214);
		panel.add(lbl_Image);
		
		RoundedBorder btnBack = new RoundedBorder("Send", 25);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new UserManagement().setVisible(true);
			}
		});
		btnBack.setText("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		btnBack.setBorder(null);
		btnBack.setBackground(new Color(39, 85, 160));
		btnBack.setBounds(390, 264, 126, 35);
		contentPane.add(btnBack);
	}
}
