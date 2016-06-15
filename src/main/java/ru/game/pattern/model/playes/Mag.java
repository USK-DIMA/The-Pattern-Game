package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.model.Enemy;
import ru.game.pattern.model.WindowInfo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */


/**
 * Класс: Player-объект. Игровой объект Маг
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
abstract public class Mag extends Player{

    /**
     * Скорость движения объекта
     */
    private final int speed;

    /**
     * Множитель, который показыает, во сколько раз измениться скорость движения объекта, попавшего в радиус поражения
     */
    private final double freeze;

    /**
     * Максимальный запас маны
     */
    private final int maxMana;

    /**
     * Радиус поражения
     */
    private final int freezeRadius;

    /**
     * Потери маны при исопльзовании способности за одну итерацию (т.е. за один вызов метода update(GameController gc)
     */
    private final int manaLosses;

    /**
     * Восстановление маны при отключёной способности за одну итерацию  (т.е. за один вызов метода update(GameController gc)
     */
    private final int manaAdding;

    private MouseListener mouseListener;

    private KeyListener keyListener = new MagKeyListener();

    /**
     * Запас маны
     */
    private int mana;

    /**
     * Включена ли способность
     */
    volatile private boolean isFreeze;

    private boolean invise = false;

    private int invisePause;

    private int inviseTimer = 0;

    private Color manaColor = Color.BLUE;

    private int inviseTransparency = 0;

    public Mag(int MaxHelth, WindowInfo windowsInfo, int speed, double freeze, int maxMana, int freezeRadius, int manaLosses, int manaAdding, int invisePause) throws IOException {
        super(MaxHelth, windowsInfo);
        this.speed = speed;
        this.freeze = freeze;
        this.maxMana = maxMana;
        this.freezeRadius = freezeRadius;
        this.manaLosses = manaLosses;
        this.manaAdding = manaAdding;
        this.invisePause = invisePause;
        additionalSelectingIndicatorShift = 19;
        mana = this.maxMana;
        mouseListener = new MagMouseListener();
        isFreeze = false;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    protected int getBulletCount() {
        return inviseTimer;
    }

    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return true;
    }

    @Override
    protected void resetAction() {
        isFreeze = false;
        targetLocation = null;
        objectForAttack = null;
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
    public void updateSpecial(GameController gameController) {
        recalculateMana();
        recalculateFreeze();
        invise(gameController);
        freezeObjects(gameController);
        move(gameController);
    }

    private void invise(GameController gameController) {
        if(inviseTimer<=0){
            if(invise){
                invise = false;
                inviseTimer = invisePause;
                gameController.getBackgound().setBlack(240);
                gameController.getEnemy().stream().forEach(o->o.reset());
            }
        } else {
            inviseTimer--;
        }
    }

    /**
     * Проверка остатка маны. Если маны нет, то способность отключается
     */
    private void recalculateFreeze() {
        if(mana<=0){
            isFreeze = false;
        }
    }

    /**
     * Пересчёт маны. Если способность включена, То мана уменьшается, если способность выключениа, то мана увеличивается
     */
    private void recalculateMana() {
        if (isFreeze) {
            addMana(-getManaLosses());
        } else {
            addMana(getManaAdding());
        }
    }

    /**
     * Метод, увеличивающий ману, но не больше максимума
     * @param addMana на сколько увеличиваем ману
     */
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

    /**
     * Замедляем объекты, попавшие в радиус поражения
     * @param gameController
     */
    private void freezeObjects(GameController gameController) {
        if(isFreeze){
            gameController.getPhysicalGameObject().stream()
                    .filter(o -> o instanceof Enemy && o.distanceBetweenCenter(this) <= freezeRadius)
                    .forEach(o -> o.setOneMultiSpeed(freeze)
            );
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
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-5, location.y-PLAYER_IMAGE_SHIFT_Y-35, PLAYER_IMAGE_SHIFT_X*2+5, 10);
        g.setColor(manaColor);
        g.fillRect(location.x-PLAYER_IMAGE_SHIFT_X-4, location.y-PLAYER_IMAGE_SHIFT_Y-34, (int)((PLAYER_IMAGE_SHIFT_X*2+4)*(double)mana/ maxMana), 8);

    }

    public int getManaAdding() {
        return manaAdding;
    }

    private void setFreeze(boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    private void tryInvise() {
        if(inviseTimer<=0){
            invise = true;
        }
    }

    class MagMouseListener extends PlayerMouseListener{
        @Override
        public void mouseReleasedSpecial(MouseEvent e) {

        }
    }


    class MagKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_W) {
                if(isSeletedByCursor()){
                    setFreeze(!isFreeze);
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_E){
                if(isSeletedByCursor()) {
                    tryInvise();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
