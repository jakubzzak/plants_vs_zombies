package com.finalProject.game;

import com.finalProject.ui.User;
import com.finalProject.ui.Main;
import com.finalProject.screenHandler.ControlledScreen;
import com.finalProject.screenHandler.ScreensController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class GameController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;

    @FXML
    private VBox content;

    @FXML
    private Menu fileBtnLabel;
    @FXML
    private Menu editBtnLabel;
    @FXML
    private Menu helpBtnLabel;
    @FXML
    private Menu userInfo;

    @FXML
    private MenuItem closeBtn;
    @FXML
    private MenuItem deleteBtn;
    @FXML
    private MenuItem helpBtn;

//    public void loadNewMessage(Message message) {
//        if (clientNameLabel.getText().isEmpty()) {
//            clientNameLabel.setText("signed as: " + client.getUsername());
//        }
//        Label label;
//        String timeBuilder = "[" + (message.getTime().toString().length() == 2 ? message.getTime() + ":00:00" : message.getTime().toString().length() == 5 ? message.getTime() + ":00" : message.getTime()) + "]";
//        if(client.getLocalPort() != message.getPort()) {
//            label = new Label(timeBuilder + " " + message.getUsername() + " > " + message.getText());
//            label.setAlignment(Pos.CENTER_LEFT);
//        } else {
//            label = new Label(message.getText() + " " + timeBuilder);
//            label.setAlignment(Pos.CENTER_RIGHT);
//        }
//
//        label.setPadding(new Insets(0, 10, 0, 10));
//        label.setTextFill(Color.ORANGE);
//        label.setFont(new Font("Arial Hebrew Bold", 13));
//        label.setMaxWidth(Main.getPrimaryStage().getWidth());
//
//        label.setOnMouseClicked(mouseEvent -> {
//            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//                if (mouseEvent.getClickCount() == 2) {
//                    label.setVisible(false);
//                    String[] x = label.getText().split(" ");
//                    StringBuilder output = new StringBuilder();
//                    if (x[0].matches("\\[\\d{2}:\\d{2}:\\d{2}]")) { // delivered smg
//                        for (int i = 3; i < x.length; i++) {
//                            output.append(" ").append(x[i]);
//                        }
//                    } else { // sent msg
//                        for (int i = 0; i < x.length-1; i++) {
//                            output.append(" ").append(x[i]);
//                        }
//                    }
//                    TextField openLabel = new TextField(output.toString().trim());
//                    openLabel.setPrefHeight(label.getHeight() + 10);
//                    content.getChildren().add(openLabel);
//                    openLabel.requestFocus();
//                    openLabel.selectAll();
//
//                    openLabel.setOnKeyPressed(event -> {
//                        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
//                            content.getChildren().remove(openLabel);
//                            label.setVisible(true);
//                        }
//                    });
//                }
//            }
//        });
//
//        Platform.runLater(() -> content.getChildren().add(label));
//    }

    public void close(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Main.getUser();
        user.setGameController(this);

        deleteBtn.setDisable(true);
        helpBtn.setDisable(true);

        userInfo.setText("Signed as: " + user.getUsername());
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
