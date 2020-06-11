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

public class WelcomeScreenController implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;

    @FXML
    private VBox screen;
    @FXML
    Label usernameLabel;
    @FXML
    TextField username;

    @FXML
    Label errorMessage;

    @FXML
    Button enterBtn;


    private boolean nameIsValid(String input) {
        return input.length() >= 3;
    }

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

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
