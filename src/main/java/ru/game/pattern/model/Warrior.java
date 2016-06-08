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

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */
public class Warrior extends Player {

    private final static int ATTACK_PAUSE = 30;

    /**
     * Скорость движения объекта
     */
    public final int SPEED = 9;

    private static int MAX_HELTH = 100;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    private MouseListener mouseListener;

    public Warrior(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_left.png"));
        mouseListener = new WarriorMouseListener();
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
        //// TODO: 08.06.2016
        return 0;
    }


    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return true;
    }

    @Override
    public int getSpeed() {
        return SPEED;
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
    void resetAction() {

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
    public void update(GameController gameController) {
        move(gameController);
    }

    class WarriorMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
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
