package ru.game.pattern.model;

import ru.game.pattern.controller.Property;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Игровой объект, созданный для примера
 * Представлят из себя обычный круг
 */
public class Player extends GameObject {

    private int x = 20;

    private int y = 20;

    private final int r = 5;

    private final int speed=10;

    private Color color = Color.RED;

    private boolean left;

    private boolean right;

    private boolean up;

    private boolean down;

    private WindowInfo windowsInfo;

    private PlayerKeyListener keyListener;

    public Player(WindowInfo windowsInfo) {
        this.windowsInfo=windowsInfo;
        keyListener = new PlayerKeyListener();
        left = false;
        right = false;
        up = false;
        down = false;
    }

    @Override
    public KeyListener getKeyListener(){
        return keyListener;
    }

    @Override
    public void draw(Graphics2D g) {
            g.setColor(color);
            g.fillOval(x-r, y-r, 2*r, 2*r);
    }

    @Override
    public void update() {
        double sin45 = 0.70710678118; //в случае движения по диагонали, сумарная скорось пердвижения по x и по y должна быть рвана speed

        double k = 1;

        if(left){
            if(up^down){ //если нажата одна из клавиш. Если нажаты обе, то они себя компесируют
                k=sin45;
            }
            updateX((int)((-speed * k * Property.GAME_SPEED) + 0.5));
        }
        if(right){
            if(up^down){ //если нажата одна из клавиш. Если нажаты обе, то они себя компесируют
                k=sin45;
            }
            updateX((int)((speed * k * Property.GAME_SPEED) + 0.5));
        }
        if(up){
            if(left^right){ //если нажата одна из клавиш. Если нажаты обе, то они себя компесируют
                k=sin45;
            }
            updateY((int)((-speed * k * Property.GAME_SPEED) + 0.5));
        }
        if(down){
            if(left^right){ //если нажата одна из клавиш. Если нажаты обе, то они себя компесируют
                k=sin45;
            }
            updateY((int)((speed * k * Property.GAME_SPEED) + 0.5));
        }
    }

    private void updateX(int dx) {
        x+=dx;
        if(x<0){
            x=0;
        } else if(x>windowsInfo.getWidth()){
            x=windowsInfo.getWidth();
        }
    }

    private void updateY(int dy) {
        y+=dy;
        if(y<0){
            y=0;
        } else if(y>windowsInfo.getHeight()){
            y=windowsInfo.getHeight();
        }
    }


    private class PlayerKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_W){
                up=true;
            }
            if(key == KeyEvent.VK_S){
                down=true;
            }
            if(key == KeyEvent.VK_A){
                left=true;
            }
            if(key == KeyEvent.VK_D){
                right=true;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_W){
                up=false;
            }
            if(key == KeyEvent.VK_S){
                down=false;
            }
            if(key == KeyEvent.VK_A){
                left=false;
            }
            if(key == KeyEvent.VK_D){
                right=false;
            }
        }
    }

}
