package ru.game.pattern.controller;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

import ru.game.pattern.model.GameObject;
import ru.game.pattern.view.PatternGameGraphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Объект, содержащий в себе информацию о состоянии игры.
 */
public class GameStatus extends GameObject {

    volatile private boolean run;

    volatile private boolean pause;

    volatile private boolean menu = true;

    private KeyListener keyListener = new GameStatusKeyListener();

    public GameStatus() {
        this.run = true;
    }

    @Override
    public KeyListener getKeyListener() {
        return keyListener;
    }

    @Override
    public PatternGameMouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(PatternGameGraphics2D g) {

    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public Type getType() {
        return null;
    }

    public boolean isRun() {
        return run;
    }

    public void stopGame(){
        run=false;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }


    class GameStatusKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == 27){
                setPause(!pause);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
