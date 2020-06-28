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
import java.util.Properties;


/**
 Core of the application.
 Stores all values needed to be accessed globally.
 */
public class Main extends Application {
    public static Stage ps; // window size -> global access
    public static Scene sc; // showing components -> global access
    private static User user;
    private static Properties props;
    private static Level currentLevel;
    private static List<Level> levels;
    private static final ScreensController screenContainer = new ScreensController();
    public static double INIT_HEIGHT = 250; // TODO: move to config
    public static double INIT_WIDTH = 300;
    public static double HEIGHT = INIT_HEIGHT; // TODO: move to config
    public static double WIDTH = INIT_WIDTH;

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

        Group root = new Group();
        root.getChildren().addAll(screenContainer);
        primaryStage.setTitle("Plants vs. Zombies");
        primaryStage.setResizable(true);
        sc = new Scene(root);

        sc.widthProperty().addListener((observable, old, newSceneWidth) -> {
//            System.out.println("new window width: ");
            WIDTH = (double) newSceneWidth;
        });
        sc.heightProperty().addListener((observable, old, newSceneHeight) -> {
            HEIGHT = (double) newSceneHeight;
        });
        primaryStage.setScene(sc);


        // always first load a new screen and set it as active afterwards
        screenContainer.loadScreen(welcomeScreenID, welcomeScreenSOURCE);
        screenContainer.setScreen(welcomeScreenID);

        primaryStage.show();
        ps = primaryStage;
//        ps.setHeight(INIT_HEIGHT);
//        ps.setWidth(INIT_WIDTH);

        INIT_HEIGHT = ps.getHeight();
        INIT_WIDTH = ps.getWidth();
        loadLevels();
    }

    /**
     Creates levels from all the files found by the function getAllFiles and stores them for global access.
     */
    private static void loadLevels() {
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
    }

    public static User getUser() { return user; }
    public static ScreensController getScreenContainer() { return screenContainer; }
    public static List<Level> getLevels() { return levels; }
    public static Stage getPrimaryStage() { return ps; }
    public static Level getCurrentLevel() { return currentLevel; }
    public double getHeight() { return INIT_HEIGHT; }
    public double getWidth() { return INIT_WIDTH; }
    public static Properties getProps() {
        return props;
    }

    public static void setCurrentLevel(Level currentLevel) { Main.currentLevel = currentLevel; }
    public void setHeight(double newSize) { INIT_HEIGHT = newSize; }
    public void setWidth(double newSize) { INIT_WIDTH = newSize; }

    /**
     Goes through all files in directory 'resources/levels'.
     Returns an array of strings of all the present files suitable to be loaded as levels.
     */
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
