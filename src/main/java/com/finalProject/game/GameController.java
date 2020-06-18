package com.finalProject.game;

import com.finalProject.exceptions.WrongWindowSizeException;
import com.finalProject.game.bullets.Hit;
import com.finalProject.level.Level;
import com.finalProject.level.PlantType;
import com.finalProject.ui.User;
import com.finalProject.ui.Main;
import com.finalProject.screenHandler.ControlledScreen;
import com.finalProject.screenHandler.ScreensController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

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
    private StackPane gameArea;
    @FXML
    private Pane hitLayer;
    @FXML
    private GridPane fieldLayer;
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

    public void releaseZombie(Zombie zombie) {
        // TODO: visualize zombie
        System.out.println("zombie released: " + zombie);
    }

    public void removePlant(Plant plant) {
        System.out.println(gameArea.getChildren());
    }

    public void releaseHit(Hit hit) {
        //TODO: Stack pane
        ImageView bullet = new ImageView(new Image(hit.getImageSrc(), hit.getMaxPicSize(), hit.getMaxPicSize(), true, true));
//        System.out.println(hit.getParent().getCol() + ":" + hit.getParent().getRow());
        bullet.setX(65 + hit.getOffsetX() + hit.getParent().getCol() * 18);
        bullet.setY(15 + hit.getOffsetY() + hit.getParent().getRow() * 22);
        bullet.setTranslateX(bullet.getX());
        bullet.setTranslateY(bullet.getY());
        Timeline t = new Timeline(new KeyFrame(Duration.millis(25), e -> {
//            System.out.println("updatujem poziciu -> " + bullet.getX() + ":" + bullet.getY());
            bullet.setX(bullet.getX() + hit.getSpeed());
//            bullet.setY(bullet.getY() + 0.2);
            bullet.setTranslateX(bullet.getX());
            bullet.setTranslateY(bullet.getY());
        }));
        t.setCycleCount(200);
        t.setOnFinished(event -> {
            hitLayer.getChildren().remove(bullet);
        });
        if (PlantType.ifFlower(hit.getParent())) {
            bullet.setOnMouseReleased(click -> {
                try {
                    amount.setText(Integer.parseInt(amount.getText()) + PlantType.getDamagePerHit(hit.getParent().getType()) + "");
                    t.stop();
                    hitLayer.getChildren().remove(bullet);
                } catch (Exception e) {
                    System.out.println("can't pick up the sun");
                }
            });
        }
        try { Thread.sleep(3000); } catch (InterruptedException ie) { System.out.println("sleeping interrupted at creating hit"); }
        Platform.runLater(() -> hitLayer.getChildren().add(bullet));
        t.play();
    }

    private void loadLevel() {
        System.out.println("loading level: " + current.toString());
        gameArea.setStyle("-fx-background-image: url('" + current.getBgImgSrc() + "'); -fx-background-size: cover");
        try{
            loadCards();
            loadLawnMowers();
            createGameGrid();
            current.start();
        } catch (Exception e) {
            System.out.println("pruser pri handlovani hry -> " + e.getMessage());
        }
    }

    private void createGameGrid() throws WrongWindowSizeException {
        fieldLayer = new GridPane();
        fieldLayer.setGridLinesVisible(true);
        hitLayer = new Pane();
        hitLayer.setStyle("-fx-border-color: red;");

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
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                    try {
                        Plant plant = (Plant) dragEvent.getDragboard().getContent(DataFormat.lookupMimeType("plant"));
                        plant.setCellPos(finalRow, finalCol);
                        plant.setController(this);
                        int newAmount = Integer.parseInt(amount.getText()) - plant.getCost();
                        if (cell.getChildren().isEmpty() && newAmount >= 0) {
                            System.out.println("plant dropped at " + cell.getId() + ": " + plant);
                            ImageView view = new ImageView();
                            view.setImage(new Image(plant.getImageSrc(), CellSize.getCharacterHeight(size) * 0.7, CellSize.getCharacterHeight(size) * 0.9, true, true));
                            cell.getChildren().add(view);
                            plant.start();
                            amount.setText(newAmount + "");
                        }
                    } catch (Exception e) {
                        System.out.println("problem when dropping plant -> " + e.getMessage());
                    }
                    dragEvent.consume();
                });
                cell.setOnDragOver(dragEvent -> {
//                    Main.sc.setCursor(Cursor.CLOSED_HAND);
//                    cell.setCursor(Cursor.CLOSED_HAND);
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                    dragEvent.consume();
                });

                fieldLayer.add(cell, col, row);
            }
        }

        fieldLayer.setPadding(new Insets(33, 10, 8, 110));
        gameArea.getChildren().addAll(fieldLayer, hitLayer);
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
        for (Plant plant : current.getPlantTypes()) {
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
//                Main.sc.setCursor(Cursor.CLOSED_HAND);
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
                    gameArea.getChildren().remove(fieldLayer);
                    gameArea.getChildren().add(fieldLayer);
                }
                db.setContent(content);

                event.consume();
            });
            col.setOnDragEntered(dragEvent -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
                System.out.println("drag entered");
                dragEvent.consume();
            });
//            col.setOnDragExited(dragEvent -> {
//                Main.sc.setCursor(Cursor.CLOSED_HAND);
//                System.out.println("drag exited");
//            });
            col.setOnDragDone(dragEvent -> {
                Main.sc.setCursor(Cursor.DEFAULT);
                System.out.println("drag done");
                gameArea.getChildren().remove(hitLayer);
                gameArea.getChildren().add(hitLayer);
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
        current.setController(this);

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
