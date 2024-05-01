package javafx_plus_willpower.controller;

import com.example.flat2d.DesignPatterns.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx_plus_willpower.callbacks.UIUpdateCallback;
import javafx_plus_willpower.record.ChatConfig;
import javafx_plus_willpower.record.SignUpConfig;
import javafx_plus_willpower.utilities.DatabaseUtilities;
import javafx_plus_willpower.utilities.SceneUtilities;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx_plus_willpower.utilities.DatabaseUtilities.loggedIn;

public class SignInController implements Initializable, UIUpdateCallback {
    private static final SignUpConfig SIGN_UP_CONFIG = SignUpConfig.createWithDefaults();
    private static final ChatConfig CHAT_CONFIG = ChatConfig.createWithDefaults();
    @FXML
    private TextField textField_Username;
    @FXML
    private PasswordField passwordField_password;
    @FXML
    private Button btn_ButtonConnect;
    @FXML
    private Button btn_SignUp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_ButtonConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Query in database and find the textField_Username and passwordField_password.
                // If success, go to main.fxml
                if(DatabaseUtilities.userCheckerMethod(textField_Username.getText(), passwordField_password.getText())){
                    // Switching to the Chat.fxml scene will automatically instantiate the ChatController class.
//                    SceneUtilities.changeScene(actionEvent, CHAT_CONFIG.fxmlFilePath(), CHAT_CONFIG.title(), null, textField_Username.getText(), (loader)->{
//                        onUIUpdated(loader);
//                    });
//                    User user = User.getInstance();
                    System.out.println("Id: "+ loggedIn.getUserId()+ "Username: "+ loggedIn.getUsername());
//
//                    System.out.println("nisulod");
                }
            }
        });
        btn_SignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneUtilities.changeScene(actionEvent, SIGN_UP_CONFIG.fxmlFilePath(), SIGN_UP_CONFIG.title(), null, null, null);
            }
        });
    }
    @Override
    public void onUIUpdated(FXMLLoader loader) {
        ChatController controller = loader.getController();
        controller.connectAction();
    }
}
