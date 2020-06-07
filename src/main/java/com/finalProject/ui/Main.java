package com.finalProject.ui;

import com.finalProject.level.Level;
import com.finalProject.screenHandler.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static Stage ps;
    private static User user;
    private static List<Level> levels;

    private static String screen0ID = "welcome";
    private static String screen0File = "/views/welcomeScreen.fxml";
    private static String screen1ID = "game";
    private static String screen1File = "/views/gameScreen.fxml";
    private static String screen2ID = "level";
    private static String screen2File = "/views/levelScreen.fxml";
//    private static String screen3ID = "help";
//    private static String screen3File = "/views/helpScreen.fxml";

    private static String lvl1 = "lvl1.txt"; // file -> v nom parametre lvl
    private static String lvl2 = "lvl2.txt";
    private static String lvl3 = "lvl3.txt";

    @Override
    public void start(Stage primaryStage) {
        user = new User();

        levels = new ArrayList<>();
        // TODO: citaj subory, nech sa sem nemusia pripisovat furt
        levels.add(new Level(0, lvl1));
        levels.add(new Level(1, lvl2));
        levels.add(new Level(2, lvl3));

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screen0ID, screen0File);
        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);
        mainContainer.setScreen(screen0ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        primaryStage.setTitle("Plants vs. Zombies");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ps = primaryStage;
    }

    public static User getUser() { return user; }
    public static List<Level> getLevels() { return levels; }
    public static Stage getPrimaryStage() { return ps; }


    public static void main(String[] args) {
        launch(args);
    }
}
