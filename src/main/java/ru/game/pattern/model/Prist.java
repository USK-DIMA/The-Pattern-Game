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
 * Created by Uskov Dmitry on 09.06.2016.
 */

public class Prist extends Player{

    /**
     * Скорость движения объекта
     */
    public final int SPEED = 7;

    private static int MAX_HELTH = 100;

    private static int HELTH_HILL = 3;

    public static final int HILL_RADIUS = 40;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    private MouseListener mouseListener;

    private boolean hill;


    public Prist(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_left.png"));
        mouseListener = new PristMouseListener();
        hill = false;
    }

    @Override
    protected boolean isDrawTargetLocation() {
        return true;
    }

    @Override
    public int getSpeed() {
        return SPEED;
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
        return 0;
    }

    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return true;
    }

    @Override
    void resetAction() {
        targetLocation = null;
        targetLocationList.clear();
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

    @Override
    public void drawSpecialBeforeAll(Graphics2D g) {
        if(hill){
            g.setColor(Color.GREEN);
            g.drawRect(location.x - HILL_RADIUS, location.y - HILL_RADIUS, 2*HILL_RADIUS, 2*HILL_RADIUS);
        }
    }

    class PristMouseListener extends PlayerMouseListener{
        @Override
        public void mouseReleasedSpecial(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано CКМ
                if(isSeletedByCursor()){
                   trySetHill(!hill);
                }
            }
        }
    }

    private void trySetHill(boolean hill ) {
        this.hill = hill;
    }
}
