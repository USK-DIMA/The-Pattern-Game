package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */

abstract public class Mag extends Player{

    /**
     * Скорость движения объекта
     */
    private final int speed;

    private final double freeze;

    private final int maxMana;

    private final int freezeRadius;

    private final int manaLosses;

    private final int manaAdding;

    private MouseListener mouseListener;

    private int mana;

    volatile private boolean isFreeze;

    private Color manaColor = Color.BLUE;

    public Mag(int MaxHelth, WindowInfo windowsInfo, int speed, double freeze, int maxMana, int freezeRadius, int manaLosses, int manaAdding) throws IOException {
        super(MaxHelth, windowsInfo);
        this.speed = speed;
        this.freeze = freeze;
        this.maxMana = maxMana;
        this.freezeRadius = freezeRadius;
        this.manaLosses = manaLosses;
        this.manaAdding = manaAdding;
        additionalSelectingIndicatorShift = 15;
        mana = this.maxMana;
        mouseListener = new PristMouseListener();
        isFreeze = false;
    }

    @Override
    protected boolean isDrawTargetLocation() {
        return true;
    }

    @Override
    public int getSpeed() {
        return speed;
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
        isFreeze = false;
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
            isFreeze = false;
        }
    }

    private void recalculateMana() {
        if (isFreeze) {
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

        if(this.mana> maxMana){
            this.mana = maxMana;
        }
    }

    public int getManaLosses() {
        return manaLosses;
    }

    private void freezeObjects(GameController gameController) {
        if(isFreeze){
                for(PhysicalGameObject o : gameController.getPhysicalGameObject()){
                    if(o.distanceBetweenCenter(this)<= freezeRadius){
                        o.setOneMultiSpeed(freeze);
                    }
                }
        }
    }

    @Override
    public void drawSpecialBeforeAll(Graphics2D g) {
        if(isFreeze){

            g.setStroke(new BasicStroke(3));
            g.setColor(Color.BLUE);
            g.drawOval(location.x - freezeRadius, location.y - freezeRadius, 2* freezeRadius, 2* freezeRadius);

            g.setStroke(new BasicStroke(1));
            g.setColor(new Color(0, 0, 255, 70));
            g.fillOval(location.x - freezeRadius, location.y - freezeRadius, 2* freezeRadius, 2* freezeRadius);

        }
    }

    @Override
    protected void drawSpecial(Graphics2D g) {
        //отрисовка полоски маны
        g.setColor(Color.black);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-5, location.y-PLAYER_IMAGE_SHIFT_Y-12-additionalSelectingIndicatorShift, PLAYER_IMAGE_SHIFT_X*2, 10);
        g.setColor(manaColor);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-4, location.y-PLAYER_IMAGE_SHIFT_Y-11-additionalSelectingIndicatorShift, (int)((PLAYER_IMAGE_SHIFT_X*2-2)*(double)mana/ maxMana), 8);

    }

    public int getManaAdding() {
        return manaAdding;
    }

    class PristMouseListener extends PlayerMouseListener{
        @Override
        public void mouseReleasedSpecial(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано CКМ
                if(isSeletedByCursor()){
                   trySetHill(!isFreeze);
                }
            }
        }
    }

    private void trySetHill(boolean hill ) {
        this.isFreeze = hill;
    }
}
