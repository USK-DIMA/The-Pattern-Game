package ru.game.pattern.model;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Игровой объект, созданный для примера
 * Представлят из себя обычный круг
 */
public class Player extends GameObject {

    public final int SPEED = 20;

    private int x;

    private int y;

    /**
     * Радиус
     */
    private final int r = 5;

    private Color color = Color.RED;

    private Color selectingColor = Color.YELLOW;

    private final static int SELECTING_BOARD_HEIGHT = 2;

    volatile private Point targetLocation;

    private WindowInfo windowsInfo;

    private boolean selectedByCursor;

    public Player(WindowInfo windowsInfo) {
        this.windowsInfo=windowsInfo;
        this.x=windowsInfo.getWidth()/2;
        this.y=windowsInfo.getHeight()/2;
        selectedByCursor=false;
        targetLocation=null;
    }

    public void setLocation(int x, int y){
        this.x=x;
        this.x=y;
    }

    @Override
    public KeyListener getKeyListener(){
        return null;
    }

    @Override
    public MouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(Graphics2D g) {
        if(selectedByCursor){
            g.setColor(selectingColor);
            int selectingR = r+SELECTING_BOARD_HEIGHT;
            g.fillOval(x-selectingR, y-selectingR, 2*selectingR, 2*selectingR);
        }
        g.setColor(color);
        g.fillOval(x-r, y-r, 2*r, 2*r);
    }

    @Override
    public void update() {
        if(targetLocation!=null) {
            if (!(targetLocation.x == x && targetLocation.y == y)) {
                double dx;
                double dy;
                double targetX = targetLocation.getX();
                double targetY = targetLocation.getY();
                if(targetX - x!=0) {
                    double tan = Math.abs((targetY - y) / (targetX - x));
                    dx = SPEED / Math.sqrt(1 + tan * tan);
                    if(dx > Math.abs(targetX - x)){
                        dx = Math.abs(targetX - x);
                    }
                    dx*= Math.signum(targetX - x);

                    dy = Math.abs(dx * tan);
                    if(dy > Math.abs(targetY - y)){
                        dy = Math.abs(targetY - y);
                    }
                    dy *= Math.signum(targetY - y);
                }
                else {
                    dx=0;
                    if(SPEED <Math.abs(targetY - y)) {
                        dy = SPEED;
                    } else {
                        dy = Math.abs(targetY - y);
                    }
                    dy*=Math.signum(targetLocation.getY() - y);
                }

                x += dx;
                y += dy;
            }
        }
    }

    @Override
    public Type getType() {
        return Type.player;
    }

    @Override
    public boolean isSeletedByCursor() {
        return selectedByCursor;
    }

    @Override
    public void setSelectedByCursor(boolean selectedByCursor) {
        this.selectedByCursor = selectedByCursor;
    }

    @Override
    public Point getLocation() {
        return new Point(x, y);
    }

    @Override
    public void setClickCursorLocation(Point point) {
        targetLocation=point;
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

}
