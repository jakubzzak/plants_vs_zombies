package com.finalProject.ui;

import com.finalProject.game.GameController;
import com.finalProject.level.Level;

import java.util.HashMap;
import java.util.Map;

/**
 Class for storing user data.
 */
public class User {
    private int UID;
    private String username;
    private GameController gameController;
    private Map<Integer, Boolean> levelStatus;
    private int wallet = 50;


    public User() {
        this.UID = -999;
        this.username = "";
        levelStatus = new HashMap<>();
    }

    public int getUID() { return UID; }
    public String getUsername() { return username; }
    public int getWalletAmount() { return wallet; }

    public Boolean getLevelStatus(Level level) { return levelStatus.get(level.getID()); }

    public void setUID(int uid) { UID = uid; }
    public void setWallet(int amount) { wallet = amount; }
    public void setUsername(String name) { this.username = name; }
    public void setGameController(GameController game) { gameController = game; }
    public void setLevelStatus(Level level, boolean status) {
        levelStatus.put(level.getID(), status);
    }

    public void addAmount(int amount) {
        wallet += amount;
    }
    public boolean subAmount(int amount) {
        if (wallet - amount >= 0) {
            wallet -= amount;
            return true;
        }
        return false;
    }

}
