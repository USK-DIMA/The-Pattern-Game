package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */

public abstract class Player  extends PhysicalGameObject{

    /**
     * Сдвиг изображения объекта по оси X относительно центральной координаты объекта координаты объекта
     */
    protected static final int PLAYER_IMAGE_SHIFT_X = 23;

    /**
     * Сдвиг изображения объекта по оси Y относительно центральной координаты объекта координаты объекта
     */
    protected final static int PLAYER_IMAGE_SHIFT_Y =  35;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси X относительно центральной координаты объекта координаты объекта
     */
    protected final static int SELECTING_INDICATOR_IMAGE_SHIFT_X =  14;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси Y относительно центральной координаты объекта координаты объекта
     */
    protected final static int SELECTING_INDICATOR_IMAGE_SHIFT_Y =  PLAYER_IMAGE_SHIFT_Y + 49;

    /**
     * Радиус, показывающий размер объекта
     */
    protected int TERITORY_RADIUS = 8;


    public Player(int maxHelth) {
        super(maxHelth);
    }

    /**
     * точка, куда объекту следует двигаться
     */
    volatile protected Point targetLocation;

    /**
     * информация об окне
     */
    protected WindowInfo windowsInfo;

    /**
     * true, если объект выделен курсором, иначе false
     */
    protected boolean selectedByCursor;

    /**
     * Изображение игрового объекта при движении вправо
     */
    protected BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    protected BufferedImage playerLeftImage;

    /**
     * будет ссылаться на то изображение, которое будет отрисовываться
     */
    protected BufferedImage playerImageForDraw;

    protected BufferedImage targetPointImage;

    protected Color helthColor = Color.RED;

    protected int fireTimer;

    protected java.util.List<Point> targetLocationList;

    /**
     * Изображение индикатора выделения курсором
     */
    protected BufferedImage selectiongIndicatorImage;


    public void setLocation(Point location){
        this.location=location;
    }

    public void setLocation(int x, int y){
        this.location.x=x;
        this.location.y=y;
    }

    /**
     * Отрисовка изображения персонажа. Без индикатора выделения, здоровья и прочего. Только его тело.
     * @param g
     */
    protected abstract void drawPlayer(Graphics2D g);


    protected abstract int getActivBulletCount();

    @Override
    public Type getType() {
        return Type.player;
    }


    @Override
    public int getTerritoryRadius() {
        return TERITORY_RADIUS;
    }


    @Override
    public void draw(Graphics2D g) {
        if(destroy){ return; }
        int x = location.x;
        int y = location.y;

        //отрисовка индикатора выделения
        if(selectedByCursor){
            g.drawImage(selectiongIndicatorImage, x-SELECTING_INDICATOR_IMAGE_SHIFT_X, y-SELECTING_INDICATOR_IMAGE_SHIFT_Y, null);
        }


        drawPlayer(g);

        //отрисовка HP
        g.setColor(Color.black);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5, y-PLAYER_IMAGE_SHIFT_Y-12, PLAYER_IMAGE_SHIFT_X*2, 10);
        g.setColor(helthColor);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5+1, y-PLAYER_IMAGE_SHIFT_Y-11, (int)((PLAYER_IMAGE_SHIFT_X*2-1)*(double)helth/maxHelth), 8);

        //Отрисовка цифры, кол-ва патрон в очереди
        if(getActivBulletCount()>0) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(getActivBulletCount()), x-PLAYER_IMAGE_SHIFT_X-4, y-PLAYER_IMAGE_SHIFT_Y-14);
        }

        //Отрисовка точек движения
        if(targetLocation!=null){
            g.drawImage(targetPointImage, targetLocation.x-2, targetLocation.y-33, null);
            if(targetLocationList.size()>0){
                List<Point> points = new LinkedList<>(targetLocationList);
                for(Point p : points){
                    g.drawImage(targetPointImage, p.x-2, p.y-33, null);
                }
            }
        }
    }


    protected void move(GameController gameController){
        if(targetLocation!=null) { //пока только движение. Если двигаться объекту некуда, то ничего не делаем
            int x = location.x;
            int y = location.y;
            if (!(targetLocation.x == x && targetLocation.y == y)) {//если не достигли цели
                double dx;
                double dy;
                double targetX = targetLocation.getX();
                double targetY = targetLocation.getY();
                if(targetX - x!=0) {
                    double tan = Math.abs((targetY - y) / (targetX - x));
                    dx = getSpeed() / Math.sqrt(1 + tan * tan);
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
                    if(getSpeed() <Math.abs(targetY - y)) {
                        dy = getSpeed();
                    } else {
                        dy = Math.abs(targetY - y);
                        dy *= Math.signum(targetLocation.getY() - y);
                        targetLocation=null;
                    }
                    if(targetLocation!=null) {
                        dy *= Math.signum(targetLocation.getY() - y);
                    }
                }

                for(PhysicalGameObject o :  gameController.getPhysicalGameObject()){
                    if (o == this){ continue;}//сам с собой не проверяем
                    int length = o.collision(x+dx, y+dy, TERITORY_RADIUS);
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
                if(location.equals(targetLocation)){
                    targetLocation=null;
                }
            }
            if(targetLocation==null && targetLocationList.size()>0){
                targetLocation = targetLocationList.remove(0);
            }
        }
    }

    public abstract int getSpeed();
}
