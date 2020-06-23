package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;
import com.finalProject.game.GameController;
import com.finalProject.game.Plant;
import com.finalProject.game.Zombie;
import com.finalProject.game.bullets.Hit;
import javafx.animation.Timeline;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


public class Level extends Thread {
    private int ID;
    private GameController controller;
    private java.lang.String coverSrc = "";
    private java.lang.String bgSrc = "";
    private int zombieCount = 100;
    private boolean lawnMower = true;
    private List<ZombieType> zombieTypes = new ArrayList<>();
    private List<PlantType> plantTypes = new ArrayList<>();
    private Timeline t;

    private final List<Zombie> zombies = new LinkedList<>();
    private final List<Plant> plants = new LinkedList<>();
    private final List<Hit> hits = new ArrayList<>();

    public Level(int id, java.lang.String filename) {
        ID = id;
        loadLevel(filename);
    }

    private void setCoverSrc(java.lang.String src) { coverSrc = src; }
    private void setBgSrc(java.lang.String src) { bgSrc = src; }
    private void setZombieCount(int count) { zombieCount = count; }
    private void setLawnMower(boolean isPresent) { lawnMower = isPresent; }
    private void addPlant(java.lang.String type) throws WrongPlantTypeException { plantTypes.add(PlantType.getTypeImport(type)); }
    private void addZombie(java.lang.String type) throws WrongZombieTypeException { zombieTypes.add(ZombieType.getTypeImport(type)); }
    public void setController(GameController controller) { this.controller = controller; }

    public int getID() { return ID; }
    public Image getCoverImg() { return new Image( "/pics/lvl_covers/" + coverSrc); }
    public Image getBgImg() { return new Image( "/pics/bg/" + coverSrc); }
    public java.lang.String getBgImgSrc() { return "/pics/bg/" + bgSrc; }
    public boolean hasLawnMower() { return lawnMower; }
    public List<Plant> getPlantTypes() { return plantTypes.stream().map(x -> {
        try {
            return new Plant(x, PlantType.getCost(x));
        } catch (WrongPlantTypeException e) {
            e.printStackTrace();
            return null;
        }
    }).collect(Collectors.toList()); }

    public int getZombieCount() { return zombieCount; }
    synchronized public void addHit(Hit hit) { hits.add(hit); }
    synchronized public void addPlant(Plant plant) { plants.add(plant); }

    private void loadLevel(java.lang.String filename) {
        try(Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            while (sc.hasNextLine()) {
                java.lang.String[] line = sc.nextLine().strip().split(":?\\s+");
                if (line.length > 1 && !line[0].equals("//")) {
                    switch (line[0]) {
                        case "cover_src":
                            setCoverSrc(line[1]);
                            break;
                        case "bg_src":
                            setBgSrc(line[1]);
                            break;
                        case "zombie_count":
                            setZombieCount(Integer.parseInt(line[1]));
                            break;
                        case "lawn_mower":
                            setLawnMower(line[1].equals("true"));
                            break;
                        case "plants":
                            for (int i=1; i < line.length; i++) {
                                addPlant(line[i]);
                            }
                            break;
                        case "zombies":
                            for (int j=1; j < line.length; j++) {
                                addZombie(line[j]);
                            }
                            break;
                        default:
                            throw new RuntimeException("Command unrecognized: '" + line[0] + "'");
                    }
                }
            }
        } catch (WrongPlantTypeException | WrongZombieTypeException e) {
            System.out.println(e.getMessage());
        }
        catch (NumberFormatException e) {
            System.out.println("pruser pri parsovani cisla zo suboru " + filename);
        } catch (Exception e) {
            System.out.println("pruser pri citani suboru " + filename + " -> " + e.getMessage());
        }
    }

    private void endOfGame() {
        t.stop();
    }

    @Override
    public void run() {
        Random rnd = new Random();

        new Thread(() -> { // handle hits
            while (zombieCount > 0 || zombies.size() > 0) {
                synchronized (hits) {
                    List<Hit> toBeRemoved = new ArrayList<>();
                    hits.forEach(hit -> {
                        if (hit.isDead()) {
                            System.out.println("removing hit -> " + hit);
                            toBeRemoved.add(hit);
                            controller.removeObjectFromHitLayer(hit.getImg());
                        } else {
                            hit.moveForward();
                        }
                    });
                    toBeRemoved.forEach(hits::remove);
                }
                try { sleep(50); } catch (Exception e) { System.out.println("hit handling thread interrupted"); }
            }
        }).start();

        new Thread(() -> { // handle plants
            // TODO: spustat/vypinat fire pri kazdej rastline
            while (zombieCount > 0 || zombies.size() > 0) {
//                System.out.println("removing zombie");
                synchronized (plants) {
                    List<Plant> toBeRemoved = new ArrayList<>();
                    plants.forEach(plant -> {
                        if (plant.isDead()) {
                            System.out.println("removing plant -> " + plant);
                            plant.setFiring(false);
                            toBeRemoved.add(plant);
                            controller.removeObjectFromPlantLayer(plant.getImg());
                        }
                    });
                    toBeRemoved.forEach(plants::remove);
                }
                try { sleep(250); } catch (Exception e) { System.out.println("plant handling thread interrupted"); }
            }
            plants.forEach(plant -> plant.setFiring(false));
            controller.endOfGame(true);
        }).start();

        new Thread(() -> { // releases zombies
            try { sleep(5000); } catch (Exception e) { System.out.println("game loop interrupted"); }
            while (zombieCount > 0) {
//                System.out.println("releasing new zombie");
                synchronized (zombies){
                    Zombie zombie = new Zombie(zombieTypes.get(rnd.nextInt(zombieTypes.size())), rnd.nextInt(5), 220);
                    controller.releaseZombie(zombie);
                    zombies.add(zombie);
                }
                zombieCount--;
                try { sleep(rnd.nextInt(15000)); } catch (Exception e) { System.out.println("zombie releasing thread interrupted"); }
            }
        }).start();

        new Thread(() -> { // handle zombies
            while (zombieCount > 0 || zombies.size() > 0) {
                synchronized (zombies) {
                    List<Zombie> toBeRemoved = new ArrayList<>();
                    zombies.forEach(zombie -> {
                        if (zombie.isDead() || zombie.reachedHouse()) {
                            System.out.println("removing zombie -> " + zombie);
                            toBeRemoved.add(zombie);
                            controller.removeObjectFromZombieLayer(zombie.getImg());
                        } else {
                            zombie.moveForward();
                        }
                    });
                    toBeRemoved.forEach(zombies::remove);
                }
                try { sleep(100); } catch (Exception e) { System.out.println("zombie handling thread interrupted"); }
            }
        }).start();
    }

    @Override
    public String toString() {
        return "Level{" +
                "zombieCount=" + zombieCount +
                ", lawnMower=" + lawnMower +
                ", zombies=" + zombies +
                ", plants=" + plants +
                ", hits=" + hits +
                '}';
    }
}
