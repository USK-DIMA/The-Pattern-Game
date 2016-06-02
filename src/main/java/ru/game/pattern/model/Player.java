package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Игровой объект, созданный для примера
 * Представлят из себя обычный круг
 */
public class Player extends PhysicalGameObject {

    private static final int PLAYER_IMAGE_SHIFT_X = 23;

    private final static int PLAYER_IMAGE_SHIFT_Y =  45;

    private final static int SELECTING_INDICATOR_IMAGE_SHIFT_X =  14;

    private final static int SELECTING_INDICATOR_IMAGE_SHIFT_Y =  PLAYER_IMAGE_SHIFT_Y + 34;

    public final int SPEED = 5;

    private int TERITORY_RADIUS = 8;

    private Point location;

    volatile private Point targetLocation;

    private WindowInfo windowsInfo;

    private boolean selectedByCursor;

    private BufferedImage playerImage;

    private BufferedImage selectiongIndicatorImage;

    public Player(WindowInfo windowsInfo) throws IOException {
        this.windowsInfo=windowsInfo;
        this.location = new Point(windowsInfo.getWidth()/2, windowsInfo.getHeight()/2);
        playerImage = ImageIO.read(new File("src/main/resources/player.png"));
        selectiongIndicatorImage = ImageIO.read(new File("src/main/resources/selecting_player.png"));
        selectedByCursor=false;
        targetLocation=null;
    }

    public void setLocation(Point location){
        this.location=location;
    }

    public void setLocation(int x, int y){
        this.location.x=x;
        this.location.y=y;
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
        int x = location.x;
        int y = location.y;
        if(selectedByCursor){
            g.drawImage(selectiongIndicatorImage, x-SELECTING_INDICATOR_IMAGE_SHIFT_X, y-SELECTING_INDICATOR_IMAGE_SHIFT_Y, null);
        }
        g.drawImage(playerImage, x-PLAYER_IMAGE_SHIFT_X, y-PLAYER_IMAGE_SHIFT_Y, null);
    }

    @Override
    public void update(GameController gameController) {
        if(targetLocation!=null) {
            int x = location.x;
            int y = location.y;
            if (!(targetLocation.x == x && targetLocation.y == y)) {//если не достигли цели
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
                    targetLocation=null;
                }

                //Проверяем объекты на столкновения

                //// TODO: 03.06.2016 закоментированный код не работает как надо. Пока вернём старый код, который тоже работает не как надо, но лучше
                //// TODO: 03.06.2016 есди этот способ не будет как-то улучшен и использован, изменить сигнатуру  ArrayList<> getPhysicalGameObject на List<> getPhysicalGameObject (и все такие ArrayList-ы)
                /*int  index = gameController.getPhysicalGameObject().indexOf(this);
                System.err.println("index: "+index);
                for(int i=index+1; i<gameController.getPhysicalGameObject().size(); i++){
                    PhysicalGameObject o =  gameController.getPhysicalGameObject().get(i);
                    if (o == this){ continue;}//сам с собой не проверяем
                    int length = o.collision(x+dx, y+dy, TERITORY_RADIUS);
                    System.out.println(length);
                    if(length<0) {
                        if (dx != 0) {
                            dx *= -((double) length / dx);
                        }
                        if (dy != 0) {
                            dy *= -((double) length / dy);
                        }
                        //targetLocation=null;
                    }
                }*/

                for(PhysicalGameObject o :  gameController.getPhysicalGameObject()){
                    if (o == this){ continue;}//сам с собой не проверяем
                    int length = o.collision(x+dx, y+dy, TERITORY_RADIUS);
                    System.out.println(length);
                    if(length<0) {
                        if (dx != 0) {
                            dx *= -((double) length / dx);
                        }
                        if (dy != 0) {
                            dy *= -((double) length / dy);
                        }
                        //targetLocation=null;
                    }
                }
                location.x += dx;
                location.y += dy;
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
        return location;
    }

    @Override
    void resetAction() {
        targetLocation=null;
    }

    @Override
    public int collision(PhysicalGameObject gameObject) {
        if(gameObject==null){ return 0;}
        int x = location.x;
        int y = location.y;
        int x2 = gameObject.getLocation().x;
        int y2 = gameObject.getLocation().y;
        double length = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return (int)(length - (getTerritoryRadius() + gameObject.getTerritoryRadius()));
    }

    @Override
    public int collision(double x, double y, int teritoryRadius) {
        int x2 = location.x;
        int y2 = location.y;
        double length = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return (int)(length - (getTerritoryRadius() + teritoryRadius));
    }

    @Override
    public int getTerritoryRadius() {
        return TERITORY_RADIUS;
    }

    @Override
    public void setClickCursorLocation(Point point) {
        targetLocation=point;
    }


}
