package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.Enemy;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;
import ru.game.pattern.model.staticObjects.StaticPhysicalGameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */


/**
 * Класс: Player-объект. Игровой объект Воин
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
abstract public class Warrior extends Player {

    /**
     * Скорость движения объекта Воин
     */
    private final int speed;

    private MouseListener mouseListener;


    public Warrior(int maxHelth, WindowInfo windowsInfo, int attackPause, int attackRadius, int speed, int damage) throws IOException {
        super(maxHelth, windowsInfo);
        this.attackPause = attackPause;
        this.attackRadius = attackRadius;
        this.speed = speed;
        this.damage = damage;
        mouseListener = new WarriorMouseListener();
        drawTargetLocation = true;
    }

    @Override
    protected int getBulletCount() {
        //// TODO: 08.06.2016
        return 0;
    }


    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return true;
    }


    @Override
    public int getSpeed() {
        return (int)(speed * getOneMultiSpeed());
    }

    @Override
    protected void resetAction() {
        targetLocationList.clear();
        objectForAttack = null;
        targetLocation=null;
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }

    @Override
    public MouseListener getMouseListener() {
        return mouseListener;
    }

    @Override
    public void updateSpecial(GameController gameController) {
        move(gameController);//просто бег
    }

    class WarriorMouseListener extends PlayerMouseListener{


    }
}
