package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;

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
 * Игровой объект, созданный для примера
 * //TODO скорее всего многие поля из этого класса надо перенести в PhysicalGameObject
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 */
public class Archer extends Player {

    private final static int ATTACK_PAUSE = 15;

    /**
     * Скорость движения объекта
     */
    public final int SPEED = 5;

    private static int MAX_HELTH = 100;

    private MouseListener mouseListener;

    private List<Point> atackPoints;

    public Archer(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH);
        this.windowsInfo=windowsInfo;
        this.location = new Point(windowsInfo.getWidth()/2, windowsInfo.getHeight()/2);
        this.atackPoints = new LinkedList<>();
        this.targetLocationList = new LinkedList<>();
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_left.png"));
        selectiongIndicatorImage = ImageIO.read(new File(Property.RESOURSES_PATH + "selecting_player.png"));
        targetPointImage = ImageIO.read(new File(Property.RESOURSES_PATH + "flag.png"));
        selectedByCursor=false;
        mouseListener = new PlayerMouseListener();
        playerImageForDraw = playerRightImage;
        targetLocation=null;
        fireTimer = 0;
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
    protected void drawPlayer(Graphics2D g) {
        // отрисовка героя
        g.drawImage(playerImageForDraw, location.x-PLAYER_IMAGE_SHIFT_X, location.y-PLAYER_IMAGE_SHIFT_Y, null);

    }

    @Override
    protected int getActivBulletCount() {
        return atackPoints.size();
    }

    @Override
    public void update(GameController gameController) {
        attack(gameController);
        move(gameController);
    }

    @Override
    public boolean isSeletedByCursor() {
        return selectedByCursor;
    }

    @Override
    public void setSelectedByCursor(boolean selectedByCursor) {
        this.selectedByCursor = selectedByCursor;
    }

    @Override
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
    }

    @Override
    public int getSpeed(){
        return SPEED;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    void resetAction() {
        targetLocationList.clear();
        targetLocation=null;
        atackPoints.clear();
    }

    private void armBullet(Point point) {
        atackPoints.add(point);
        System.out.println("Added bullet: "+ atackPoints.size());
    }

    private void attack(GameController gameController){
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
    }

    class PlayerMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано СКМ
                System.out.println("BUTTON2");
                if(isSeletedByCursor()){
                    armBullet(new Point(e.getX(), e.getY()));
                }
            }

            if(e.getButton()==MouseEvent.BUTTON3) { //Клик по экрано ПКМ
                    if(isSeletedByCursor()){
                        setClickCursorLocation(new Point(e.getX(), e.getY()), e.isShiftDown());
                    }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
