package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Level {
    private int ID;
    private String coverSrc = "";
    private String bgSrc = "";
    private int zombieCount = 100;
    private boolean lawnMower = true;
    private List<ZombieType> zombies = new ArrayList<>();
    private List<PlantType> plants = new ArrayList<>();

    public Level(int id, String filename) {
        ID = id;
        loadLevel(filename);
    }

    private void setCoverSrc(String src) { coverSrc = src; }
    private void setBgSrc(String src) { bgSrc = src; }
    private void setZombieCount(int count) { zombieCount = count; }
    private void setLawnMower(boolean isPresent) { lawnMower = isPresent; }
    private void addPlant(String type) throws WrongPlantTypeException { plants.add(PlantType.getTypeImport(type)); }
    private void addZombie(String type) throws WrongZombieTypeException { zombies.add(ZombieType.getTypeImport(type)); }

    public int getID() { return ID; }
    public Image getCoverImg() { return new Image( "/pics/lvl_covers/" + coverSrc); }
    public Image getBgImg() { return new Image( "/pics/bg/" + coverSrc); }

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

    @Override
    public String toString() {
        return "Level{" +
                "ID=" + ID +
//                ", coverSrc='" + coverSrc + '\'' +
                ", bgSrc='" + bgSrc + '\'' +
                ", zombieCount=" + zombieCount +
                ", lawnMower=" + lawnMower +
                ", zombies=" + zombies +
                ", plants=" + plants +
                '}';
    }
}
