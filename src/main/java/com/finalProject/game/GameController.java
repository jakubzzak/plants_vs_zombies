package com.finalProject.game;

import com.finalProject.exceptions.WrongWindowSizeException;
import com.finalProject.level.Level;
import com.finalProject.ui.User;
import com.finalProject.ui.Main;
import com.finalProject.screenHandler.ControlledScreen;
import com.finalProject.screenHandler.ScreensController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class GameController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;
    Level current;

    WindowSize size = WindowSize.SMALL;

    @FXML
    private VBox screen;

    @FXML
    private VBox content;

    @FXML
    private HBox gameArea;
    @FXML
    private HBox toolbarArea;
    @FXML
    private HBox cardsArea;
    @FXML
    private VBox walletArea;

    @FXML
    private Menu fileBtnLabel;
    @FXML
    private Menu editBtnLabel; // set ratio
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
        toolbarArea.setStyle("-fx-background-color: chocolate; -fx-border-color: brown; -fx-border-width: 3; -fx-background-radius: 15; -fx-border-radius: 5;");
//        toolbarArea.setPadding(new Insets(3, 0, 3, 0));
        // wallet
        walletArea.setStyle("-fx-background-radius: 15; -fx-border-width: 3; -fx-border-color: brown; -fx-border-radius: 5; -fx-background-color: lightblue;");
//        walletArea.setPadding(new Insets(8, 10, 8, 10));

        ImageView walletView = new ImageView();
        walletView.setImage(new Image("/pics/SUN.png"));
        walletView.setPreserveRatio(true);
        walletView.setSmooth(true);
        walletView.setFitHeight(toolbarArea.getPrefHeight()*0.4);

        Label amount = new Label(user.getWalletAmount() + "");

        walletArea.getChildren().addAll(walletView, amount);
        // all plants
        for (Plant plant : current.getPlants()) {
            VBox col = new VBox();
            col.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 5; -fx-padding: 1; -fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: saddlebrown");
            col.setMinWidth(50);
            // img
            ImageView view = new ImageView();
            view.setImage(new Image("/pics/plants/" + plant.getType() + ".png"));
            view.setPreserveRatio(true);
            view.setSmooth(true);
            view.setFitHeight(toolbarArea.getPrefHeight()*0.4);
            // btn
            Label cost = new Label(plant.getCost() + "");
            col.setCursor(Cursor.OPEN_HAND);
            col.setOnDragDropped(dragEvent -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
                System.out.println("drag droped");
            });
            col.setOnDragExited(dragEvent -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
                System.out.println("drag exited");
            });
            col.setOnDragEntered(dragEvent -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
                System.out.println("drag entered");
            });
            col.setOnDragDetected(event -> { // works :)
                Main.sc.setCursor(Cursor.CLOSED_HAND);
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = col.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                Node imgView = col.getChildren().get(0);
                if (imgView instanceof ImageView) {
                    Image img = ((ImageView) imgView).getImage();
                    try {
                        img = new Image(img.getUrl(), WindowSize.getCharacterHeight(size), 50, true, true);
                    } catch (WrongWindowSizeException e) {
                        e.printStackTrace();
                    }
                    content.putImage(img);
                    if (DataFormat.lookupMimeType("plant") != null) {
                        content.put(DataFormat.lookupMimeType("plant"), plant);
                    } else {
                        content.put(new DataFormat("plant"), plant);
                    }
                }
                db.setContent(content);

                event.consume();
            });
            col.setOnDragDone(dragEvent -> { // works :)
                Main.sc.setCursor(Cursor.DEFAULT);
                //TODO: check if drop valid, if isValid then subtract budget, else do nothing
                try {
                    Plant nop = (Plant) dragEvent.getDragboard().getContent(DataFormat.lookupMimeType("plant"));
                    System.out.println("drag done and successful");
                    dragEvent.getDragboard().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            col.setAlignment(Pos.CENTER);
            col.getChildren().addAll(view, cost);

            cardsArea.setStyle("-fx-background-radius: 5; -fx-background-color: chocolate;");
            Platform.runLater(() -> cardsArea.getChildren().add(col));
        }
        // TODO: nastuduj dokumentaciu, s kodom nizsie funguju aj drag exitied
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

        screen.setPrefWidth(Main.sc.getWidth());;
        screen.setPrefHeight(Main.sc.getHeight());

        Main.ps.setResizable(false); // match exact dimensions of the window
        Main.ps.setHeight(Main.INIT_HEIGHT);
        Main.ps.setWidth(Main.INIT_WIDTH);
        // TODO: 3 sizes, init | middle | large

//        gameArea.setPrefHeight(284);
//        gameArea.setPrefWidth(482);

        Main.sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
            screen.setPrefWidth((double)newSceneWidth);
            System.out.println("new width -> " + newSceneWidth);
        });
        Main.sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            screen.setPrefHeight((double)newSceneHeight);
        });

        gameArea.heightProperty().addListener((observable, old, newHeight) -> {
            System.out.println("new height -> " + newHeight);
        });
        gameArea.widthProperty().addListener((observable, old, newWidth) -> {
            System.out.println("new width -> " + newWidth);
        });

        gameArea.setOnDragOver(event -> { // works :)
            /* accept it only if it is not dragged from the same node and if it has a string data */
            if (event.getGestureSource() != gameArea &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        // h: 284, w: 482 -> gameScreen
        // h: 540, w: 720
        // h: 540, w: 720
        loadLevel();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
