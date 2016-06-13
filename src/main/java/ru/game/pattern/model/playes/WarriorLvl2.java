package ru.game.pattern.model.playes;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */


/**
 * Класс: Player-объект. Игровой объект Воин
 * @see PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
public class WarriorLvl2 extends Warrior {

    public static final String ICON_PATH = Property.RESOURSES_PATH + "warrior_icon1.jpg";

    public static final int COST = 100;

    private final static int ATTACK_PAUSE = 30;

    private final static int ATTACK_RADIUS = 8;

    /**
     * Скорость движения объекта Воин
     */
    public final static int SPEED = 9;

    public final static int DAMAGE = 15;

    /**
     * Максимальное кол-во здоровья объекта Воин
     */
    private static int MAX_HELTH = 100;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;


    public WarriorLvl2(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, ATTACK_PAUSE, ATTACK_RADIUS, SPEED, DAMAGE );
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_left.png"));

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
