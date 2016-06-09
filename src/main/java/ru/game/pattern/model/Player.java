package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */

/**
 * Родительский класс всех Player-объектов (т.е. персонажей, которыми будем играть)
 */
public abstract class Player  extends PhysicalGameObject{

    /**
     * Сдвиг изображения объекта по оси X относительно центральной координаты объекта координаты объекта
     */
    protected static final int PLAYER_IMAGE_SHIFT_X = 23;

    /**
     * Сдвиг изображения объекта по оси Y относительно центральной координаты объекта координаты объекта
     */
    protected final static int PLAYER_IMAGE_SHIFT_Y =  25;

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
     * будет ссылаться на то изображение, которое будет отрисовываться
     */
    protected BufferedImage playerImageForDraw;

    /**
     * изображение метки, куда персонаж передвигается
     */
    protected BufferedImage targetPointImage;

    protected Color helthColor = Color.RED;

    /**
     * Таймер, отсчитывающий время перезарядки
     */
    protected int fireTimer;

    /**
     * Лист точек передвижения
     */
    protected java.util.List<Point> targetLocationList;

    /**
     * Изображение индикатора выделения курсором
     */
    protected BufferedImage selectiongIndicatorImage;


    /**
     * Дополнительный сдвиг вверх индикатора выделения.
     * Если надо отрисовать ещё что-то под индикатором (например полоску маны).
     * Переопределяется в дочернем классе
     */
    protected int additionalSelectingIndicatorShift = 0;


    public Player(int maxHelth, WindowInfo windowsInfo) throws IOException {
        super(maxHelth);
        this.windowsInfo=windowsInfo;

        this.location = new Point(windowsInfo.getWidth()/2, windowsInfo.getHeight()/2);
        this.targetLocationList = new LinkedList<>();

        selectiongIndicatorImage = ImageIO.read(new File(Property.RESOURSES_PATH + "selecting_player.png"));
        targetPointImage = ImageIO.read(new File(Property.RESOURSES_PATH + "flag.png"));
        selectedByCursor=false;

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
            g.drawImage(selectiongIndicatorImage, x-SELECTING_INDICATOR_IMAGE_SHIFT_X, y-SELECTING_INDICATOR_IMAGE_SHIFT_Y - additionalSelectingIndicatorShift, null);
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

        if(isDrawTargetLocation()) {
            //Отрисовка точек движения
            if (targetLocation != null) {
                g.drawImage(targetPointImage, targetLocation.x - 2, targetLocation.y - 33, null);
                if (targetLocationList.size() > 0) {
                    List<Point> points = new LinkedList<>(targetLocationList);
                    for (Point p : points) {
                        g.drawImage(targetPointImage, p.x - 2, p.y - 33, null);
                    }
                }
            }
        }
        drawSpecial(g);
    }

    /**
     * Если надо отрисовать что-то ещё особенное поверх всего
     */
    protected void drawSpecial(Graphics2D g){

    }

    protected abstract boolean isDrawTargetLocation();

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

    /**
     * Возвращает скокрость объекта
     * @return скорость объекта
     */
    public abstract int getSpeed();

    /**
     * Логика движения объекта. Метод должен вызываеться в методе update(GameController gameController)
     */
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

    /**
     * Отрисовка изображения персонажа. Без индикатора выделения, здоровья и прочего. Только его тело.
     * @param g
     */
    protected void drawPlayer(Graphics2D g) {
        // отрисовка героя
        if(isAutomaticTurnImagePlayer()) {
            if (targetLocation != null && targetLocation.x < location.x) {
            } else {
                playerImageForDraw = getImageForMoveToRight();
            }
        }
        g.drawImage(playerImageForDraw, location.x-PLAYER_IMAGE_SHIFT_X, location.y-PLAYER_IMAGE_SHIFT_Y, null);
    }


    public void setTargetLocation(Point point, boolean isShiftDown){
        if(isShiftDown && targetLocation!=null){
            targetLocationList.add(point);
        }else {
            targetLocationList.clear();
            targetLocation = point;
            if (targetLocation.x > location.x) {
                playerImageForDraw = getImageForMoveToRight();
            } else {
                playerImageForDraw = getImageForMoveToLeft();
            }
        }
    }


    /**
     * Изображение, которое будет отрисоввываться при движении влево (т.к. может быть анимация, то логика этого метода должна быть определена в дочернем классе)
     * @return изображение, которое будет отрисоввываться при движении влево
     */
    protected abstract BufferedImage getImageForMoveToLeft();

    /**
     * Изображение, которое будет отрисоввываться при движении вправо (т.к. может быть анимация, то логика этого метода должна быть определена в дочернем классе)
     * @return изображение, которое будет отрисоввываться при движении вправо
     */
    protected abstract BufferedImage getImageForMoveToRight();

    /**
     * Возарщает сколько ударов (выстрелов) стоят в очереди атаки,
     * т.е. если быстро нажать клавишу атаки 15 раз, то герой не ударит сразу 15 раз,
     * у героя есть пауза между атаками. Поэтому все остальные атаки становяться в очередь.
     * @return количество ударов(выстрелов) в очереди
     */
    protected abstract int getActivBulletCount();

    /**
     * В методе ОТРИСОВКИ изображения героя в классе Player идёт автоматическое определение
     * движения персонажа и в заивисмости от того, двигается он вправо или влево,
     * будет вызываться метод getImageForMoveToRight()  или getImageForMoveToLeft().
     * Но бывает, что наджо сделать так, чтобы персонаж бежал задом к цели (например во время стрельбы)
     * тогда логика присвоения ссылки объекту playerImageForDraw определяется в дочернем классе
     * (как правило в методе update(GameController gameController)) и ф-я isAutomaticTurnImagePlayer должна возвращать false
     *
     * @return true, если автоматический поворот объекта в сторону движения должен быть вкл, false, если должен быть отключён
     */
    protected abstract boolean isAutomaticTurnImagePlayer();

    public class PlayerMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if(e.getButton()==MouseEvent.BUTTON3) { //Клик по экрано ПКМ
                if(isSeletedByCursor()){
                    setTargetLocation(new Point(e.getX(), e.getY()), e.isShiftDown());
                }
            }

            mouseReleasedSpecial(e);
        }

        public void mouseReleasedSpecial(MouseEvent e){

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
