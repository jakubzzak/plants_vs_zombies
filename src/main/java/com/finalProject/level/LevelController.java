package com.finalProject.level;

import com.finalProject.screenHandler.ControlledScreen;
import com.finalProject.screenHandler.ScreensController;
import com.finalProject.ui.Main;
import com.finalProject.ui.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LevelController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;
    List<Level> levels;

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

    @FXML
    private HBox content;

    @FXML
    private VBox screen;

    private void fillContent() {
        content.setSpacing(20);
        for (Level lvl : levels) {
            VBox col = new VBox();
            // img
            ImageView view = new ImageView();
            view.setImage(lvl.getCoverImg());
            view.setPreserveRatio(true);
            view.setSmooth(true);
//            view.setFitWidth(300);
            view.setFitHeight(200);
            // btn
            Button btn = new Button("Level " + (lvl.getID() + 1));
            btn.setCursor(Cursor.HAND);
            btn.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    Main.setCurrentLevel(lvl);
                    Main.getScreenContainer().loadScreen(Main.gameScreenID, Main.gameScreenSOURCE);
                    myController.setScreen("game");
                    System.out.println("success, entering game");
                }
            });

            col.setAlignment(Pos.CENTER);
            col.setSpacing(10);
            col.getChildren().add(view);
            col.getChildren().add(btn);

            Platform.runLater(() -> content.getChildren().add(col));
        }
    }

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
        userInfo.setText("Signed as: " + user.getUsername());

        levels = Main.getLevels();

        deleteBtn.setDisable(true);
        helpBtn.setDisable(true);

        fillContent();

        screen.setPrefWidth(Main.sc.getWidth());
        content.setPrefWidth(Main.sc.getWidth());
        screen.setPrefHeight(Main.sc.getHeight());

        Main.sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
            screen.setPrefWidth((double)newSceneWidth);
            content.setPrefWidth((double)newSceneWidth);
        });
        Main.sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            screen.setPrefHeight((double)newSceneHeight);
        });

//        userInfo.setText("Signed as: " + user.getUsername());

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
