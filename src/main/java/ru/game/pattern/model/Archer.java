package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Игровой объект, созданный для примера
 * //TODO скорее всего многие поля из этого класса надо перенести в PhysicalGameObject
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 */
public class Archer extends PhysicalGameObject {

    /**
     * Сдвиг изображения объекта по оси X относительно центральной координаты объекта координаты объекта
     */
    private static final int PLAYER_IMAGE_SHIFT_X = 23;

    /**
     * Сдвиг изображения объекта по оси Y относительно центральной координаты объекта координаты объекта
     */
    private final static int PLAYER_IMAGE_SHIFT_Y =  35;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси X относительно центральной координаты объекта координаты объекта
     */
    private final static int SELECTING_INDICATOR_IMAGE_SHIFT_X =  14;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси Y относительно центральной координаты объекта координаты объекта
     */
    private final static int SELECTING_INDICATOR_IMAGE_SHIFT_Y =  PLAYER_IMAGE_SHIFT_Y + 49;

    private final static int ATTACK_PAUSE = 15;

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

    private MouseListener mouseListener;

    /**
     * ,Будет ссылаться на то изображение, которое будет отрисовываться
     */
    private BufferedImage playerImageForDraw;

    private BufferedImage targetPointImage;

    private static int MAX_HELTH = 100;

    private Color helthColor = Color.RED;

    private int fireTimer;

    private List<Point> targetLocationList;

    /**
     * Изображение индикатора выделения курсором
     */
    private BufferedImage selectiongIndicatorImage;

    private List<Point> atackPoints;

    public Archer(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH);
        this.windowsInfo=windowsInfo;
        this.location = new Point(windowsInfo.getWidth()/2, windowsInfo.getHeight()/2);
        this.atackPoints = new LinkedList<>();
        this.targetLocationList = new LinkedList<>();
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "player_left.png"));
        selectiongIndicatorImage = ImageIO.read(new File(Property.RESOURSES_PATH + "selecting_player.png"));
        targetPointImage = ImageIO.read(new File(Property.RESOURSES_PATH + "flag.png"));
        selectedByCursor=false;
        mouseListener = new PlayerMouseListener();
        playerImageForDraw = playerRightImage;
        targetLocation=null;
        fireTimer = 0;
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
        return mouseListener;
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


        // отрисовка героя
        g.drawImage(playerImageForDraw, x-PLAYER_IMAGE_SHIFT_X, y-PLAYER_IMAGE_SHIFT_Y, null);

        //отрисовка HP
        g.setColor(Color.black);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5, y-PLAYER_IMAGE_SHIFT_Y-12, PLAYER_IMAGE_SHIFT_X*2, 10);
        g.setColor(helthColor);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5+1, y-PLAYER_IMAGE_SHIFT_Y-11, (int)((PLAYER_IMAGE_SHIFT_X*2-1)*(double)helth/maxHelth), 8);

        //Отрисовка цифры, кол-ва патрон в очереди
        if(atackPoints.size()>0) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(atackPoints.size()), x-PLAYER_IMAGE_SHIFT_X-4, y-PLAYER_IMAGE_SHIFT_Y-14);
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

    @Override
    public void update(GameController gameController) {
        attack(gameController);

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
    public void setClickCursorLocation(Point point, boolean isShiftDown) {
        if(isShiftDown && targetLocation!=null){
            targetLocationList.add(point);
        }else {
            targetLocationList.clear();
            targetLocation = point;
            if (targetLocation.x > location.x) {
                playerImageForDraw = playerRightImage;
            } else {
                playerImageForDraw = playerLeftImage;
            }
        }
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    void resetAction() {
        targetLocationList.clear();
        targetLocation=null;
        atackPoints.clear();
    }

    @Override
    public int getTerritoryRadius() {
        return TERITORY_RADIUS;
    }


    private void armBullet(Point point) {
        atackPoints.add(point);
        System.out.println("Added bullet: "+ atackPoints.size());
    }

    private void attack(GameController gameController){
        if(fireTimer <= 0) {
            if (atackPoints.size() > 0) {
                try {
                    Point point = atackPoints.remove(0);
                    gameController.addBullet(new FireBall(new Point(location), point, getTerritoryRadius(), this));
                    fireTimer = ATTACK_PAUSE;


                    if (point.x > location.x) { //во время стрельбы герой смотрит в сторону стрельбы
                        playerImageForDraw = playerRightImage;
                    } else {
                        playerImageForDraw = playerLeftImage;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else  if(targetLocation!=null) {//но если стрелять не надо, то движемся лицом к цели
                    if (targetLocation.x > location.x) {
                        playerImageForDraw = playerRightImage;
                    } else {
                        playerImageForDraw = playerLeftImage;
                    }
            }
        }
        else {
            fireTimer--;
        }
    }

    class PlayerMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано СКМ
                System.out.println("BUTTON2");
                if(isSeletedByCursor()){
                    armBullet(new Point(e.getX(), e.getY()));
                }
            }

            if(e.getButton()==MouseEvent.BUTTON3) { //Клик по экрано ПКМ
                    if(isSeletedByCursor()){
                        setClickCursorLocation(new Point(e.getX(), e.getY()), e.isShiftDown());
                    }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
