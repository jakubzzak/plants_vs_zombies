package com.finalProject.welcome;

import com.finalProject.ui.Main;
import com.finalProject.ui.User;
import com.finalProject.screenHandler.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for welcome screen.
 */
public class WelcomeScreenController implements Initializable, ControlledScreen {

    /**
     * Screen controller for handling screens.
     */
    ScreensController myController;
    /**
     * User data.
     */
    User user;

    /**
     * Main screen.
     */
    @FXML
    private VBox screen;
    /**
     * Label for username.
     */
    @FXML
    private Label usernameLabel;
    /**
     * Username input.
     */
    @FXML
    private TextField username;
    /**
     * Message shown if input not satisfied.
     */
    @FXML
    private Label errorMessage;
    /**
     * Button to enter the game.
     */
    @FXML
    private Button enterBtn;

    /**
     Checks if name input is valid.
     */
    private boolean nameIsValid(String input) {
        return input.length() >= 3;
    }


    /**
     Triggers on enter pressed or button 'enter' down.
     */
    public void enter(ActionEvent event) {
        if (!nameIsValid(username.getText())) { // parsing username
            username.selectAll();
            errorMessage.setText("Username is too short, 3 signs required!");
            return;
        }
        user.setUsername(username.getText());
        Main.getScreenContainer().loadScreen(Main.lvlScreenID, Main.lvlScreenSOURCE);
        Main.getScreenContainer().setScreen("level");
        System.out.println("success, entering levels");
        Main.getScreenContainer().unloadScreen("welcome");
    }

    /**
     Initializes current controller.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Main.getUser();

        enterBtn.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                enter(new ActionEvent());
            }
        });
        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                enter(new ActionEvent());
            }
        });

        Main.sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
            screen.setPrefWidth((double)newSceneWidth);
        });
        Main.sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            screen.setPrefHeight((double)newSceneHeight);
        });
    }

    /**
     Sets parent of the current screen.
     */
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
