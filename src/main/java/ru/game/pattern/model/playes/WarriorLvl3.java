package ru.game.pattern.model.playes;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.controller.Property.*;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */


/**
 * Класс: Player-объект. Игровой объект Воин
 * @see PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
public class WarriorLvl3 extends Warrior {

    public static final String ICON_PATH =  "warrior/warrior_icon3.jpg";

    public static final int COST = WARRIOR_LVL3_COST;

    private final static int ATTACK_PAUSE = WARRIOR_LVL3_ATTACK_PAUSE;

    private final static int ATTACK_RADIUS = WARRIOR_LVL3_ATTACK_RADIUS;

    /**
     * Скорость движения объекта Воин
     */
    public final static int SPEED = WARRIOR_LVL3_SPEED;

    public final static int DAMAGE = WARRIOR_LVL3_MAX_DAMAGE;

    /**
     * Максимальное кол-во здоровья объекта Воин
     */
    private static int MAX_HELTH = WARRIOR_LVL3_MAX_HELTH;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;


    public WarriorLvl3(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo, ATTACK_PAUSE, ATTACK_RADIUS, SPEED, DAMAGE );
        playerRightImage = getResourseAsImage("warrior/warrior_right3.png");
        playerLeftImage = getResourseAsImage("warrior/warrior_left3.png");

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
