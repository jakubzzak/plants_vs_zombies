package com.finalProject.game;

import com.finalProject.level.ZombieType;

public class Zombie extends Thread {
    private ZombieType type;

    public Zombie(ZombieType type) {
        this.type = type;
    }

    @Override
    public void run() {
        // TODO: move the zombie
    }
}
