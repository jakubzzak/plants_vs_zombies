package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;
import com.finalProject.game.*;
import com.finalProject.game.bullets.Hit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;


/**
 * Handles running of the game.
 */
public class Level {
    private final int ID;
    private GameController controller;
    private String coverSrc = "";
    private String bgSrc = "";
    private int zombieCount = 100;
    private boolean lawnMower = true;
    private final List<ZombieType> zombieTypes = new ArrayList<>();
    private final List<PlantType> plantTypes = new ArrayList<>();
    private final List<LawnMower> lawnMowers = new ArrayList<>();
    private boolean gameOver = false;

    private final List<Zombie> zombies = new LinkedList<>();
    private final List<Plant> plants = new LinkedList<>();
    private final List<Hit> hits = new ArrayList<>();

    public Level(int id, String filename) {
        ID = id;
        loadLevel(filename);
    }

    private void setCoverSrc(String src) { coverSrc = src; }
    private void setBgSrc(String src) { bgSrc = src; }
    private void setZombieCount(int count) { zombieCount = count; }
    private void setLawnMower(boolean isPresent) { lawnMower = isPresent; }
    private void addPlant(String type) throws WrongPlantTypeException { plantTypes.add(PlantType.getTypeImport(type)); }
    private void addZombie(String type) throws WrongZombieTypeException { zombieTypes.add(ZombieType.getTypeImport(type)); }
    public void setController(GameController controller) { this.controller = controller; }

    public int getID() { return ID; }
    public Image getCoverImg() { return new Image( "/pics/lvl_covers/" + coverSrc); }
    public Image getBgImg() { return new Image( "/pics/bg/" + coverSrc); }
    public String getBgImgSrc() { return "/pics/bg/" + bgSrc; }
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
    synchronized public void addLawnMover(LawnMower lawnMower) { lawnMowers.add(lawnMower); }

    /**
     * Loads chosen level from given file.
     * @param filename
     * Name of the file required to be loaded.
     */
    private void loadLevel(String filename) {
        try(Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().strip().split(":?\\s+");
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

    /**
     * Kicks off all game threads for handling the game.
     */
    public void run() {
        Random rnd = new Random();

        new Thread(() -> { // handle hits
            while (!gameOver && (zombieCount > 0 || zombies.size() > 0)) {
                synchronized (hits) {
                    List<Hit> toBeRemoved = new ArrayList<>();
                    hits.forEach(hit -> {
                        if (hit.isDead()) {
//                            System.out.println("removing hit -> " + hit);
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
            while (!gameOver && (zombieCount > 0 || zombies.size() > 0)) {
//                System.out.println("removing zombie");
                synchronized (plants) {
                    List<Plant> toBeRemoved = new ArrayList<>();
                    plants.forEach(plant -> {
                        boolean zombieIsPresent = zombies.stream().anyMatch(zombie -> zombie.getRow() == plant.getRow());
                        if (plant.isDead()) {
//                            System.out.println("removing plant -> " + plant);
                            plant.setFiring(false);
                            toBeRemoved.add(plant);
                            controller.removeObjectFromPlantLayer(plant.getCellId(), plant.getImg());
                        } else if (!PlantType.isFlower(plant) && !PlantType.isBarrier(plant)) {
                            if (!plant.isFiring() && zombieIsPresent){
                                plant.setFiring(true);
                            } else if (plant.isFiring() && !zombieIsPresent) {
                                plant.setFiring(false);
                            }
                        }
                    });
                    toBeRemoved.forEach(plants::remove);
                }
                try { sleep(250); } catch (Exception e) { System.out.println("plant handling thread interrupted"); }
            }
            plants.forEach(plant -> plant.setFiring(false));
            if (!gameOver) {
                controller.endOfGame(true);
            }
        }).start();

        new Thread(() -> { // releases zombies
            try { sleep(25000); } catch (Exception e) { System.out.println("game loop interrupted"); }
            while (!gameOver && zombieCount > 0) {
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
            while (!gameOver && (zombieCount > 0 || zombies.size() > 0)) {
                synchronized (zombies) {
                    List<Zombie> toBeRemoved = new ArrayList<>();
                    zombies.forEach(zombie -> {
                        if (zombie.isDead()) {
                            toBeRemoved.add(zombie);
                            controller.removeObjectFromZombieLayer(zombie.getImg());
                        } else {
                            // check collision with lawnMovers
                            LawnMower lawnMover = lawnMowers.get(zombie.getRow());
                            if (lawnMover != null && lawnMover.isRunning()) {
                                // TODO: calculate offset
                                if (lawnMover.getX() + 20 >= zombie.getX()) {
                                    zombie.setDead();
                                }
                            }
                            // check collision with hits
                             Optional<Hit> firstHit = hits.stream().filter(hit -> !PlantType.isFlower(hit.getParent())).filter(hit -> hit.getParent().getRow() == zombie.getRow()).max(Comparator.comparing(Hit::getX));
                            if (firstHit.isPresent()) {
                                Hit hit = firstHit.get();
                                if (hit.getImg().getX() >= zombie.getX()) {
                                    zombie.receiveHit(hit);
                                    hit.setDead();
                                }
                            }
                            // check collision with plants
                            Optional<Plant> firstObstacle = plants.stream().filter(obstacle -> obstacle.getRow() == zombie.getRow()).max(Comparator.comparing(Plant::getX));
                            if (firstObstacle.isPresent()) {
                                Plant plant = firstObstacle.get();
                                try {
                                    if (plant.getX() >= zombie.getX()) {
                                        zombie.setEating(true, plant);
                                    } else if (zombie.isEating()) {
                                        zombie.setEating(false);
                                    }
                                } catch (Exception e) {
                                    System.out.println("img not present yet");
                                }
                            }
                            zombie.moveForward();
                        }
                    });
                    toBeRemoved.forEach(zombies::remove);
                }
                for ( Zombie zombie : zombies.stream().filter(zombie -> zombie.getX() < 50).collect(Collectors.toList()) ) { // trigger lawnMovers if zombie reached house
                    if (lawnMowers.stream().filter(Objects::nonNull).filter(mover -> !mover.isRunning()).anyMatch(lawnMower -> lawnMower.getRow() == zombie.getRow())) {
                        LawnMower lawnMower = lawnMowers.get(zombie.getRow());
                        Timeline t = new Timeline(new KeyFrame(Duration.millis(50), e -> {
                            lawnMower.moveForward();
                        }));
                        t.setCycleCount(50);
                        t.setOnFinished(event -> {
                            if (lawnMower.getX() < 210) {
                                t.play();
                            } else {
                                lawnMower.setRunning(false);
                                lawnMowers.remove(lawnMower);
                                lawnMowers.add(zombie.getRow(), null);
                                controller.removeObjectFromZombieLayer(lawnMower.getImg());
                            }
                        });
                        lawnMower.setRunning(true);
                        t.play();
                        zombie.setDead();
                    } else {
                        gameOver = true;
                        controller.endOfGame(false);
                        break;
                    }
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
