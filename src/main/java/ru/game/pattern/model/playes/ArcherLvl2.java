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
public class ArcherLvl2 extends Archer {


    public static final int COST = ARCHER_LVL2_COST;

    private final static int ATTACK_PAUSE = ARCHER_LVL2_ATTACK_PAUSE;

    public static final String ICON_PATH = Property.RESOURCES_PATH + "archer/archer_icon2.jpg";

    /**
     * Скорость движения объекта
     */
    public static final int SPEED = ARCHER_LVL2_SPEED;

    public static int MAX_HELTH = ARCHER_LVL2_MAX_HELTH;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;


    public ArcherLvl2(WindowInfo windowsInfo) throws IOException {
        super(windowsInfo, FireBallLvl1::new, SPEED, MAX_HELTH, ATTACK_PAUSE);
        playerRightImage = ImageIO.read(new File(Property.RESOURCES_PATH + "archer/archer_right2.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURCES_PATH + "archer/archer_left2.png"));

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
