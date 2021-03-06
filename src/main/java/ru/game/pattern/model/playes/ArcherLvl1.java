package ru.game.pattern.model.playes;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.FireBallLvl1;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.controller.Property.*;

/**
 * Created by Uskov Dmitry on 13.06.2016.
 */
public class ArcherLvl1 extends Archer {


    public static final int COST = Property.ARCHER_LVL1_COST;

    private final static int ATTACK_PAUSE = ARCHER_LVL1_ATTACK_PAUSE;

    public static final String ICON_PATH =  "archer/archer_icon1.jpg";

    /**
     * Скорость движения объекта
     */
    public static final int SPEED = ARCHER_LVL1_SPEED;

    public static int MAX_HELTH = Property.ARCHER_LVL1_MAX_HELTH;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;


    public ArcherLvl1(WindowInfo windowsInfo) throws IOException {
        super(windowsInfo, FireBallLvl1::new, SPEED, MAX_HELTH, ATTACK_PAUSE);
        playerRightImage = getResourseAsImage("archer/archer_right.png");
        playerLeftImage = getResourseAsImage("archer/archer_left.png");
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
