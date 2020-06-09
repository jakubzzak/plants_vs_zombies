package com.finalProject.game;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.level.Level;
import com.finalProject.level.PlantType;
import com.finalProject.ui.User;
import com.finalProject.ui.Main;
import com.finalProject.screenHandler.ControlledScreen;
import com.finalProject.screenHandler.ScreensController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;
    Level current;

    @FXML
    private VBox screen;

    @FXML
    private VBox content;

    @FXML
    private HBox gameArea;
    @FXML
    private HBox pickPlantArea;

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

    private void loadLevel() {
        System.out.println("loading level: " + current.toString());
        gameArea.setStyle("-fx-background-image: url('" + current.getBgImgSrc() + "'); -fx-background-size: cover");

        try{
            loadCards();
            if (current.hasLawnMower()) {
                // TODO: load lawnMowers
            }
            // TODO: start game
        } catch (Exception e) {
            System.out.println("pruser pri handlovani hry -> " + e.getMessage());
        }
    }

    private void loadCards() {
        for (Plant plant : current.getPlants()) {
            VBox col = new VBox();
            col.setStyle("-fx-background-radius: 10; -fx-border-width: 10; -fx-border-color: brown;");
            // img
            ImageView view = new ImageView();
            view.setImage(new Image("/pics/plants/" + plant + ".png"));
            view.setPreserveRatio(true);
            view.setSmooth(true);
            view.setFitHeight(100);
            // btn
            Label cost = new Label(plant.getCost() + "");
            col.setCursor(Cursor.OPEN_HAND);
            col.setOnDragEntered(dragEvent -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
            });
            col.setOnDragDone(dragEvent -> {
                Main.sc.setCursor(Cursor.DEFAULT);
                //TODO: check if drop valid, if isValid then subtract budget, else do nothing
            });

            col.setAlignment(Pos.CENTER);
//            col.setSpacing(0);
            col.getChildren().add(view);
            col.getChildren().add(cost);

            Platform.runLater(() -> pickPlantArea.getChildren().add(col));
        }
    }

    public void close(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Main.getUser();
        userInfo.setText("Signed as: " + user.getUsername());

        current = Main.getCurrentLevel(); // pointless but nice :)

        user.setGameController(this);

        deleteBtn.setDisable(true);
        helpBtn.setDisable(true);

        screen.setPrefWidth(Main.sc.getWidth());
//        content.setPrefWidth(Main.sc.getWidth());
        screen.setPrefHeight(Main.sc.getHeight());

        Main.sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
            screen.setPrefWidth((double)newSceneWidth);
        });
        Main.sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            screen.setPrefHeight((double)newSceneHeight);
        });

        loadLevel();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
