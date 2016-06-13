package ru.game.pattern.model.playes;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 13.06.2016.
 */
public class MagLvl3 extends Mag {

    public static final int COST = 50;
    /**
     * Скорость движения объекта
     */
    public static int SPEED = 7;

    public static int MAX_HELTH = 100;

    public static double FREEZE = 0.5;

    public static int MAX_MANA = 150;

    public static final String ICON_PATH = Property.RESOURSES_PATH + "mag_icon1.jpg";

    public static final int FREEZE_RADIUS = 90;

    private static int MANA_LOSSES = 1;

    private static int MANA_ADDING = 1;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    public MagLvl3(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, SPEED, FREEZE, MAX_MANA, FREEZE_RADIUS, MANA_LOSSES, MANA_ADDING);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "mag_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "mag_left.png"));
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
