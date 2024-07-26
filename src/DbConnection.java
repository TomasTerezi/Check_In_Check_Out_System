import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DbConnection {
//	public static void main(String[] args) {
//	
//	getConnection();
//}

	public static Connection getConnection() {

		Connection conn = null;

		final String URL = "jdbc:mysql://localhost:3306/check_in_out";

		final String USER = "root";

		final String PASSWORD = "root";

		try {

			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			// JOptionPane.showMessageDialog(null, "Connection me db u be me sukeses");

		} catch (SQLException e) {

			// JOptionPane.showMessageDialog(null, "Connection me db nuk u be me sukeses");

			e.printStackTrace();

		}

		return conn;
	}
}
