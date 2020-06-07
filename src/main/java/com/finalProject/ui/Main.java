package com.finalProject.ui;

import com.finalProject.level.Level;
import com.finalProject.screenHandler.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static Stage ps;
    private static User user;
    private static Level currentLevel;
    private static List<Level> levels;
    private static ScreensController screenContainer = new ScreensController();

    public static String welcomeScreenID = "welcome";
    public static String welcomeScreenSOURCE = "/views/welcomeScreen.fxml";
    public static String gameScreenID = "game";
    public static String gameScreenSOURCE = "/views/gameScreen.fxml";
    public static String lvlScreenID = "level";
    public static String lvlScreenSOURCE = "/views/levelScreen.fxml";
//    private static String screen3ID = "help";
//    private static String screen3File = "/views/helpScreen.fxml";


    @Override
    public void start(Stage primaryStage) {
        user = new User();
        currentLevel = null;
        levels = new ArrayList<>();

        for (String filename : getAllFiles(new File("src/main/resources/levels"))) {
            try {
                if (!filename.matches(".*template.txt")) {
                    levels.add(new Level(levels.size(), filename));
                }
            } catch (Exception e) {
                System.out.println("pruser pri citani levelu " + filename);
            }
        }

        screenContainer.loadScreen(welcomeScreenID, welcomeScreenSOURCE);
//        mainContainer.loadScreen(gameScreenID, gameScreenSOURCE);
//        mainContainer.loadScreen(lvlScreenID, lvlScreenSOURCE);
        screenContainer.setScreen(welcomeScreenID);

        Group root = new Group();
        root.getChildren().addAll(screenContainer);
        primaryStage.setTitle("Plants vs. Zombies");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ps = primaryStage;
    }

    public static User getUser() { return user; }
    public static ScreensController getScreenContainer() { return screenContainer; }
    public static List<Level> getLevels() { return levels; }
    public static Stage getPrimaryStage() { return ps; }
    public static Level getCurrentLevel() { return currentLevel; }

    public static void setCurrentLevel(Level currentLevel) { Main.currentLevel = currentLevel; }

    private static List<String> getAllFiles(File current) {
        List<String> temp = new ArrayList<>();
        File[] directoryListing = current.listFiles();
        if ( directoryListing != null ) {
            for (File child : directoryListing) {
                if ( child.isDirectory() ) temp.addAll(getAllFiles(child));
                else temp.add(child.getPath());
            }
        }
        return temp;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
