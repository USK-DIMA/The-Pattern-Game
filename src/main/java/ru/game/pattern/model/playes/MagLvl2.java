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
public class MagLvl2 extends Mag {

    public static final int COST = MAG_LVL2_COST;
    /**
     * Скорость движения объекта
     */
    public static int SPEED = MAG_LVL2_SPEED;

    public static int MAX_HELTH = MAG_LVL2_MAX_HELTH;

    public static double FREEZE = MAG_LVL2_FREEZE;

    public static int MAX_MANA = MAG_LVL2_MAX_MANA;

    public static final String ICON_PATH = "mag/mag_icon2.jpg";

    public static final int FREEZE_RADIUS = MAG_LVL2_FREEZE_RADIUS;

    private static int MANA_LOSSES = MAG_LVL2_MANA_LOSSES;

    private static int MANA_ADDING = MAG_LVL2_MANA_ADDING;

    private static int INVISE_PAUSE = MAG_LVL2_INVISE_PAUSE;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    public MagLvl2(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, SPEED, FREEZE, MAX_MANA, FREEZE_RADIUS, MANA_LOSSES, MANA_ADDING, INVISE_PAUSE);
        playerRightImage = getResourseAsImage("mag/mag_right2.png");
        playerLeftImage = getResourseAsImage("mag/mag_left2.png");
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
