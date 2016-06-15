package ru.game.pattern.model;

import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.controller.Property.*;

/**
 * Created by Uskov Dmitry on 03.06.2016.
 */

/**
 * Класс: Объект-пуля. Объектами-пулями стреляют герои дальнего боя.
 * Этот класс представляет собой одну реализацию такого класса.
 * Объекты данного класса будут создаваться во время стрельбы.
 */
public class FireBallLvl1 extends FireBall {

    private static final int SPEED = FIREBALL_LVL1_SPEED;

    private static final int TERRITORY_RADIUS = FIREBALL_LVL1_TERRITORY_RADIUS;

    private static final int MAX_DISTANSE = FIREBALL_LVL1_MAX_DISTANSE;

    private static final int DAMAGE = FIREBALL_LVL1_DAMAGE;

    private static BufferedImage image;

    public FireBallLvl1(Point location, Point targetLocation, int objectTerritoryRadius, GameObject parant) throws IOException {
        super(location, targetLocation, objectTerritoryRadius, parant, TERRITORY_RADIUS, MAX_DISTANSE, DAMAGE, SPEED);
        if(image==null){
            image = ImageIO.read(new File(Property.RESOURSES_PATH + "fireball_min.png"));
        }
    }

    @Override
    protected BufferedImage getImageForDraw() {
        return image;
    }
}
