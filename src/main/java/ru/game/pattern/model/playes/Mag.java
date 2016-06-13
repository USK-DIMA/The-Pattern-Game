package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;

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

public class Mag extends Player{

    public static final int COST = 50;
    /**
     * Скорость движения объекта
     */
    public final int SPEED = 7;

    public static int MAX_HELTH = 100;

    public static double FREEZE = 0.5;

    public static int MAX_MANA = 150;

    public static final String ICON_PATH = Property.RESOURSES_PATH + "mag_icon1.jpg";

    public static final int FREEZE_RADIUS = 90;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    private MouseListener mouseListener;

    private int mana = MAX_MANA;

    volatile private boolean freeze;

    private Color manaColor = Color.BLUE;

    private static int MANA_LOSSES = 1;

    private static int MANA_ADDING = 1;


    public Mag(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "mag_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "mag_left.png"));
        additionalSelectingIndicatorShift = 15;
        mouseListener = new PristMouseListener();
        freeze = false;
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
    protected void resetAction() {
        freeze = false;
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
        recalculateMana();
        recalculateFreeze();
        freezeObjects(gameController);
        move(gameController);
    }

    private void recalculateFreeze() {
        if(mana<=0){
            freeze = false;
        }
    }

    private void recalculateMana() {
        if (freeze) {
            addMana(-getManaLosses());
        } else {
            addMana(getManaAdding());
        }
    }

    public void addMana(int addMana) {
        this.mana +=addMana;
        if(this.mana<0){
            mana = 0;
        }

        if(this.mana>MAX_MANA){
            this.mana = MAX_MANA;
        }
    }

    public static int getManaLosses() {
        return MANA_LOSSES;
    }

    private void freezeObjects(GameController gameController) {
        if(freeze){
                for(PhysicalGameObject o : gameController.getPhysicalGameObject()){
                    if(o.distanceBetweenCenter(this)<= FREEZE_RADIUS){
                        o.setOneMultiSpeed(FREEZE);
                    }
                }
        }
    }

    @Override
    public void drawSpecialBeforeAll(Graphics2D g) {
        if(freeze){

            g.setStroke(new BasicStroke(3));
            g.setColor(Color.BLUE);
            g.drawOval(location.x - FREEZE_RADIUS, location.y - FREEZE_RADIUS, 2* FREEZE_RADIUS, 2* FREEZE_RADIUS);

            g.setStroke(new BasicStroke(1));
            g.setColor(new Color(0, 0, 255, 70));
            g.fillOval(location.x - FREEZE_RADIUS, location.y - FREEZE_RADIUS, 2* FREEZE_RADIUS, 2* FREEZE_RADIUS);

        }
    }

    @Override
    protected void drawSpecial(Graphics2D g) {
        //отрисовка полоски маны
        g.setColor(Color.black);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-5, location.y-PLAYER_IMAGE_SHIFT_Y-12-additionalSelectingIndicatorShift, PLAYER_IMAGE_SHIFT_X*2, 10);
        g.setColor(manaColor);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-4, location.y-PLAYER_IMAGE_SHIFT_Y-11-additionalSelectingIndicatorShift, (int)((PLAYER_IMAGE_SHIFT_X*2-2)*(double)mana/MAX_MANA), 8);

    }

    public int getManaAdding() {
        return MANA_ADDING;
    }

    class PristMouseListener extends PlayerMouseListener{
        @Override
        public void mouseReleasedSpecial(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано CКМ
                if(isSeletedByCursor()){
                   trySetHill(!freeze);
                }
            }
        }
    }

    private void trySetHill(boolean hill ) {
        this.freeze = hill;
    }
}
