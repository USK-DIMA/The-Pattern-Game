package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.FireBall;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Класс: Player-объект. Игровой объект Лучник
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
public class Archer extends Player {

    private final static int ATTACK_PAUSE = 30;

    /**
     * Скорость движения объекта
     */
    public final int SPEED = 5;

    private static int MAX_HELTH = 100;

    private MouseListener mouseListener;

    private List<Point> atackPoints;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;


    public Archer(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo);
        this.atackPoints = new LinkedList<>();

        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_left.png"));
        playerImageForDraw = playerRightImage;

        mouseListener = new ArcherMouseListener();
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
    protected BufferedImage getImageForMoveToLeft() {
        return playerLeftImage;
    }

    @Override
    protected BufferedImage getImageForMoveToRight() {
        return playerRightImage;
    }

    @Override
    protected int getActivBulletCount() {
        return atackPoints.size();
    }

    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return false;
    }

    @Override
    public void update(GameController gameController) {
        if(fireTimer <= 0) {
            if (atackPoints.size() > 0) {
                try {
                    Point point = atackPoints.remove(0);
                    gameController.addBullet(new FireBall(new Point(location), point, getTerritoryRadius(), this));
                    fireTimer = ATTACK_PAUSE;


                    if (point.x > location.x) { //во время стрельбы герой смотрит в сторону стрельбы
                        playerImageForDraw = playerRightImage;
                    } else {
                        playerImageForDraw = playerLeftImage;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else  if(targetLocation!=null) {//но если стрелять не надо, то движемся лицом к цели
                if (targetLocation.x > location.x) {
                    playerImageForDraw = playerRightImage;
                } else {
                    playerImageForDraw = playerLeftImage;
                }
            }
        }
        else {
            fireTimer--;
        }

        move(gameController);
    }

    /*@Override
    public void setClickCursorLocation(Point point, boolean isShiftDown) {
        if(isShiftDown && targetLocation!=null){
            targetLocationList.add(point);
        }else {
            targetLocationList.clear();
            targetLocation = point;
            if (targetLocation.x > location.x) {
                playerImageForDraw = playerRightImage;
            } else {
                playerImageForDraw = playerLeftImage;
            }
        }
    }*/

    @Override
    protected boolean isDrawTargetLocation() {
        return true;
    }

    @Override
    public int getSpeed(){
        return (int)(SPEED * getOneMultiSpeed());
    }

    @Override
    protected void resetAction() {
        targetLocationList.clear();
        targetLocation=null;
        atackPoints.clear();
    }

    private void armBullet(Point point) {
        atackPoints.add(point);
        System.out.println("Added bullet: "+ atackPoints.size());
    }

    class ArcherMouseListener extends PlayerMouseListener{

        @Override
        public void mouseReleasedSpecial(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано СКМ
                if(isSeletedByCursor()){
                    armBullet(new Point(e.getX(), e.getY()));
                }
            }
        }
    }

}
