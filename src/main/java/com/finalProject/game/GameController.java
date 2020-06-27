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
import javafx.beans.Observable;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;


public class GameController extends Thread implements Initializable, ControlledScreen {

    ScreensController myController;
    User user;
    Level current;

    CellSize size = CellSize.SMALL;
    Map<Integer, HBox> cells = new HashMap<>();

    @FXML
    private VBox screen;

    @FXML
    private VBox content;

    @FXML
    private StackPane gameArea;
    @FXML
    private Pane hitLayer;
    @FXML
    private Pane zombieLayer;
    @FXML
    private GridPane plantLayer;

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
    private Menu editBtnLabel; // TODO: set ratio
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

    public GridPane getPlantLayer() { return plantLayer; }
    public Pane getHitLayer() { return hitLayer; }

    private void loadLevel() {
        System.out.println("loading level: " + current.toString());
        gameArea.setStyle("-fx-background-image: url('" + current.getBgImgSrc() + "'); -fx-background-size: contain");
        try{
            loadCards();
            createGameArea();
            loadLawnMowers();
            current.run();
        } catch (Exception e) {
            System.out.println("pruser pri handlovani hry -> " + e.getMessage());
        }
    }

    public void releaseZombie(Zombie zombie) {
        ImageView zomb = new ImageView(new Image(zombie.getImageSrc(), zombie.getMaxPicSize(), zombie.getMaxPicSize(), true, true));
        zombie.setImg(zomb);
        zomb.setX(210);
        zomb.setY(17 + zombie.getRow() * 22 - (10));
        zomb.setTranslateX(zomb.getX());
        zomb.setTranslateY(zomb.getY());
        zombie.setAxes(zomb.getX(), zomb.getY());
        addObjectToZombieLayer(zomb);
    }
    public void releaseHit(Hit hit) {
        //TODO: calculate trajectory
        ImageView bullet = new ImageView(new Image(hit.getImageSrc(), hit.getMaxPicSize(), hit.getMaxPicSize(), true, true));
        bullet.setX(hit.getOffsetX() + hit.getParent().getX());
        bullet.setY(15 + hit.getOffsetY() + hit.getParent().getRow() * 22);
        bullet.setTranslateX(bullet.getX());
        bullet.setTranslateY(bullet.getY());
        hit.setImg(bullet);
        hit.setCurrentX(bullet.getX());
        hit.setCurrentY(bullet.getY());
        if (PlantType.isFlower(hit.getParent())) {
            bullet.setOnMouseReleased(click -> {
                try {
                    amount.setText(Integer.parseInt(amount.getText()) + PlantType.getDamagePerHit(hit.getParent().getType()) + "");
                    hit.setDead();
                    removeObjectFromHitLayer(bullet);
                } catch (Exception e) {
                    System.out.println("can't pick up the sun");
                }
            });
        }
        addObjectToHitLayer(bullet);
        current.addHit(hit);
    }

    public void addObjectToZombieLayer(ImageView pic) {
        try{
//            CompletableFuture.supplyAsync(() -> zombieLayer.getChildren().add(pic), Platform::runLater).join();
//            System.out.println("trying to add zombie");
            synchronized (zombieLayer) {
                Platform.runLater(() -> zombieLayer.getChildren().add(pic));
            }
        } catch (Exception e ) {
            System.out.println("add at zombieLayer failed");
        }
    }
    public void removeObjectFromZombieLayer(ImageView pic) {
        try{
//            CompletableFuture.supplyAsync(() -> hitLayer.getChildren().remove(pic), Platform::runLater).join();
            synchronized (zombieLayer) {
//                System.out.println("trying to remove zombie");
                Platform.runLater(() -> zombieLayer.getChildren().remove(pic));
            }
        } catch (Exception e ) {
            System.out.println("remove at zombieLayer failed");
        }
    }

    public void addObjectToHitLayer(ImageView pic) {
        try{
//            CompletableFuture.supplyAsync(() -> hitLayer.getChildren().add(pic), Platform::runLater).join();
            synchronized (hitLayer) {
//                System.out.println("trying to add hit");
                Platform.runLater(() -> hitLayer.getChildren().add(pic));
            }
        } catch (Exception e ) {
            System.out.println("add at hitLayer failed");
        }
    }
    public void removeObjectFromHitLayer(ImageView pic) {
        try{
//            CompletableFuture.supplyAsync(() -> hitLayer.getChildren().remove(pic), Platform::runLater).join();
            synchronized (hitLayer) {
//                System.out.println("trying to remove hit");
                Platform.runLater(() -> hitLayer.getChildren().remove(pic));
            }
        } catch (Exception e ) {
            System.out.println("remove at hitLayer failed");
        }
    }

    public void addObjectToPlantLayer(ImageView pic) {
        try {
            synchronized (plantLayer) {
//                System.out.println("trying to add plant");
                Platform.runLater(() -> plantLayer.getChildren().add(pic));
            }
        } catch (Exception e ) {
            System.out.println("add at fieldLayer failed");
        }
    }
    public void removeObjectFromPlantLayer(int cellId, ImageView pic) {
        try {
//            CompletableFuture.supplyAsync(() -> plantLayer.getChildren().remove(pic), Platform::runLater).join();
            synchronized (plantLayer) {
                HBox obj = cells.get(cellId);
                if (obj != null) {
                    cells.put(cellId, null);
                    Platform.runLater(() -> obj.getChildren().remove(pic));
                } else {
                    System.out.println("cell not present in cells!");
                }
            }
        } catch (Exception e ) {
            e.getStackTrace();
        }
    }

