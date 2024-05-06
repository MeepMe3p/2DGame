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
import javafx.scene.image.ImageView;
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
    VBox vbMainMenu, vbAccount, vbSignIn,vbHighscore;
    HBox hbUserDetails;
    String userLoggedIn;
    String idLoggedIn;



    public static int GAME_STATE = 1;
    public GameMainMenu() {
        super(MenuType.MAIN_MENU);
        Image img = image("background/backg.jpg");
        Texture bg = new Texture(img);



//      IMPLEMENTATIONS FOR BUTTONS
        customMenuButton btnPlayGame = new customMenuButton("PLAY", ()->{
            fireNewGame();
            GAME_STATE = 0;
        });
        customMenuButton btnAccount = new customMenuButton("Account", ()->{
            vbAccount.setVisible(true);
            vbMainMenu.setVisible(false);
        });
        customMenuButton btnMultiplayer= new customMenuButton("MULTIPLAYER", ()->{
                GAME_STATE = 1;
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
                System.out.println("aaId: "+ loggedIn.getUserId()+ "Username: "+ loggedIn.getUsername());

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
 * vbRegister = the register form -------- void
 * vbSignIn = the sign_in form ----------- void
 * vbHighscore = the highscore
 */

//======= VB MAIN MENU - this is the first thing you will see play, account,etc =============== //
        vbMainMenu = new VBox(10,btnPlayGame,btnAccount,btnMultiplayer,btnExit);
        vbMainMenu.setTranslateY(500);
        vbMainMenu.setTranslateX(50);
// ==========================================================================================//

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
// =========================================================================

//=========== HbUserDetails - the thing at the top part ==============================//
        Image user_pic = image("profile-pic.jpg");
        ImageView ivUser_pic = new ImageView(user_pic);
        Text tUser_id = new Text("--");
        Text tUser_name = new Text("No user currently logged in");
        tUser_name.setFill(Color.WHITE);
        tUser_name.setFill(Color.WHITE);
        ivUser_pic.setFitHeight(120);
        ivUser_pic.setPreserveRatio(true);
        if(loggedIn != null){
            tUser_id.setText(String.valueOf(loggedIn.getUserId()));
            tUser_name.setText(loggedIn.getUsername());
            System.out.println(loggedIn);
        }
        hbUserDetails = new HBox(ivUser_pic,tUser_id,tUser_name);
        hbUserDetails.setTranslateX(30);
        hbUserDetails.setTranslateY(30);
        hbUserDetails.setAlignment(Pos.CENTER_LEFT);
// ==================================================================================//

/*      Stack pane where everything is the vboxes are  this is the overall backgdground
        TODO design this
 */
        StackPane menu = new StackPane(vbMainMenu, vbAccount);
        getContentRoot().getChildren().addAll(bg,menu,hbUserDetails);
        menu.setTranslateX(360);
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
