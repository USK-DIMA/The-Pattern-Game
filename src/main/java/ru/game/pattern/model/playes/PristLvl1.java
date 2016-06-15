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
public class PristLvl1 extends Prist {

    public static final int COST = PRIST_LVL1_COST;

    public static final String ICON_PATH = Property.RESOURSES_PATH + "prist/prist_icon1.jpg";

    private static final int MAX_HELTH = PRIST_LVL1_MAX_HELTH;

    private static final int SPEED = PRIST_LVL1_SPEED;

    private static final int HELTH_HILL = PRIST_LVL1_HELTH_HILL;

    private static final int MAX_MANA = PRIST_LVL1_MAX_MANA;

    private static final int HILL_PAUSE = PRIST_LVL1_HILL_PAUSE;

    private static final int HILL_RADIUS = PRIST_LVL1_HILL_RADIUS;

    private static final int MANA_LOSES = PRIST_LVL1_MANA_LOSSES;

    private static final int MANA_ADDING = PRIST_LVL1_MANA_ADDING;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    public PristLvl1(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, SPEED, HELTH_HILL, MAX_MANA, HILL_PAUSE, HILL_RADIUS, MANA_LOSES, MANA_ADDING);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist/prist_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist/prist_left.png"));
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