    public void endOfGame(boolean win) {
        Text message = new Text();
        message.setX(220 - 110); //284, w: 482 // 241
        message.setY(100 - 8); // 142
        message.setFont(Font.font("Verdana", 10));
        message.setFill(Color.WHITE);
        message.setTextAlignment(TextAlignment.CENTER);
        if (win) {
            user.setLevelStatus(current, true);
            message.setText("Good job!");
        } else {
            user.setLevelStatus(current, false);
            message.setText("Game over!");
        }
        Timeline t = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            message.setX(message.getX() - 1.5);
            message.setY(message.getY() - .5);
            message.setFont(Font.font(message.getFont().getSize() + 1.5));
            message.setTranslateX(message.getX());
            message.setTranslateY(message.getY());
        }));
        t.setCycleCount(30);
        t.setOnFinished(event -> {
            myController.unloadScreen("level");
            try { sleep(3000); } catch (Exception e) { System.out.println("trouble with after game sleeping"); }
            myController.loadScreen("level", Main.lvlScreenSOURCE);
            myController.setScreen("level");
            Platform.runLater(() -> hitLayer.getChildren().remove(message));
//            screen.setPrefWidth(Main.ps.getWidth());
//            screen.setPrefHeight(Main.ps.getHeight());
        });
        Platform.runLater(() -> hitLayer.getChildren().add(message));
        t.play();

    }

    private void createGameArea() throws WrongWindowSizeException {
        plantLayer = new GridPane();
//        plantLayer.setGridLinesVisible(true);
        hitLayer = new Pane();
//        hitLayer.setStyle("-fx-border-color: red;");
        zombieLayer = new Pane();
//        zombieLayer.setStyle("-fx-border-color: red;");

        for (int row=0; row<5; row++) {
            for (int col=0; col<9; col++) {
                HBox cell = new HBox();
                cell.setPrefSize(CellSize.getCharacterWidth(size), CellSize.getCharacterHeight(size));
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
                        cells.put(plant.getCellId(), cell);
                        plant.setController(this);
                        int newAmount = Integer.parseInt(amount.getText()) - plant.getCost();
                        if (cell.getChildren().isEmpty() && newAmount >= 0) {
                            System.out.println("plant dropped at " + cell.getId() + ": " + plant);
                            current.addPlant(plant);
                            ImageView view = new ImageView();
                            view.setImage(new Image(plant.getImageSrc(), CellSize.getCharacterWidth(size) * 0.7, CellSize.getCharacterHeight(size) * 0.7, true, true));
                            cell.getChildren().add(view);
                            plant.start();
                            plant.setImg(view);
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

                plantLayer.add(cell, col, row);
            }
        }

        plantLayer.setPadding(new Insets(35, 10, 8, 110));
        zombieLayer.setPadding(new Insets(35, 10, 8, 110));
        gameArea.getChildren().addAll(zombieLayer, plantLayer, hitLayer);
    }

    private void loadLawnMowers() {
        if (current.hasLawnMower()) {
            for (int row=0; row<5; row++) {
                LawnMower lawnMower = new LawnMower(row, -1, this);
                current.addLawnMover(lawnMower);
                ImageView mover = new ImageView(new Image(lawnMower.getImageSrc(), lawnMower.getMaxPicSize(), lawnMower.getMaxPicSize(), true, true));
                lawnMower.setImg(mover);
                mover.setX(lawnMower.getX());
                mover.setY(17 + lawnMower.getRow() * 22 - 5);
                mover.setTranslateX(mover.getX());
                mover.setTranslateY(mover.getY());
//                mover.setRotate(40);
                addObjectToZombieLayer(mover);
            }
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
                        img = new Image(img.getUrl(), CellSize.getCharacterWidth(size), 50, true, true);
                    } catch (WrongWindowSizeException e) {
                        e.printStackTrace();
                    }
                    content.putImage(img);
                    if (DataFormat.lookupMimeType("plant") != null) {
                        content.put(DataFormat.lookupMimeType("plant"), plant);
                    } else {
                        content.put(new DataFormat("plant"), plant);
                    }
                    gameArea.getChildren().remove(plantLayer);
                    gameArea.getChildren().add(plantLayer);
                }
                db.setContent(content);

                event.consume();
            });
            col.setOnDragEntered(dragEvent -> {
                Main.sc.setCursor(Cursor.CLOSED_HAND);
//                System.out.println("drag entered");
                dragEvent.consume();
            });
            col.setOnDragDone(dragEvent -> {
                Main.sc.setCursor(Cursor.DEFAULT);
//                System.out.println("drag done");
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

//        deleteBtn.setDisable(true);
//        helpBtn.setDisable(true);

        Main.ps.setHeight(Main.INIT_HEIGHT - 25);
        Main.ps.setWidth(Main.INIT_WIDTH);
        Main.ps.setResizable(false);

        // TODO: 3 sizes, init | middle | large

        // h: 284, w: 482 cell:
        // h: 540, w: 720 cell:
        // h: 540, w: 720 cell:

        loadLevel();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
