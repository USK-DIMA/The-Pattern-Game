package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.Key;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */

/**
 * Класс: Player-объект. Игровой объект Жрец
 * Очень многое аналогично объекту-магу.
 * @see Mag
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
abstract public class Prist extends Player{

    /**
     * Скорость движения объекта
     */
    private final int speed;

    private final int helthHill;

    private final int maxMana ;

    private final int hillPause;

    private final int hillRadius;

    private final int manaLosses ;

    private final int manaAdding ;

    private MouseListener mouseListener;

    private KeyListener keyListener;

    private int mana;

    volatile private boolean hill;

    private Color manaColor = Color.BLUE;


    public Prist(int maxHelth, WindowInfo windowsInfo, int speed, int helthHill, int maxMana, int hillPause, int hillRadius, int manaLosses, int manaAdding) throws IOException {
        super(maxHelth, windowsInfo);
        this.speed = speed;
        this.helthHill = helthHill;
        this.maxMana = maxMana;
        this.hillPause = hillPause;
        this.hillRadius = hillRadius;
        this.manaLosses = manaLosses;
        this.manaAdding = manaAdding;
        mana = maxMana;
        additionalSelectingIndicatorShift = 15;
        mouseListener = new PristMouseListener();
        keyListener = new PristKeyListener();
        hill = false;
    }


    @Override
    protected boolean isDrawTargetLocation() {
        return true;
    }

    @Override
    public int getSpeed() {
        return (int)(speed * getOneMultiSpeed());
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
        return keyListener;
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

        if(this.mana> maxMana){
            this.mana = maxMana;
        }
    }

    public int getManaLosses() {
        return manaLosses;
    }

    private void hillObjects(GameController gameController) {
        if(hill){
            if(fireTimer <= 0) {
                fireTimer = hillPause;
                gameController.getPhysicalGameObject().stream()
                        .filter(o -> o instanceof Player && o.distanceBetweenCenter(this) <= hillRadius)
                        .forEach(o ->o.addHelth(helthHill));
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
            g.drawOval(location.x - hillRadius, location.y - hillRadius, 2* hillRadius, 2* hillRadius);

            g.setStroke(new BasicStroke(1));
            g.setColor(new Color(0, 255, 0, 70));
            g.fillOval(location.x - hillRadius, location.y - hillRadius, 2* hillRadius, 2* hillRadius);

        }
    }

    @Override
    protected void drawSpecial(Graphics2D g) {
        //отрисовка полоски маны
        g.setColor(Color.black);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-5, location.y-PLAYER_IMAGE_SHIFT_Y-12-additionalSelectingIndicatorShift, PLAYER_IMAGE_SHIFT_X*2+5, 10);
        g.setColor(manaColor);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-4, location.y-PLAYER_IMAGE_SHIFT_Y-11-additionalSelectingIndicatorShift, (int)((PLAYER_IMAGE_SHIFT_X*2+3)*(double)mana/ maxMana), 8);

    }

    public int getManaAdding() {
        return manaAdding;
    }

    class PristMouseListener extends PlayerMouseListener{
        @Override
        public void mouseReleasedSpecial(MouseEvent e) {

        }
    }

    class PristKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_Q) { //Клик по экрано CКМ
                if(isSeletedByCursor()){
                    trySetHill(!hill);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

        private void trySetHill(boolean hill ) {
        this.hill = hill;
    }
}
