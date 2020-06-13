package com.finalProject.game;

import com.finalProject.exceptions.WrongWindowSizeException;
import com.finalProject.level.Level;
import com.finalProject.ui.User;
import com.finalProject.ui.Main;
import com.finalProject.screenHandler.ControlledScreen;
import com.finalProject.screenHandler.ScreensController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.net.URL;
import java.util.ResourceBundle;


public class GameController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;
    Level current;

    CellSize size = CellSize.SMALL;

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
    private Label amount;

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
            loadLawnMowers();
            createGameGrid();
            // TODO: start game
        } catch (Exception e) {
            System.out.println("pruser pri handlovani hry -> " + e.getMessage());
        }
    }

    private void createGameGrid() throws WrongWindowSizeException {
        GridPane grid = new GridPane();

        for (int row=0; row<5; row++) {
            for (int col=0; col<9; col++) {
                HBox cell = new HBox();
                cell.setPrefSize(CellSize.getCharacterHeight(size), CellSize.getCharacterHeight(size));
                cell.setId("cell#" + (row * 9 + col));
                cell.setAlignment(Pos.CENTER);

                int finalRow = row;
                int finalCol = col;
                cell.setOnDragDropped(dragEvent -> {
                    Main.sc.setCursor(Cursor.DEFAULT);
                    try {
                        Plant plant = (Plant) dragEvent.getDragboard().getContent(DataFormat.lookupMimeType("plant"));
                        plant.setCellPos(finalRow, finalCol);
                        System.out.println("plant droped at " + cell.getId() + ": " + plant);
                        int newAmount = Integer.parseInt(amount.getText()) - plant.getCost();
                        if (cell.getChildren().isEmpty() && newAmount >= 0) {
                            ImageView view = new ImageView();
                            view.setImage(new Image(plant.getImageSrc(), CellSize.getCharacterHeight(size) * 0.7, CellSize.getCharacterHeight(size) * 0.9, true, true));
                            cell.getChildren().add(view);
                            plant.start();
                            amount.setText(newAmount + "");
                        }
                    } catch (Exception e) {
                        System.out.println("problem when dropping plant -> " + e.getMessage());
                    }
                });

                grid.add(cell, col, row);
            }
        }

        grid.setPadding(new Insets(33, 10, 8, 110));
        gameArea.getChildren().add(grid);
    }

    private void loadLawnMowers() {
        if (current.hasLawnMower()) {
            // TODO: load lawnMowers
        }
    }

    private void loadCards() {
        toolbarArea.setStyle("-fx-background-color: chocolate; -fx-border-color: brown; -fx-border-width: 3; -fx-background-radius: 15; -fx-border-radius: 5;");
        walletArea.setStyle("-fx-background-radius: 15; -fx-border-width: 3; -fx-border-color: brown; -fx-border-radius: 5; -fx-background-color: lightblue;");

        ImageView walletView = new ImageView();
        walletView.setImage(new Image("/pics/SUN.png"));
        walletView.setPreserveRatio(true);
        walletView.setSmooth(true);
        walletView.setFitHeight(toolbarArea.getPrefHeight()*0.4);

        amount = new Label(user.getWalletAmount() + "");

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
            col.setOnDragDetected(event -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
                Dragboard db = col.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                Node imgView = col.getChildren().get(0);
                if (imgView instanceof ImageView) {
                    Image img = ((ImageView) imgView).getImage();
                    try {
                        img = new Image(img.getUrl(), CellSize.getCharacterHeight(size), 50, true, true);
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

            col.setAlignment(Pos.CENTER);
            col.getChildren().addAll(view, cost);

            cardsArea.setStyle("-fx-background-radius: 5; -fx-background-color: chocolate;");
            Platform.runLater(() -> cardsArea.getChildren().add(col));
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

        screen.setPrefWidth(Main.sc.getWidth());;
        screen.setPrefHeight(Main.sc.getHeight());

        Main.ps.setResizable(false); // match exact dimensions of the window
        Main.ps.setHeight(Main.INIT_HEIGHT);
        Main.ps.setWidth(Main.INIT_WIDTH);
        // TODO: 3 sizes, init | middle | large

        Main.sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
            screen.setPrefWidth((double)newSceneWidth);
        });
        Main.sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            screen.setPrefHeight((double)newSceneHeight);
        });
        // h: 284, w: 482 cell: 48
        // h: 540, w: 720 cell:
        // h: 540, w: 720 cell:
        loadLevel();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
