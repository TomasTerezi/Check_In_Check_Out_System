
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmployeeManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ID;
	private JTextField Name;
	private JTextField Surname;
	private JTextField Age;
	private JTextField Email;
	private JTextField Address;
	private JTextField Search;
	private JTable table;
	private JRadioButton MaleRadioButton;
	private JRadioButton FemaleRadioButton;
	private final JComboBox comboBoxJob = new JComboBox();

	PreparedStatement ps;
	ResultSet rs;

	private String selectedGender;
	private JTextField viewField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeManagement frame = new EmployeeManagement();
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

	private void clearFields() {
		ID.setText("");
		Name.setText("");
		Surname.setText("");
		Age.setText("");
		Email.setText("");
		Address.setText("");
		comboBoxJob.setSelectedItem("HR");
		MaleRadioButton.setSelected(false);
		FemaleRadioButton.setSelected(false);
	}

	public static ResultSet fetchData() {
		Connection connection = DbConnection.getConnection();
		if (connection == null) {
			return null;
		}
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM check_in_out.users";
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create the frame.
	 */
	public EmployeeManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setMaximumSize(new Dimension(1000, 650));
		setMinimumSize(new Dimension(1000, 650));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(207, 217, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(new Color(207, 217, 224));
		tabbedPane.setBounds(25, 47, 343, 543);
		contentPane.add(tabbedPane);

		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(new Color(207, 217, 224));
		tabbedPane.addTab("Add user", null, panelAdd, null);
		panelAdd.setLayout(null);
		panelAdd.setLayout(null);

		ID = new JTextField();
		ID.setBounds(166, 26, 150, 25);
		ID.setEditable(false);
		panelAdd.add(ID);
		ID.setColumns(10);

		Name = new JTextField();
		Name.setColumns(10);
		Name.setBounds(166, 76, 150, 25);
		panelAdd.add(Name);

		Surname = new JTextField();
		Surname.setColumns(10);
		Surname.setBounds(166, 122, 150, 25);
		panelAdd.add(Surname);

		Age = new JTextField();
		Age.setColumns(10);
		Age.setBounds(166, 170, 150, 25);
		panelAdd.add(Age);

		Email = new JTextField();
		Email.setColumns(10);
		Email.setBounds(166, 269, 150, 25);
		panelAdd.add(Email);

		Address = new JTextField();
		Address.setColumns(10);
		Address.setBounds(166, 316, 150, 25);
		panelAdd.add(Address);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblNewLabel.setBounds(20, 24, 118, 25);
		panelAdd.add(lblNewLabel);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblName.setBounds(20, 76, 118, 25);
		panelAdd.add(lblName);

		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblSurname.setBounds(20, 122, 118, 25);
		panelAdd.add(lblSurname);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblAge.setBounds(20, 170, 118, 25);
		panelAdd.add(lblAge);

		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblGender.setBounds(20, 217, 118, 25);
		panelAdd.add(lblGender);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblEmail.setBounds(20, 267, 118, 25);
		panelAdd.add(lblEmail);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblAddress.setBounds(20, 314, 120, 25);
		panelAdd.add(lblAddress);

		JLabel lblJobPosition = new JLabel("Job position");
		lblJobPosition.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
		lblJobPosition.setBounds(20, 364, 120, 25);
		panelAdd.add(lblJobPosition);

		RoundedBorder btnAdd = new RoundedBorder("ADD", 25);
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setBackground(new Color(39, 85, 160));
		btnAdd.setBorder(null);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String uName = Name.getText();
				String uSurname = Surname.getText();
				String uAge = Age.getText();
				String uEmail = Email.getText();
				String uAddress = Address.getText();
				String username = uName.toUpperCase() + " " + uSurname.toUpperCase();
				String password = (uName.charAt(0) + "" + uSurname.charAt(0) + uAge).toUpperCase();

				if (uName.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in a name!");
					return;
				} else if (uSurname.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in a surname!");
					return;
				} else if (uAge.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in age!");
					return;
				}
				int age = Integer.parseInt(uAge);
				if (age < 18) {
					JOptionPane.showMessageDialog(null, "Applicant must be 18 or older to apply for this job!");
					return;
				} else if (age >= 65) {
					JOptionPane.showMessageDialog(null, "Applicant must be younger to apply for this job!");
					return;
				}

				if (uEmail.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in your email!");
					return;
				}
				if (uAddress.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in your address!");
					return;
				}

				if (selectedGender == null) {
					JOptionPane.showMessageDialog(null, "Please select a gender!");
					return;
				}

				String query = "INSERT INTO users(Name, Surname, Age, Gender, Email, Address, Role) VALUES(?, ?, ?, ?, ?, ?, ?)";

				try {
					Connection connection = DbConnection.getConnection();
					ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, uName);
					ps.setString(2, uSurname);
					ps.setString(3, uAge);
					ps.setString(4, selectedGender);
					ps.setString(5, uEmail);
					ps.setString(6, uAddress);
					ps.setString(7, (String) comboBoxJob.getSelectedItem());

					ps.executeUpdate();

					ResultSet generatedKeys = ps.getGeneratedKeys();
					if (generatedKeys.next()) {
						int userId = generatedKeys.getInt(1);

						String accountQuery = "INSERT INTO accounts(username, password, user_id) VALUES(?, ?, ?)";
						ps = connection.prepareStatement(accountQuery);
						ps.setString(1, username);
						ps.setString(2, password);
						ps.setInt(3, userId);

						ps.executeUpdate();

						JOptionPane.showMessageDialog(null, "Employee and account have been registered successfully!");
					}

					refreshTable();
					clearFields();

				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, "Something went wrong!");
					err.printStackTrace();
				}
			}
		});

		btnAdd.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnAdd.setBounds(116, 470, 110, 35);
		panelAdd.add(btnAdd);

		JPanel panelDelete = new JPanel();
		panelDelete.setBackground(new Color(207, 217, 224));
		tabbedPane.addTab("Delete user", null, panelDelete, null);
		panelDelete.setLayout(null);

		JLabel lbl = new JLabel("ID");
		lbl.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lbl.setBounds(10, 15, 37, 19);
		panelDelete.add(lbl);

		ID = new JTextField();
		ID.setBounds(57, 10, 133, 26);
		ID.setEditable(true);
		panelDelete.add(ID);
		ID.setColumns(10);

		RoundedBorder btnDelete = new RoundedBorder("DELETE", 25);
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setBorder(null);
		btnDelete.setBackground(new Color(39, 85, 160));
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = ID.getText();

				String query = "DELETE FROM users WHERE ID=?";

				try {
					ps = DbConnection.getConnection().prepareStatement(query);
					ps.setString(1, id);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Employee has been deleted successfully!");

					refreshTable();
					clearFields();

				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Something went wrong");
				}
			}
		});
		btnDelete.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnDelete.setBounds(217, 10, 111, 27);
		panelDelete.add(btnDelete);

		RoundedBorder btnExport = new RoundedBorder("EXPORT", 25);
		btnExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExport.setForeground(new Color(255, 255, 255));
		btnExport.setBackground(new Color(39, 85, 160));
		btnExport.setBorder(null);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = "C:\\Users\\User\\Desktop\\AdminExport.csv";

				try {
					FileWriter fw = new FileWriter(path);

					PreparedStatement ps;
					String query = "SELECT * from users";
					ps = DbConnection.getConnection().prepareStatement(query);
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						fw.append(rs.getString("ID"));
						fw.append(',');
						fw.append(rs.getString("Name"));
						fw.append(',');
						fw.append(rs.getString("Surname"));
						fw.append(',');
						fw.append(rs.getString("Age"));
						fw.append(',');
						fw.append(rs.getString("Gender"));
						fw.append(',');
						fw.append(rs.getString("Email"));
						fw.append(',');
						fw.append(rs.getString("Address"));
						fw.append(',');
						fw.append(rs.getString("Role"));
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
		btnExport.setBounds(620, 545, 150, 35);
		contentPane.add(btnExport);

		RoundedBorder btnUpdate = new RoundedBorder("UPDATE", 25);
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setForeground(new Color(255, 255, 255));
		btnUpdate.setBackground(new Color(39, 85, 160));
		btnUpdate.setBorder(null);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String uId = ID.getText();
				String uName = Name.getText();
				String uSurname = Surname.getText();
				String uAge = Age.getText();
				String uEmail = Email.getText();
				String uAddress = Address.getText();
				String uJob = (String) comboBoxJob.getSelectedItem();

				String query = "UPDATE users SET Name=?, Surname=?, Age=?, Email=?, Address=?, Role=? WHERE ID=?";

				try {
					ps = DbConnection.getConnection().prepareStatement(query);
					ps.setString(1, uName);
					ps.setString(2, uSurname);
					ps.setString(3, uAge);
					ps.setString(4, uEmail);
					ps.setString(5, uAddress);
					ps.setString(6, uJob);
					ps.setString(7, uId);

					ps.executeUpdate();

					JOptionPane.showMessageDialog(null, "Update successful!");

					refreshTable();
					clearFields();

				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, "UPDATE FAILED!!");
					err.printStackTrace();
				}

			}
		});
		btnUpdate.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnUpdate.setBounds(402, 545, 150, 35);
		contentPane.add(btnUpdate);

		RoundedBorder btnSearch = new RoundedBorder("SEARCH", 25);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.setBackground(new Color(39, 85, 160));
		btnSearch.setBorder(null);
		btnSearch.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnSearch.setBounds(402, 75, 150, 35);
		contentPane.add(btnSearch);

		final JComboBox comboBoxFilter = new JComboBox();
		comboBoxFilter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxFilter.setForeground(new Color(255, 255, 255));
		comboBoxFilter.setBorder(null);
		comboBoxFilter.setBackground(new Color(39, 85, 160));
		;

