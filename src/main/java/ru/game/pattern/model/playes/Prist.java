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

public class Prist extends Player{

    /**
     * Скорость движения объекта
     */
    public final int SPEED = 7;

    private static int MAX_HELTH = 100;

    private static int HELTH_HILL = 5;

    private static int MAX_MANA = 100;

    private static int HILL_PAUSE = 10;

    public static final int HILL_RADIUS = 80;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    private MouseListener mouseListener;

    private int mana = 100;

    volatile private boolean hill;

    private Color manaColor = Color.BLUE;

    private static int MANA_LOSSES = 1;

    private static int MANA_ADDING = 1;


    public Prist(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_left.png"));
        additionalSelectingIndicatorShift = 15;
        mouseListener = new PristMouseListener();
        hill = false;
    }

    @Override
    protected boolean isDrawTargetLocation() {
        return true;
    }

    @Override
    public int getSpeed() {
        return (int)(SPEED * getOneMultiSpeed());
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
        hill = false;
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
        recalculateHill();
        hillObjects(gameController);
        move(gameController);
    }

    private void recalculateHill() {
        if(mana<=0){
            hill = false;
        }
    }

    private void recalculateMana() {
        if (hill) {
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

    private void hillObjects(GameController gameController) {
        if(hill){
            if(fireTimer <= 0) {
                fireTimer = HILL_PAUSE;
                for(PhysicalGameObject o : gameController.getPhysicalGameObject()){
                    if(o.distanceBetweenCenter(this)<=HILL_RADIUS){
                        o.addHelth(HELTH_HILL);
                    }
                }
            }
            else {
                fireTimer--;
            }
        }
    }

    @Override
    public void drawSpecialBeforeAll(Graphics2D g) {
        if(hill){

            g.setStroke(new BasicStroke(3));
            g.setColor(Color.GREEN);
            g.drawOval(location.x - HILL_RADIUS, location.y - HILL_RADIUS, 2*HILL_RADIUS, 2*HILL_RADIUS);

            g.setStroke(new BasicStroke(1));
            g.setColor(new Color(0, 255, 0, 70));
            g.fillOval(location.x - HILL_RADIUS, location.y - HILL_RADIUS, 2*HILL_RADIUS, 2*HILL_RADIUS);

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
                   trySetHill(!hill);
                }
            }
        }
    }

    private void trySetHill(boolean hill ) {
        this.hill = hill;
    }
}
