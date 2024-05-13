package final_project_socket.handler;

import com.example.flat2d.DesignPatterns.User;
import final_project_socket.database.MySQLConnector;
import final_project_socket.socket.Client;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

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
             PreparedStatement psInsertUser = connection.prepareStatement("INSERT INTO tblusers (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psInsertProfile = connection.prepareStatement("INSERT INTO tblprofile (player_id, player_name) VALUES (?, ?)")) {

            // Check if the username already exists
            psCheckUserExists.setString(1, username);
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                if (resultSet.next()) {
                    alert.error("Sign up attempt", "User already exists!");
                    return;
                }
            }

            psInsertUser.setString(1, username);
            psInsertUser.setString(2, password);
            psInsertUser.executeUpdate();

            try (ResultSet generatedKeys = psInsertUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int player_id = generatedKeys.getInt(1);
                    psInsertProfile.setInt(1, player_id);
                    psInsertProfile.setString(2, username);
                    psInsertProfile.executeUpdate();
                    alert.information("Success", "You have successfully signed up!");
                    SceneHandler.changeScene(event, "/final_project_socket/fxml/Sign_In.fxml", "Sign In!", null, null, null);
                } else {
                    throw new SQLException("Failed to get generated keys, no userid obtained.");
                }
            }
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

                if (retrievedPassword.equals(password)) {
                    try (PreparedStatement statement = connection.prepareStatement("UPDATE tblusers SET is_online = ? WHERE userid = ?")) {
                        statement.setBoolean(1, true);
                        statement.setInt(2, userId);
                        statement.executeUpdate();
                    }
                    Socket socket = new Socket("localhost", 9806);
                    Client client = new Client(socket, username);
                    SceneHandler.changeScene(event, "/final_project_socket/fxml/Chat_Box.fxml", "Welcome!", username, socket, client);
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