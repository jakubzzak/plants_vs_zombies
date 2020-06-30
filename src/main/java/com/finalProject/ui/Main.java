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
//import java.util.Properties;


/**
 Core of the application.
 Stores all values needed to be accessed globally.
 */
public class Main extends Application {
    /**
     * Global instance of primary stage.
     */
    public static Stage ps; // window size -> global access
    /**
     * Global instance of scene.
     */
    public static Scene sc; // showing components -> global access
    /**
     * Global instance of user.
     */
    private static User user;
//    private static Properties props;
    /**
     * Global instance of chosen level.
     */
    private static Level currentLevel;
    /**
     * Global instance of list of all levels.
     */
    private static List<Level> levels;
    /**
     * Global instance of screen handler.
     */
    private static final ScreensController screenContainer = new ScreensController();
    /**
     * Global instance of initial height.
     */
    public static double INIT_HEIGHT = 250; // TODO: move to config
    /**
     * Global instance of initial width.
     */
    public static double INIT_WIDTH = 300;
    /**
     * Global instance of current height.
     */
    public static double HEIGHT = INIT_HEIGHT; // TODO: move to config
    /**
     * Global instance of current width.
     */
    public static double WIDTH = INIT_WIDTH;

    /**
     * Prop for welcome screen source.
     */
    public static String welcomeScreenID = "welcome";
    /**
     * Prop for welcome screen source.
     */
    public static String welcomeScreenSOURCE = "/views/welcomeScreen.fxml";
    /**
     * Prop for game screen source.
     */
    public static String gameScreenID = "game";
    /**
     * Prop for game screen source.
     */
    public static String gameScreenSOURCE = "/views/gameScreen.fxml";
    /**
     * Prop for level screen source.
     */
    public static String lvlScreenID = "level";
    /**
     * Prop for level screen source.
     */
    public static String lvlScreenSOURCE = "/views/levelScreen.fxml";
//    private static String screen3ID = "help";
//    private static String screen3File = "/views/helpScreen.fxml";


    /**
     * Kick-start of the application.
     * @param primaryStage
     * Stage to visualize app on the screen.
     */
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

    /**
     * Get the instance of the user.
     * @return
     * Object for handling user.
     */
    public static User getUser() { return user; }

    /**
     * Get the instance of the screen controller.
     * @return
     * Instance of the screen controller.
     */
    public static ScreensController getScreenContainer() { return screenContainer; }

    /**
     * Get the list of levels presented in the src/levels/ folder.
     * @return
     * List of levels.
     */
    public static List<Level> getLevels() { return levels; }

    /**
     * Gets the current level.
     * @return
     * Currently played level.
     */
    public static Level getCurrentLevel() { return currentLevel; }
//    public double getHeight() { return INIT_HEIGHT; }
//    public double getWidth() { return INIT_WIDTH; }
//    public static Properties getProps() {
//        return props;
//    }
//

    /**
     * Sets the current level.
     * @param currentLevel
     * Currently played level.
     */
    public static void setCurrentLevel(Level currentLevel) { Main.currentLevel = currentLevel; }
//    public void setHeight(double newSize) { INIT_HEIGHT = newSize; }
//    public void setWidth(double newSize) { INIT_WIDTH = newSize; }

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
