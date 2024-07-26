import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class Requests extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField id;
	private JButton btnReject;
	private JButton btnAccept;
	private JTextField content;
	private JTextField user_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Requests frame = new Requests();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void refreshTable() {
		try {
			ResultSet rs = fetchData();
			if (rs == null) {
				JOptionPane.showMessageDialog(null, "Unable to fetch data from the database.");
				return;
			}
			TableModel tableModel = DbUtils.resultSetToTableModel(rs);
			table.setModel(tableModel);

		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	private void deletereq(int id)
	{
		String query = "DELETE FROM check_in_out.requests WHERE id = ?";
		PreparedStatement ps;
		try {
			ps = DbConnection.getConnection().prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();;
			refreshTable();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Request Doesnt Exist");
		}
	}
	
	public static ResultSet fetchData() {
		Connection connection = DbConnection.getConnection();
		if (connection == null) {
			return null;
		}
		try {
			Statement stmt = connection.createStatement();
			// SQL query to fetch data
			String query= "SELECT * FROM requests";
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create the frame.
	 */
	public Requests() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 423);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 58, 557, 253);
		contentPane.add(scrollPane);
		ResultSet rs = fetchData();
		if (rs == null) {
			JOptionPane.showMessageDialog(null, "Unable to fetch data from the database.");
			return;
		}
		
		user_id = new JTextField();
		user_id.setVisible(false);
		user_id.setBounds(358, 346, 96, 19);
		contentPane.add(user_id);
		user_id.setColumns(10);
		
		JTextField type = new JTextField();
		type.setVisible(false);
		type.setBounds(252, 346, 96, 19);
		contentPane.add(type);
		type.setColumns(10);
		
		content = new JTextField();
		content.setVisible(false);
		content.setBounds(361, 316, 96, 19);
		contentPane.add(content);
		content.setColumns(10);


		TableModel tableModel = DbUtils.resultSetToTableModel(rs);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel model = table.getModel();

				model.getValueAt(index, 0).toString();
				user_id.setText(model.getValueAt(index, 0).toString());
				id.setText(model.getValueAt(index, 1).toString());
				type.setText(model.getValueAt(index, 2).toString());
				content.setText(model.getValueAt(index, 3).toString());
				
			}
		});
		scrollPane.setViewportView(table);
		
		id = new JTextField();
		id.setVisible(false);
		id.setEnabled(false);
		id.setBounds(252, 316, 96, 19);
		contentPane.add(id);
		id.setColumns(10);
		
		btnReject = new RoundedBorder("Reject", 25);
		btnReject.setForeground(new Color(255, 255, 255));
		btnReject.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		btnReject.setBackground(new Color(39, 85, 160));
		btnReject.setBorder(null);
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Id = id.getText();
				if(Id == "")
				{
					return;
				}
				deletereq(Integer.parseInt(Id));
				id.setText("");
			}
		});
		btnReject.setBounds(50, 330, 120, 35);
		contentPane.add(btnReject);
		
		btnAccept = new RoundedBorder("Accept", 25);
		btnAccept.setBorder(null);
		btnAccept.setBackground(new Color(39, 85, 160));
		btnAccept.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		btnAccept.setForeground(new Color(255, 255, 255));
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Type = type.getText();
				String Id = user_id.getText();
				if(Id == "")
				{
					return;
				}
				switch(Type)
				{
				case "Username change":
					String username = content.getText();
					String name = username.split(" ")[0];
					String surname = username.split(" ")[1];
					String query = "UPDATE check_in_out.users SET name = ?, surname = ? WHERE id = ? ";
					String query2 = "UPDATE check_in_out.accounts SET username = ? WHERE user_id = ? ";
					PreparedStatement ps;
					try {
						ps = DbConnection.getConnection().prepareStatement(query);
						ps.setString(1, name);
						ps.setString(2,surname);
						System.out.println(Id);
						ps.setInt(3, Integer.parseInt(Id));
						
						ps.executeUpdate();
						ps = DbConnection.getConnection().prepareStatement(query2);
						ps.setString(1,username);
						ps.setInt(2,Integer.parseInt(Id));
						ps.executeUpdate();
						
						
						refreshTable();
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("Request Doesnt Exist");
					}
					break;
				case "Password change":
					String pass = content.getText();
					String query3 = "UPDATE check_in_out.accounts SET password = ? WHERE user_id = ? ";

					try {
						ps = DbConnection.getConnection().prepareStatement(query3);
						ps.setString(1, pass);
						ps.setInt(2, Integer.parseInt(Id));
						ps.executeUpdate();
						
						refreshTable();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Request does not exist!");

					}
					break;
				case "Email change":
					String email = content.getText();
					String query4 = "UPDATE check_in_out.users SET Email = ? WHERE id = ? ";
						try {
							if (EmailValidator.isValidEmail(email)) {	
						ps = DbConnection.getConnection().prepareStatement(query4);
						ps.setString(1, email);
						ps.setInt(2, Integer.parseInt(Id));
						ps.executeUpdate();
						}
							else {
		                    JOptionPane.showMessageDialog(null, "Invalid email!");}
							
							refreshTable();
					}
						catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Request does not exist!");
					}
					break;
				default:

				}
				id.setText("");
				type.setText("");
				content.setText("");
				user_id.setText("");
				deletereq(Integer.parseInt(id.getText()));
			}
		});
		btnAccept.setBounds(488, 330, 120, 35);
		contentPane.add(btnAccept);

	}
}
