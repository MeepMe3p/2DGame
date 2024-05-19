package final_project_socket.fxml_controller;

import final_project_socket.handler.AuthenticationHandler;
import final_project_socket.handler.SceneHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    private Button btn_submit, btn_signup, btn_close;
    @FXML
    private TextField txtf_username;
    @FXML
    private PasswordField passf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_submit.setOnAction(event -> AuthenticationHandler.signInUser(event, txtf_username.getText(), passf_password.getText()));
        btn_close.setOnAction(event -> {
            Stage stage = (Stage) btn_close.getScene().getWindow();
            stage.close();
        });
        btn_signup.setOnAction(event -> SceneHandler.changeScene(event, "/final_project_socket/fxml/Sign_Up.fxml", "Sign Up!", null, null, null));
    }
}

