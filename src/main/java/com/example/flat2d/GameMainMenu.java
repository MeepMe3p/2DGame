package com.example.flat2d;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.example.flat2d.DesignPatterns.User;
import com.example.flat2d.Misc.Database;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx_plus_willpower.utilities.DatabaseUtilities;
import javafx_plus_willpower.utilities.SceneUtilities;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static javafx_plus_willpower.utilities.DatabaseUtilities.loggedIn;

public class GameMainMenu extends FXGLMenu {
    Text tName;
    VBox vbMainMenu, vbAccount, vbSignIn;

    public static int GAME_STATE = 1;
    public GameMainMenu() {
        super(MenuType.MAIN_MENU);
        Image img = image("background/backg.jpg");
        Texture bg = new Texture(img);
//
//        tName = new Text("Name: My name");
//        tName.setTranslateX(700);
//        tName.setTranslateY(50);
//        tName.getText().setFil



//      IMPLEMENTATIONS FOR BUTTONS
        customMenuButton btnPlayGame = new customMenuButton("PLAY", ()->{
            fireNewGame();
//            getGameController().resumeEngine();
            GAME_STATE = 0;
//            System.out.println("hello");
        });
        customMenuButton btnAccount = new customMenuButton("Account", ()->{
            vbAccount.setVisible(true);
            vbMainMenu.setVisible(false);
        });
        customMenuButton btnMultiplayer= new customMenuButton("MULTIPLAYER", ()->{
                GAME_STATE = 1;
////            System.out.println("yaay");
////               new GameApp();
//            //TODO chat system here for now kay muopen r ashag new scene idk how to exit the game and open again
//            Chatbox cb = new Chatbox();
//            Stage stage = new Stage();
//            Scene sc = new Scene(cb,126,555);
//            stage.setScene(sc);
//            stage.show();
////            getGameController().pauseEngine();
//            getGameController().exit();
            try {
                // Load the existing FXML file
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx_plus_willpower/Sign_In.fxml"));
                Parent root = loader.load();
                // Create the main scene
                Scene mainScene = new Scene(root);
                mainScene.setFill(Color.TRANSPARENT);
                primaryStage.initStyle(StageStyle.TRANSPARENT);
                // Set the main scene for the primary stage
                primaryStage.setScene(mainScene);

                // Set the title for the primary stage
                primaryStage.setTitle("Sign In");

                // Show the primary stage
                primaryStage.show();

                // Open a new stage with a new scene
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        customTextField tfUsername = new customTextField("Username");
        customTextField tfPassword = new customTextField("Password");

//       ======================= ACCOUNT VBOX BUTTONS ================================
        customMenuButton btnExit = new customMenuButton("EXIT", this::fireExit);
        customMenuButton btnLogin = new customMenuButton("Log In",()->{
//            System.out.println("Login account");
            User user = User.getInstance();
            System.out.println("Tf: "+tfUsername.getInput()+"  "+ tfPassword.getInput());
            if(DatabaseUtilities.userCheckerMethod(tfUsername.getInput(),tfPassword.getInput())){
//                user.setUsername(tfUsername.getInput());
//                user.setUserId(1);
                System.out.println("fuck");
            }
                System.out.println("aaId: "+ user.getUserId()+ "Username: "+ user.getUsername());

        });
        customMenuButton btnRegister = new customMenuButton("Register Account",()->{
            System.out.println("Register Account");
            String username = tfUsername.getInput();
            String password = tfPassword.getInput();
            System.out.println("Username: "+username);
            System.out.println("Password: "+password);

//          TODO: not yet done i will still do conditions and shits wid dis but hey atleast it successfully registers
            Database.createUser(username,password);


        });
        customMenuButton btnReturn = new customMenuButton("Return",()->{
            vbAccount.setVisible(false);
            vbMainMenu.setVisible(true);
        })
//       ===================================================================

;
//        box.set
/*IMPLEMENTATIONS FOR VBOXES THE THINGIES THAT SHOW UP WHEN YOU INTERACT LOL
* vbMainMenu = the first thing you will see once you open the game
* vbRegister = the register form
* vbSignIn = the sign_in form
* */

        vbMainMenu = new VBox(10,btnPlayGame,btnAccount,btnMultiplayer,btnExit);
        vbMainMenu.setTranslateY(500);
        vbMainMenu.setTranslateX(50);


//      Account Related Stuff for sign in and register

        vbAccount = new VBox(10,
                tfUsername,
                tfPassword,
                btnLogin,
                btnRegister,
                btnReturn);
        vbAccount.setTranslateY(500);
        vbAccount.setTranslateX(50);
        vbAccount.setVisible(false);
// ===================================
        StackPane menu = new StackPane(vbMainMenu, vbAccount);
//        menu.setTranslateX(360);
//        menu.setTranslateY(360);

        getContentRoot().getChildren().add(bg);
        getContentRoot().getChildren().addAll(menu/*,tName*/);


    }
    private static class customMenuButton extends StackPane {
        String name;
        Runnable action;
        Text text;
        Rectangle selector;
        public customMenuButton(String name, Runnable action) {
            this.name = name;
            this.action = action;
            selector = new Rectangle(8, 20,Color.RED);
            text = FXGL.getUIFactoryService().newText(name, Color.YELLOW,20);
            getChildren().addAll(text,selector);
            setOnMouseClicked(e->{
                action.run();
                System.out.println("clicked");
            });
        }
    }
    public static class customTextField extends StackPane{
        private String name;
        TextField textField;
        Text incorrect;
        Text username_taken;
        StackPane error_pane;

        public customTextField(String name) {
            this.name = name;

            textField = new TextField();
            textField.setPromptText(name);
            textField.setMaxWidth(300);

            incorrect = FXGL.getUIFactoryService().newText("Username or Password is incorrect", Color.RED,10);
            incorrect.setTranslateX(5);
            incorrect.setVisible(false);

            username_taken = FXGL.getUIFactoryService().newText("Username is already taken",Color.RED,10);
            username_taken.setVisible(false);

            error_pane = new StackPane();
            error_pane.getChildren().addAll(incorrect,username_taken);
            error_pane.setAlignment(Pos.CENTER);

            VBox box = new VBox();
            box.getChildren().addAll(textField,error_pane);
            getChildren().addAll(box);

        }
        public String getInput(){
            return textField.getText();
        }
    }

}
