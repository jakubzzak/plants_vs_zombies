package com.finalProject.ui;

import com.finalProject.game.GameController;
import com.finalProject.level.Level;

import java.util.HashMap;
import java.util.Map;

/**
 Class for storing user data.
 */
public class User {
//    private int UID;
    /**
     * Name of the user.
     */
    private String username;
    /**
     * Instance of the game controller.
     */
    private GameController gameController;
    /**
     * Map of level statuses.
     */
    private Map<Integer, Boolean> levelStatus;
    /**
     * Amount of sun in the current game.
     */
    private int wallet = 50;

    /**
     * Creates user.
     */
    public User() {
//        this.UID = -999;
        this.username = "";
        levelStatus = new HashMap<>();
    }

//    public int getUID() { return UID; }

    /**
     * Gets user's name.
     * @return
     * username
     */
    public String getUsername() { return username; }

    /**
     * Gets amount of suns in the user's wallet.
     * @return
     * Amount of suns.
     */
    public int getWalletAmount() { return wallet; }

    /**
     * Gets stutus of the required level.
     * @param level
     * Certain level.
     * @return
     * Level status of the required level.
     */
    public Boolean getLevelStatus(Level level) { return levelStatus.get(level.getID()); }

//    public void setUID(int uid) { UID = uid; }
//    public void setWallet(int amount) { wallet = amount; }

    /**
     * Sets the name of the user.
     * @param name
     * Name of the user.
     */
    public void setUsername(String name) { this.username = name; }

    /**
     * Sets controller of the game.
     * @param game
     * Game controller.
     */
    public void setGameController(GameController game) { gameController = game; }

    /**
     * Sets new status to the certain level.
     * @param level
     * Certain level.
     * @param status
     * New status.
     */
    public void setLevelStatus(Level level, boolean status) {
        levelStatus.put(level.getID(), status);
    }

//    public void addAmount(int amount) {
//        wallet += amount;
//    }
//    public boolean subAmount(int amount) {
//        if (wallet - amount >= 0) {
//            wallet -= amount;
//            return true;
//        }
//        return false;
//    }

}
