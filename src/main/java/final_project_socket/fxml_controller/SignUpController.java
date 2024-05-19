package final_project_socket.fxml_controller;

import final_project_socket.handler.AuthenticationHandler;
import final_project_socket.handler.AlertHandler;
import final_project_socket.handler.SceneHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button btn_submit, btn_signin, btn_close;
    @FXML
    private TextField txtf_username;
    @FXML
    private PasswordField passf_password, passf_rpassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_submit.setOnAction(event -> {
            AlertHandler alert = new AlertHandler();
            // Check if all credential are all filled in
            if (!txtf_username.getText().trim().isEmpty() &&
                    !passf_password.getText().trim().isEmpty() &&
                    !passf_rpassword.getText().trim().isEmpty()) {
                // Check if password and repeat password are matched
                if (!passf_password.getText().equals(passf_rpassword.getText())) {
                    alert.error("ERROR", "Password does not match!");
                } else {
                    AuthenticationHandler.signUpUser(event, txtf_username.getText(), passf_password.getText());
                }
            } else {
                alert.error("ERROR", "Please fill in all credentials.");
            }
        });

        btn_close.setOnAction(event -> {
            Stage stage = (Stage) btn_close.getScene().getWindow();
            stage.close();
        });

        btn_signin.setOnAction(event -> SceneHandler.changeScene(event, "/final_project_socket/fxml/Sign_In.fxml", "Sign In!", null, null, null));
    }
}