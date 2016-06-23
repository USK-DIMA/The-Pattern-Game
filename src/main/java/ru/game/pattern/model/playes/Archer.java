package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.Enemy;
import ru.game.pattern.model.FireBall;
import ru.game.pattern.model.GameObject;
import ru.game.pattern.model.WindowInfo;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Класс: Player-объект. Игровой объект Лучник
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
abstract public class Archer extends Player {

    private MouseListener mouseListener;

    /**
     * Список точек, куда наносить последующие удары.
     * обнуляется при вызове метода resetAction() (т.е. стрельба прекращается)
     */
    private List<Point> atackPoints;

    /**
     * Объект класса BulletCreater, отвечающий за создание патрон.
     * Как правило он передаётся из дочернего класса,
     * т.е. дочерний класс сам выбирыет какими патронами будет стрелять
     * @see BulletCreater
     */
    private BulletCreater bulletCreater;

    /**
     * Скорость объекта
     */
    private final int speed;

    /**
     * Пауза между атаками
     */
    private final int attackPause;

    private final int autoAttackRadius = Property.FIREBALL_LVL1_MAX_DISTANSE;

    public Archer(WindowInfo windowsInfo, BulletCreater bulletCreater, int speed, int maxHelth, int attackPause) throws IOException {
        super(maxHelth, windowsInfo);
        this.atackPoints = new LinkedList<>();
        this.bulletCreater = bulletCreater;
        this.speed = speed;
        this.attackPause = attackPause;
        playerImageForDraw = getImageForMoveToRight();
        mouseListener = new ArcherMouseListener();
        infighting = false;
    }

    @Override
    public KeyListener getKeyListener(){
        return null;
    }

    @Override
    public MouseListener getMouseListener() {
        return mouseListener;
    }

    @Override
    protected int getBulletCount() {
        return atackPoints.size();
    }

    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return false;
    }

    @Override
    public void updateSpecial(GameController gameController) {
        autoattack(gameController);
        if(fireTimer <= 0) {
            if (atackPoints.size() > 0) {
                try {
                    Point point = atackPoints.remove(0);
                    gameController.addBullet(bulletCreater.create(new Point(location), point, getTerritoryRadius(), this));
                    fireTimer = attackPause;


                    if (point.x > location.x) { //во время стрельбы герой смотрит в сторону стрельбы
                        playerImageForDraw = getImageForMoveToRight();
                    } else {
                        playerImageForDraw = getImageForMoveToLeft();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else  if(targetLocation!=null) {//но если стрелять не надо, то движемся лицом к цели
                if (targetLocation.x > location.x) {
                    playerImageForDraw = getImageForMoveToRight();
                } else {
                    playerImageForDraw = getImageForMoveToLeft();
                }
            }
        }
        else {
            fireTimer--;
        }

        move(gameController);
    }

    private void autoattack(GameController gameController) {
        if(targetLocation==null && atackPoints.size()==0) {
            atackPoints.addAll(gameController.getEnemy().stream()
                    .filter(o -> o.collision(location.x, location.y, getTerritoryRadius() + autoAttackRadius + 5) <= 0)
                    .map(Enemy::getLocation)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public int getSpeed(){
        return (int)(speed * getOneMultiSpeed());
    }

    @Override
    protected void resetAction() {
        targetLocationList.clear();
        targetLocation=null;
        atackPoints.clear();
    }

    /**
     * Добавить новую точку в массив для атаки
     * @param point координаты точки для атаки
     */
    private void armBullet(Point point) {
        atackPoints.add(point);
    }

    class ArcherMouseListener extends PlayerMouseListener{
        
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано СКМ
                if(isSeletedByCursor()){
                    armBullet(new Point(e.getX(), e.getY()));
                }
            }
        }
    }

    /**
     * Класс, отвечающий за создание патрон.
     * Как правило он определяется в дочернем классе и передаётся в класс Archer,
     * т.е. дочерний класс сам выбирыет какими патронами будет стрелять
     */
    protected interface BulletCreater {
        /**
         * создаёт патрон
         * @param location местополежения патрона, от куда он выпущен
         * @param targetLocation куда летит патрон
         * @param objectTerritoryRadius размер объекта-родителя (т.е. которые стреляет).
         *                              Типа патрон появляется не в центре объкта-родителя, а рябом с ним
         * @param parant ссылка на объкт-родителя, сделавшего выстрел (чтобы не стрелять по себе)
         * @return объект-патрон
         * @throws IOException если ресурсы не удалось подгрузить
         */
        FireBall create(Point location, Point targetLocation, int objectTerritoryRadius, GameObject parant) throws IOException;
    }

}
