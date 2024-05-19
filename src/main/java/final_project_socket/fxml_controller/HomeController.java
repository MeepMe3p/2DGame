package final_project_socket.fxml_controller;

import final_project_socket.handler.AuthenticationHandler;
import final_project_socket.handler.SceneHandler;
import final_project_socket.socket.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button btn_play, btn_chat, btn_disconnect;
    private Socket socket;
    private Client client;
    private String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // runLater() ensures that username retrieves the value from the FXML.
        // (See link: https://stackoverflow.com/questions/68363535/passing-data-to-another-controller-in-javafx).
        Platform.runLater(() -> {
            Parent root = btn_disconnect.getParent();
            Stage stage = (Stage) root.getScene().getWindow();


            btn_play.setOnAction(actionEvent -> {
                stage.close();
                AuthenticationHandler.disconnect(username); // Temporary
                username = "";
            });


            btn_chat.setOnAction(actionEvent -> {
                SceneHandler.changeScene(actionEvent, "/final_project_socket/fxml/Chat_Box.fxml", "Chat", username, socket, client);
                username = "";
            });

            btn_disconnect.setOnAction(actionEvent -> {
                AuthenticationHandler.disconnect(username);
                username = "";
                SceneHandler.changeScene(actionEvent, "/final_project_socket/fxml/Sign_In.fxml", "Log in", null, null, null);
            });

            stage.setOnCloseRequest(actionEvent -> {
                if (!username.isEmpty()) {
                    AuthenticationHandler.disconnect(username);
                }
            });
        });
    }

    public void setUserInformation(String username, Socket socket, Client client) {
        this.username = username;
        this.socket = socket;
        this.client = client;
    }
}