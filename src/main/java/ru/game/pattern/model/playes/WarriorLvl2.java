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
public class WarriorLvl2 extends Warrior {

    public static final String ICON_PATH = Property.RESOURCES_PATH + "warrior/warrior_icon2.jpg";

    public static final int COST = WARRIOR_LVL2_COST;

    private final static int ATTACK_PAUSE = WARRIOR_LVL2_ATTACK_PAUSE;

    private final static int ATTACK_RADIUS = WARRIOR_LVL2_ATTACK_RADIUS;

    /**
     * Скорость движения объекта Воин
     */
    public final static int SPEED = WARRIOR_LVL2_SPEED;

    public final static int DAMAGE = WARRIOR_LVL2_MAX_DAMAGE;

    /**
     * Максимальное кол-во здоровья объекта Воин
     */
    private static int MAX_HELTH = WARRIOR_LVL2_MAX_HELTH;

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
        playerRightImage = ImageIO.read(new File(Property.RESOURCES_PATH + "warrior/warrior_right2.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURCES_PATH + "warrior/warrior_left2.png"));

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
