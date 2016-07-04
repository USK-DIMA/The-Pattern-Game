package ru.game.pattern.controller;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

import ru.game.pattern.model.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.security.Key;

/**
 * Объект, содержащий в себе информацию о состоянии игры.
 * Пока это только параметр, показывающий началась ли игра или уже закончилась.
 * Позже возможно добавить состояние "Пауза"
 */
public class GameStatus extends GameObject {

    volatile private boolean run;

    volatile private boolean pause;


    private KeyListener keyListener = new GameStatusKeyListener();

    volatile private boolean menu = true;

    public GameStatus() {
        this.run = true;
    }

    @Override
    public KeyListener getKeyListener() {
        return keyListener;
    }

    @Override
    public MouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(Graphics2D g) {

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
