package javafx_plus_willpower.utilities;

import com.example.flat2d.DesignPatterns.User;
import javafx_plus_willpower.record.DatabaseConfig;

import java.sql.*;

public class DatabaseUtilities {
    private static final DatabaseConfig DATABASE_CONFIG = DatabaseConfig.createWithDefaults();
    private DatabaseUtilities() {
        // Private constructor to prevent instantiation from outside the class
    }
    // Load the JDBC driver
    static {
        try {
            Class.forName( DATABASE_CONFIG.MYSQL_JDBC_DRIVER_CLASS());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }
    // Establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE_CONFIG.jdbcUrl(),
                DATABASE_CONFIG.username(),
                DATABASE_CONFIG.password()
        );
    }

    // Prepare a PreparedStatement for a given query
    private static PreparedStatement prepareStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    // Insert data into the database
    public static String signUpMethod(String username, String password) {
        String query = "INSERT INTO " + DATABASE_CONFIG.tableName() + " (username, password) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(query)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the update and check the affected rows
            int rowAffected = preparedStatement.executeUpdate();

            // Return a success or failure message based on the result
            return (rowAffected > 0) ? "Data inserted successfully!" : "Failed to insert data.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to insert data.";
        }
    }
    // Method to check user credentials in the database
    public static Boolean userCheckerMethod(String username, String password) {
        String query = "SELECT * FROM " + DATABASE_CONFIG.tableName() + " WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(query)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                if(rs.getString("id") == password && rs.getString("username") == username){
                    User user = User.getInstance();
                    System.out.println(user.getUserId()+":ID UN: "+user.getUsername());
//                int id = rs.getInt("id");
//                String u_name = rs.getString("username");
                    user.setUserId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    return true;
                }

                System.out.println("The user id: "+rs.getInt("id"));
                System.out.println("The username: "+rs.getString("username"));

            }
//            return true;

            // Execute the query and check if a result is found
            return preparedStatement.executeQuery().isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
