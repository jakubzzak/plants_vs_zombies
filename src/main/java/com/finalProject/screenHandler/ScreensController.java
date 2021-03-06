package com.finalProject.screenHandler;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;


public class ScreensController extends StackPane {
    /**
     * Map of all presented screens.
     */
    private HashMap<String, Node> screens = new HashMap<>();

    /**
     *
     */
    public ScreensController() {
        super();
    }

    /**
     * Adds new screen to the map of screens.
     * @param name
     * Name of the new screen.
     * @param screen
     * Node representing the screen.
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     * Gets the required screen.
     * @param name
     * Name of the required screen.
     * @return
     * Screen.
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Loads required screen.
     * @param name
     * The name of the screen being loaded.
     * @param resource
     * Path to the FXML file representing the screen.
     * @return
     * If loading of the required screen ran successfully.
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println("screen " + name + " failed to load -> ");
            e.printStackTrace();
            return false;
        }
    }

    /**
    This method tries to displayed the screen with a predefined name.
    First it makes sure the screen has been already loaded.  Then if there is more than
    one screen the new screen is been added second, and then the current screen is removed.
    If there isn't any screen being displayed, the new screen is just added to the root.
     */
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), t -> {
                            getChildren().remove(0);                 //remove the displayed screen
                            getChildren().add(0, screens.get(name)); //add the screen
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name)); //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(1500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }


        /*
        Node screenToRemove;
        if(screens.get(name) != null) {   //screen loaded
            if(!getChildren().isEmpty()) {    //if there is more than one screen
                getChildren().add(0, screens.get(name));     //add the screen
                screenToRemove = getChildren().get(1);
                getChildren().remove(1);                    //remove the displayed screen
            } else {
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
        */
    }

    /**
     * Unloads given screen.
     * @param name
     * Name of the screen.
     * @return
     * If unloading ran successfully.
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}
