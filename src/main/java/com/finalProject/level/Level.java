package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;
import com.finalProject.game.GameController;
import com.finalProject.game.Plant;
import com.finalProject.game.Zombie;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

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

    private List<Zombie> zombies = new LinkedList<>();
    private List<Plant> plants = new LinkedList<>();

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

    @Override
    public void run() {
        Random rnd = new Random();
        try { sleep(5000); } catch (Exception e) { System.out.println("game loop interrupted"); }

        new Thread(() -> { // releases zombies
            while (zombieCount > 0) {
                System.out.println("releasing new zombie");
                controller.releaseZombie(new Zombie(zombieTypes.get(rnd.nextInt(zombieTypes.size())), rnd.nextInt(5)));
                zombieCount--;
                try { sleep(rnd.nextInt(15) * 1000); } catch (Exception e) { System.out.println("zombie releasing thread interrupted"); }
            }
        }).start();

        new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            System.out.println("removing OutDated");
            plants.forEach(plant -> {
                if(plant.getHP() <= 0) {
                    plant.setFiring(false);
                    plants.remove(plant);
                    controller.removePlant(plant);
                }
            });
            zombies.forEach(zombie -> {

            });
        }));
//        removeStopped.setCycleCount(2);
//        removeStopped.setAutoReverse(true);
//        removeStopped.getKeyFrames().add(new KeyFrame(Duration.millis(rnd.nextInt(5) * 1000),
//                new KeyValue(node.translateXProperty(), 25)));
//        removeStopped.play();
    }

    @Override
    public java.lang.String toString() {
        return "Level{" +
                "ID=" + ID +
//                ", coverSrc='" + coverSrc + '\'' +
                ", bgSrc='" + bgSrc + '\'' +
                ", zombieCount=" + zombieCount +
                ", lawnMower=" + lawnMower +
                ", zombies=" + zombieTypes +
                ", plants=" + plantTypes +
                '}';
    }
}
