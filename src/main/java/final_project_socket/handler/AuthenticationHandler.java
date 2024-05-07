package final_project_socket.handler;

import com.example.flat2d.DesignPatterns.User;
import final_project_socket.database.MySQLConnector;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
    #########################################################################################################

    This class manages the logic for database queries.
    (See how: https://youtu.be/ltX5AtW9v30?t=2128)

    #########################################################################################################
*/

public class AuthenticationHandler {
    public static User loggedIn; // <-- Temporary
    public static void signUpUser(ActionEvent event, String username, String password) {
        AlertHandler alert = new AlertHandler();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM tblusers WHERE username = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO tblusers (username, password) VALUES (?, ?)")) {
            psCheckUserExists.setString(1, username);

            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                if (resultSet.next()) {
                    alert.error("Sign up attempt", "User already exists!");
                    return;
                }
            }

            psInsert.setString(1, username);
            psInsert.setString(2, password);
            psInsert.executeUpdate();

            SceneHandler.changeScene(event, "/final_project_socket/fxml/Sign_In.fxml", "Sign In!", null, null);
        } catch (SQLException e) {
            e.printStackTrace();
            alert.error("SQLException", "An error occurred. Please try again.");
        }
    }

    public static void signInUser(ActionEvent event, String username, String password) {
        AlertHandler alert = new AlertHandler();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT password, userid , is_online FROM tblusers WHERE username = ?")) {
            ps.setString(1, username);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (!resultSet.next()) {
                    alert.error("Failed to sign in", "Provided credentials are incorrect!");
                    return;
                }

                String retrievedPassword = resultSet.getString("password");
                int userId = resultSet.getInt("userid");
                if (resultSet.getBoolean("is_online")) {
                    alert.error("Failed to sign in", "User is already signed in!");
                    return;
                }

                Socket socket = new Socket("localhost", 9806);
                if (retrievedPassword.equals(password)) {
                    try (PreparedStatement statement = connection.prepareStatement("UPDATE tblusers SET is_online = ? WHERE userid = ?")) {
                        statement.setBoolean(1, true);
                        statement.setInt(2, userId);
                        statement.executeUpdate();
                    }
                    SceneHandler.changeScene(event, "/final_project_socket/fxml/Chat_Box.fxml", "Welcome!", username, socket);
                } else {
                    alert.error("Failed to sign in", "Provided credentials are incorrect!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alert.error("SQLException", "An error occurred. Please try again.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // This Method is temporary
    public static Boolean userCheckerMethod(String username, String password) {
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblusers WHERE username = ? AND password = ?")) {
            // Set parameters for the prepared statement
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute the query and check if a result is found
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("password").equals(password) && rs.getString("username").equals(username)){
                    loggedIn = User.getInstance();
                    loggedIn.setUserId(rs.getInt("userid"));
                    loggedIn.setUsername(rs.getString("username"));
                    System.out.println("Database Util ID: "+ loggedIn.getUserId()+ "Username: "+ loggedIn.getUsername());
                    return true;
                }
            }
            return ps.executeQuery().isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}