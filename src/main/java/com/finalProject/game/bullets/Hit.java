package com.finalProject.game.bullets;

import com.finalProject.game.Plant;
import com.finalProject.game.PowerType;
import com.finalProject.level.PlantType;
import javafx.scene.image.ImageView;


/**
 * Represents a hit as an object.
 */
public interface Hit {

    /**
     * @return
     * NUmber representing damage per hit caused by the hit.
     */
    int getDamage();

    /**
     * @param type
     * Plant type.
     * @return
     * Power of the provided plant type.
     */
    PowerType getPowerType(PlantType type);

    /**
     * @return
     * Source of the image of the hit as string.
     */
    String getImageSrc();

    /**
     * @return
     * Plant as its parent.
     */
    Plant getParent();

    /**
     * @return
     * Number representing distance traveled in one movement unit.
     */
    double getSpeed();

    /**
     * @return
     * Maximum size of the image representing hit on the screen.
     */
    int getMaxPicSize();

    /**
     * @return
     * Y offset from the parent.
     */
    int getOffsetY();

    /**
     * @return
     * X offset from the parent.
     */
    int getOffsetX();

    /**
     * @return
     * If hit is active or not.
     */
    boolean isDead();

    /**
     * Attaches an imageview to the hit.
     * @param img
     * Imageview representing the hit.
     */
    void setImg(ImageView img);

    /**
     * @return
     * Imageview representing the hit on the screen.
     */
    ImageView getImg();

    /**
     * Makes the hit inactive.
     */
    void setDead();

    /**
     * Makes the hit move forward.
     */
    void moveForward();

    /**
     * @return
     * Number representing X ax according to zombies.
     */
    double getX();

    /**
     * Sets current X position.
     * @param x
     * Number that represents new current X position.
     */
    void setCurrentX(double x);

    /**
     * Sets current Y position.
     * @param y
     * Number that represents new current Y position.
     */
    void setCurrentY(double y);

    /**
     * Fires the hit.
     */
    void releaseHit();

}
