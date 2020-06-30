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


/**
 * Controller for level screen.
 */
public class LevelController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;
    /**
     * List of presented levels.
     */
    List<Level> levels;

    @FXML
    private Menu fileBtnLabel;
    @FXML
    private Menu editBtnLabel;
    @FXML
    private Menu helpBtnLabel;
    @FXML
    private MenuItem userInfo;

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

    /**
     * Loads all levels that were successfully retrieved from files.
     */
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
            Button btn;
            if (user.getLevelStatus(lvl) == null) {
                btn = new Button("Level " + (lvl.getID() + 1));
            } else if (user.getLevelStatus(lvl)) {
                btn = new Button("Win");
                btn.setDisable(true);
            } else {
                btn = new Button("Lose");
                btn.setDisable(true);
            }
            btn.setCursor(Cursor.HAND);
            btn.setOnMouseClicked(mouseEvent -> {
                btn.setDisable(true);
                myController.unloadScreen("game");
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

    /**
     * Closes the whole application.
     * @param event
     * Needed to be presented for the method to be triggered from FXML file. No specific use.
     */
    public void close(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     Initializes current controller.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Main.getUser();
        userInfo.setText("Signed as: " + user.getUsername());

        levels = Main.getLevels();

//        deleteBtn.setDisable(true);
//        helpBtn.setDisable(true);

        fillContent();

//        Main.ps.setHeight(Main.HEIGHT);
//        Main.ps.setWidth(Main.WIDTH);

        Main.ps.setResizable(true);

//        screen.setPrefWidth(Main.sc.getWidth());
//        content.setPrefWidth(Main.sc.getWidth());
//        screen.setPrefHeight(Main.sc.getHeight());


        Main.sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
            screen.setPrefWidth((double)newSceneWidth);
            content.setPrefWidth((double)newSceneWidth);
        });
        Main.sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            screen.setPrefHeight((double)newSceneHeight);
        });

//        userInfo.setText("Signed as: " + user.getUsername());

    }

    /**
     Sets parent of the current screen.
     */
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
