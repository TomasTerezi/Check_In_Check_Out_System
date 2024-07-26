
	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	
	
	

public class DataFetcher {


	
	    public static ResultSet fetchData() {
	        Connection connection = DbConnection.getConnection();
	        if (connection == null) {
	            return null;
	        }
	        try {
	            Statement stmt = connection.createStatement();
	            // SQL query to fetch data
	            String query = "SELECT * FROM check_in_out.users";
	            return stmt.executeQuery(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


}

