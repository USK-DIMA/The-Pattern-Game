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
public class PriestLvl2 extends Priest {

    public static final int COST = PRIST_LVL2_COST;

    public static final String ICON_PATH =  "prist/prist_icon2.jpg";

    private static final int MAX_HELTH = PRIST_LVL2_MAX_HELTH;

    private static final int SPEED = PRIST_LVL2_SPEED;

    private static final int HELTH_HILL = PRIST_LVL2_HELTH_HILL;

    private static final int MAX_MANA = PRIST_LVL2_MAX_MANA;

    private static final int HILL_PAUSE = PRIST_LVL2_HILL_PAUSE;

    private static final int HILL_RADIUS = PRIST_LVL2_HILL_RADIUS;

    private static final int MANA_LOSES = PRIST_LVL2_MANA_LOSSES;

    private static final int MANA_ADDING = PRIST_LVL2_MANA_ADDING;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    public PriestLvl2(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, SPEED, HELTH_HILL, MAX_MANA, HILL_PAUSE, HILL_RADIUS, MANA_LOSES, MANA_ADDING);
        playerRightImage = getResourseAsImage("prist/prist_right2.png");
        playerLeftImage = getResourseAsImage("prist/prist_left2.png");
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
