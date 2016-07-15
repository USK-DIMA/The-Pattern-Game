package ru.game.pattern.model.playes;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.controller.Property.*;

/**
 * Created by Uskov Dmitry on 13.06.2016.
 */
public class MagLvl3 extends Mag {

    public static final int COST = MAG_LVL3_COST;
    /**
     * Скорость движения объекта
     */
    public static int SPEED = MAG_LVL3_SPEED;

    public static int MAX_HELTH = MAG_LVL3_MAX_HELTH;

    public static double FREEZE = MAG_LVL3_FREEZE;

    public static int MAX_MANA = MAG_LVL3_MAX_MANA;

    public static final String ICON_PATH = Property.RESOURCES_PATH + "mag/mag_icon3.jpg";

    public static final int FREEZE_RADIUS = MAG_LVL3_FREEZE_RADIUS;

    private static int MANA_LOSSES = MAG_LVL3_MANA_LOSSES;

    private static int MANA_ADDING = MAG_LVL3_MANA_ADDING;

    private static int INVISE_PAUSE = MAG_LVL3_INVISE_PAUSE;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    public MagLvl3(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, SPEED, FREEZE, MAX_MANA, FREEZE_RADIUS, MANA_LOSSES, MANA_ADDING, INVISE_PAUSE);
        playerRightImage = ImageIO.read(new File(Property.RESOURCES_PATH + "mag/mag_right3.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURCES_PATH + "mag/mag_left3.png"));
    }

    @Override
    protected BufferedImage getImageForMoveToLeft() {
        return playerLeftImage;
    }

    @Override
    protected BufferedImage getImageForMoveToRight() {
        return playerRightImage;
    }

}
