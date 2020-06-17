package com.finalProject.ui;

import com.finalProject.game.GameController;

import java.io.IOException;


public class User extends Thread {
    private int UID;
    private String username;
    private GameController gameController;
    private int wallet = 1000;


    public User() {
        this.UID = -999;
        this.username = "";
    }

    public int getUID() { return UID; }
    public String getUsername() { return username; }
    public int getWalletAmount() { return wallet; }

    public void setUID(int uid) { UID = uid; }
    public void setWallet(int amount) { wallet = amount; }
    public void setUsername(String name) { this.username = name; }
    public void setGameController(GameController game) { gameController = game; }

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

    public void send(String msg) {}

    public void startClient() throws IOException {
        try{
            System.out.println("");
        } catch(Exception ex){
            System.out.println("Exception -> " + ex.getMessage());
        }
    }

}
