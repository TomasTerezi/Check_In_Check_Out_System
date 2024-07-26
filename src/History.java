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

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;

public class History extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int Id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					History frame = new History(21, false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ResultSet fetchData() {
		Connection connection = DbConnection.getConnection();
		if (connection == null) {
			return null;
		}
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM check_in_out.history WHERE user_id = " + Id + " ORDER BY Check_in DESC";
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create the frame.
	 */
	public History(int ID, boolean admin) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Id = ID;
		setResizable(false);
		setBounds(100, 100, 850, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUserHistoryAttendace = new JLabel("USER HISTORY ATTENDACE");
		lblUserHistoryAttendace.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		lblUserHistoryAttendace.setBounds(258, 20, 317, 30);
		contentPane.add(lblUserHistoryAttendace);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 80, 790, 120);
		contentPane.add(scrollPane);

		ResultSet rs = fetchData();
		if (rs == null) {
			JOptionPane.showMessageDialog(null, "Unable to fetch data from the database.");
			return;
		}

		TableModel tableModel = DbUtils.resultSetToTableModel(rs);

		table = new JTable(tableModel);

		scrollPane.setViewportView(table);

		RoundedBorder btnBack = new RoundedBorder("BACK", 25);
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setBackground(new Color(39, 85, 160));
		btnBack.setBorder(null);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new UserManagement().setVisible(true);
			}
		});
		if (!admin) {
			btnBack.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
			btnBack.setBounds(670, 300, 150, 38);
			contentPane.add(btnBack);
		}
		RoundedBorder btnExport = new RoundedBorder("EXPORT", 25);
		btnExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExport.setForeground(new Color(255, 255, 255));
		btnExport.setBackground(new Color(39, 85, 160));
		btnExport.setBorder(null);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String path = "C:\\Users\\User\\Desktop\\UserExport" + ID + ".csv";

				try {
					FileWriter fw = new FileWriter(path);

					PreparedStatement ps;
					String query = "SELECT user_id, Check_in, Check_out from history WHERE user_id = ?";
					ps = DbConnection.getConnection().prepareStatement(query);
					ps.setInt(1, ID);
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						fw.append(rs.getString("user_id"));
						fw.append(',');
						fw.append(rs.getString("Check_in"));
						fw.append(',');
						fw.append(rs.getString("Check_out"));
						fw.append("\n");
					}
					JOptionPane.showMessageDialog(null, "Data exported successfully!");
					fw.flush();
					fw.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnExport.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnExport.setBounds(30, 300, 150, 38);
		contentPane.add(btnExport);
	}

}
