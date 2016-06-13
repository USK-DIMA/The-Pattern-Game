package ru.game.pattern.model.playes;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.FireBallLvl1;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 13.06.2016.
 */
public class ArcherLvl3 extends Archer {


    public static final int COST = 90;

    private final static int ATTACK_PAUSE = 30;

    public static final String ICON_PATH = Property.RESOURSES_PATH + "archer/archer_icon3.jpg";

    /**
     * Скорость движения объекта
     */
    public static final int SPEED = 5;

    public static int MAX_HELTH = 100;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;


    public ArcherLvl3(WindowInfo windowsInfo) throws IOException {
        super(windowsInfo, FireBallLvl1::new, SPEED, MAX_HELTH, ATTACK_PAUSE);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "archer/archer_right3.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "archer/archer_right3.png"));

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
