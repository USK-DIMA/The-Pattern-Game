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
public class PristLvl2 extends Prist {

    public static final int COST = 75;

    public static final String ICON_PATH = Property.RESOURSES_PATH + "prist_icon1.jpg";

    private static final int MAX_HELTH = 75;

    private static final int SPEED = 5;

    private static final int HELTH_HILL = 5;

    private static final int MAX_MANA = 300;

    private static final int HILL_PAUSE = 15;

    private static final int HILL_RADIUS = 90;

    private static final int MANA_LOSES = 3;

    private static final int MANA_ADDING = 3;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    public PristLvl2(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, SPEED, HELTH_HILL, MAX_MANA, HILL_PAUSE, HILL_RADIUS, MANA_LOSES, MANA_ADDING);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_left.png"));
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