//		HashMap<String,Integer> map = new HashMap();
//		map.put("", 0);
//		map.put("Name",1);
//		map.put("Age",3);
//		map.put("Gender",4);
//		map.put("Role", 6);
		Search = new JTextField();
		Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TableModel object = (TableModel) table.getModel();
				TableRowSorter<TableModel> list = new TableRowSorter<>(object);
				table.setRowSorter(list);
				int column = 0;
				switch ((String) comboBoxFilter.getSelectedItem()) {
				case "":
					column = 0;
					break;
				case "Name":
					column = 1;
					break;
				case "Age":
					column = 3;
					break;
				case "Gender":
					column = 4;
					break;
				case "Role":
					column = 6;
					break;
				default:
					column = 0;

				}
				list.setRowFilter(RowFilter.regexFilter(Search.getText(), column));
			}
		});

		Search.setColumns(10);
		Search.setBounds(620, 77, 150, 35);
		contentPane.add(Search);

		JLabel lblUserManagment = new JLabel("USER MANAGMENT");
		lblUserManagment.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		lblUserManagment.setBounds(601, 20, 239, 30);
		contentPane.add(lblUserManagment);

		ResultSet rs = DataFetcher.fetchData();
		if (rs == null) {
			JOptionPane.showMessageDialog(null, "Unable to fetch data from the database.");
			return;
		}

		TableModel tableModel = DbUtils.resultSetToTableModel(rs);
		table = new JTable(tableModel);
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JScrollPane scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel model = table.getModel();

				ID.setText(model.getValueAt(index, 0).toString());
				Name.setText(model.getValueAt(index, 1).toString());
				Surname.setText(model.getValueAt(index, 2).toString());
				Age.setText(model.getValueAt(index, 3).toString());
				Email.setText(model.getValueAt(index, 5).toString());
				Address.setText(model.getValueAt(index, 6).toString());
				comboBoxJob.setSelectedItem(model.getValueAt(index, 7).toString());
			}
		});
		scrollPane.setBounds(402, 130, 574, 383);
		contentPane.add(scrollPane);

		MaleRadioButton = new JRadioButton("Male");
		MaleRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		MaleRadioButton.setBackground(new Color(207, 217, 224));
		MaleRadioButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		MaleRadioButton.setBounds(140, 219, 71, 21);
		panelAdd.add(MaleRadioButton);

		FemaleRadioButton = new JRadioButton("Female");
		FemaleRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FemaleRadioButton.setBackground(new Color(207, 217, 224));
		FemaleRadioButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		FemaleRadioButton.setBounds(233, 219, 83, 21);
		panelAdd.add(FemaleRadioButton);
		comboBoxJob.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxJob.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

		comboBoxJob.setModel(new DefaultComboBoxModel(new String[] { "HR", "Business Analyst", "Supervisor",
				"Accountant", "Sales Person", "Software Architect", "Manager" }));
		comboBoxJob.setBounds(166, 368, 150, 25);
		panelAdd.add(comboBoxJob);

		JPanel panelView = new JPanel();
		panelView.setBackground(new Color(207, 217, 224));
		tabbedPane.addTab("View", null, panelView, null);
		panelView.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 15, 37, 19);
		panelView.add(lblNewLabel_1);

		viewField = new JTextField();
		viewField.setBounds(57, 10, 133, 26);
		panelView.add(viewField);
		viewField.setColumns(10);

		RoundedBorder btnView = new RoundedBorder("VIEW", 25);
		btnView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnView.setForeground(new Color(255, 255, 255));
		btnView.setBackground(new Color(39, 85, 160));
		btnView.setBorder(null);
		btnView.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(viewField.getText());
					new History(id, true).setVisible(true);
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		});
		btnView.setBounds(217, 10, 111, 27);
		panelView.add(btnView);

		RoundedBorder btnLogOut = new RoundedBorder("LOG OUT", 25);
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.setForeground(new Color(255, 255, 255));
		btnLogOut.setBackground(new Color(39, 85, 160));
		btnLogOut.setBorder(null);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Logged out successfully!");
				setVisible(false);
				new SelectUser().setVisible(true);
			}
		});
		btnLogOut.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnLogOut.setBounds(826, 545, 150, 35);
		contentPane.add(btnLogOut);

		comboBoxFilter.setModel(new DefaultComboBoxModel(new String[] { "------", "Name", "Age", "Gender", "Role" }));
		comboBoxFilter.setBounds(886, 77, 90, 35);
		contentPane.add(comboBoxFilter);

		RoundedPanel panel = new RoundedPanel();
		panel.setBackground(new Color(11, 37, 69));
		panel.setBounds(0, 0, 390, 613);
		contentPane.add(panel);

		RoundedBorder requests = new RoundedBorder("Requests", 25);
		requests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Requests().setVisible(true);

			}
		});
		requests.setForeground(Color.WHITE);
		requests.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		requests.setBorder(null);
		requests.setBackground(new Color(39, 85, 160));
		requests.setBounds(402, 21, 150, 35);
		contentPane.add(requests);

		MaleRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MaleRadioButton.isSelected()) {
					FemaleRadioButton.setSelected(false);
					selectedGender = "Male";
				}
			}
		});

		FemaleRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FemaleRadioButton.isSelected()) {
					MaleRadioButton.setSelected(false);
					selectedGender = "Female";

				}
			}
		});

	}
}
