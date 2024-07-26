import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import javax.swing.SwingConstants;

public class UserManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManagement frame = new UserManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static ResultSet fetchData() {
		Connection connection = DbConnection.getConnection();
		if (connection == null) {
			return null;
		}
		try {
			Statement stmt = connection.createStatement();
			// SQL query to fetch data
			String query = "SELECT * FROM check_in_out.users WHERE ID = " + SelectUser.LoggedUserID;
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create the frame.
	 */
	public UserManagement() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 311);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAttendance = new JLabel("ATTENDANCE MANAGEMENT");
		lblAttendance.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		lblAttendance.setBounds(188, 30, 335, 30);
		contentPane.add(lblAttendance);

		RoundedBorder btnCheckOut = new RoundedBorder("Check Out", 25);
		btnCheckOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCheckOut.setForeground(new Color(255, 255, 255));
		btnCheckOut.setBackground(new Color(39, 85, 160));
		btnCheckOut.setBorder(null);
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String checkoutquery = "UPDATE check_in_out.history SET Check_out = CURRENT_TIMESTAMP() WHERE user_id = ? AND Check_out IS NULL;";
				try {
					PreparedStatement ps = DbConnection.getConnection().prepareStatement(checkoutquery);
					ps.setInt(1, SelectUser.LoggedUserID);
					ps.executeUpdate();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCheckOut.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnCheckOut.setBounds(20, 220, 126, 35);
		contentPane.add(btnCheckOut);

		RoundedBorder btnHistory = new RoundedBorder("History", 25);
		btnHistory.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHistory.setForeground(new Color(255, 255, 255));
		btnHistory.setBackground(new Color(39, 85, 160));
		btnHistory.setBorder(null);
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new History(SelectUser.LoggedUserID, false).setVisible(true);
			}
		});
		btnHistory.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnHistory.setBounds(199, 220, 126, 35);
		contentPane.add(btnHistory);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane.setBounds(20, 80, 650, 115);
		contentPane.add(scrollPane);

		ResultSet rs = fetchData();
		if (rs == null) {
			JOptionPane.showMessageDialog(null, "Unable to fetch data from the database.");
			return;
		}

		TableModel tableModel = DbUtils.resultSetToTableModel(rs);

		table = new JTable(tableModel);

		scrollPane.setViewportView(table);

		RoundedBorder btnLogOut = new RoundedBorder("Log Out", 25);
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.setForeground(new Color(255, 255, 255));
		btnLogOut.setBackground(new Color(39, 85, 160));
		btnLogOut.setBorder(null);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new SelectUser().setVisible(true);
			}
		});
		btnLogOut.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnLogOut.setBounds(544, 220, 126, 35);
		contentPane.add(btnLogOut);

		RoundedBorder btnChangePass = new RoundedBorder("Change Password", 25);
		btnChangePass.setText("Change ");
		btnChangePass.setForeground(new Color(255, 255, 255));
		btnChangePass.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnChangePass.setBorder(null);
		btnChangePass.setBackground(new Color(39, 85, 160));
		btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				new ChangePassword(SelectUser.LoggedUserID).setVisible(true);
			}
		});
		btnChangePass.setBounds(378, 220, 126, 35);
		contentPane.add(btnChangePass);
	}
}
