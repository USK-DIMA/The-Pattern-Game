package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;

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
 * //TODO скорее всего многие поля из этого класса надо перенести в PhysicalGameObject
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 */
public class Player extends PhysicalGameObject {

    /**
     * Сдвиг изображения объекта по оси X относительно центральной координаты объекта координаты объекта
     */
    private static final int PLAYER_IMAGE_SHIFT_X = 23;

    /**
     * Сдвиг изображения объекта по оси Y относительно центральной координаты объекта координаты объекта
     */
    private final static int PLAYER_IMAGE_SHIFT_Y =  45;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси X относительно центральной координаты объекта координаты объекта
     */
    private final static int SELECTING_INDICATOR_IMAGE_SHIFT_X =  14;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси Y относительно центральной координаты объекта координаты объекта
     */
    private final static int SELECTING_INDICATOR_IMAGE_SHIFT_Y =  PLAYER_IMAGE_SHIFT_Y + 49;

    /**
     * Скорость движения объекта
     */
    public final int SPEED = 5;

    /**
     * Радиус, показывающий размер объекта
     */
    private int TERITORY_RADIUS = 8;

    /**
     * точка, куда объекту следует двигаться
     */
    volatile private Point targetLocation;

    /**
     * информация об окне
     */
    private WindowInfo windowsInfo;

    /**
     * true, если объект выделен курсором, иначе false
     */
    private boolean selectedByCursor;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private BufferedImage playerLeftImage;


    /**
     * ,Будет ссылаться на то изображение, которое будет отрисовываться
     */
    private BufferedImage playerImageForDraw;

    private int maxHelth = 100;

    private int helth = 75;

    private Color helthColor = Color.RED;

    /**
     * Изображение индикатора выделения курсором
     */
    private BufferedImage selectiongIndicatorImage;


    public Player(WindowInfo windowsInfo) throws IOException {
        this.windowsInfo=windowsInfo;
        this.location = new Point(windowsInfo.getWidth()/2, windowsInfo.getHeight()/2);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_left.png"));
        selectiongIndicatorImage = ImageIO.read(new File(Property.RESOURSES_PATH + "selecting_player.png"));
        selectedByCursor=false;
        playerImageForDraw = playerRightImage;
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
        g.drawImage(playerImageForDraw, x-PLAYER_IMAGE_SHIFT_X, y-PLAYER_IMAGE_SHIFT_Y, null);

        g.setColor(Color.black);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5, y-PLAYER_IMAGE_SHIFT_Y-12, PLAYER_IMAGE_SHIFT_X*2, 10);
        g.setColor(helthColor);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5+1, y-PLAYER_IMAGE_SHIFT_Y-11, (int)((PLAYER_IMAGE_SHIFT_X*2-1)*(double)helth/maxHelth), 8);
    }

    @Override
    public void update(GameController gameController) {
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
                        targetLocation=null;
                    }
                    dy*=Math.signum(targetLocation.getY() - y);
                }

                //Проверяем объекты на столкновения, чтобы объекты не входили друг в друга

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
    public int getTerritoryRadius() {
        return TERITORY_RADIUS;
    }

    @Override
    public void setClickCursorLocation(Point point) {
        targetLocation=point;
        if(targetLocation.x>location.x){
            playerImageForDraw = playerRightImage;
        } else {
            playerImageForDraw = playerLeftImage;
        }
    }


}
