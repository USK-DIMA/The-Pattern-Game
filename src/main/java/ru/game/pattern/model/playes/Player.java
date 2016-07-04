package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.Enemy;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;
import ru.game.pattern.model.staticObjects.StaticPhysicalGameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static ru.game.pattern.controller.Property.*;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */

/**
 * Родительский класс всех Player-объектов (т.е. персонажей, которыми будем играть)
 */
public abstract class Player  extends PhysicalGameObject {

    /**
     * Сдвиг изображения объекта по оси X относительно центральной координаты объекта координаты объекта
     */
    protected static final int PLAYER_IMAGE_SHIFT_X = 14;

    /**
     * Сдвиг изображения объекта по оси Y относительно центральной координаты объекта координаты объекта
     */
    protected final static int PLAYER_IMAGE_SHIFT_Y =  19;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси X относительно центральной координаты объекта координаты объекта
     */
    protected final static int SELECTING_INDICATOR_IMAGE_SHIFT_X =  10;

    /**
     * Сдвиг изображения индикатора выделения курсором по оси Y относительно центральной координаты объекта координаты объекта
     */
    protected final static int SELECTING_INDICATOR_IMAGE_SHIFT_Y =  PLAYER_IMAGE_SHIFT_Y + 49;

    /**
     * Радиус, показывающий размер объекта
     */
    protected int TERITORY_RADIUS = PLAYER_TERRITORY_RADIUS;

    protected int attackPause = DEFAULT_ATTACK_PAUSE;

    protected int attackRadius = DEFAULT_ATTACK_RADIUS;

    /**
     * Наносимый урон
     */
    protected int damage = DEFAULT_DAMAGE;


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

    protected Color helthColor = Color.GREEN;

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
     * Место, куда кликнули для атаки. Если на врага не попали, то ничего не произойдёт
     */
    private Point clickAttack;

    /**
     * Объект, который атакуем
     */
    volatile protected PhysicalGameObject objectForAttack;

    /**
     * отрисовывать ли конечную цель.
     * Прикол в том, что при попытке атаковать врага, targetLocation для война, это местоположение его врага.
     * Но при попытке атаковать врага не нужно отрисовывать флаг перемещения, хоть и targetLocation != null.
     */
    protected boolean drawTargetLocation;

    /**
     * Дополнительный сдвиг индикатора выделения вверх.
     * Если надо отрисовать ещё что-то под индикатором (например полоску маны).
     * Переопределяется в дочернем классе
     */
    protected int additionalSelectingIndicatorShift = 0;

    protected boolean infighting = true;

    private static BufferedImage aimImage;

    private static List<StaticPhysicalGameObject> staticObjects = null;

    private List<EditHealthNumber> mEditHealthNumbers;


    public Player(int maxHelth, WindowInfo windowsInfo) throws IOException {
        super(maxHelth);
        this.windowsInfo=windowsInfo;

        this.location = new Point(windowsInfo.getWidth()/2, windowsInfo.getHeight()/2);
        this.targetLocationList = new LinkedList<>();
        if(aimImage==null) {
            aimImage = ImageIO.read(new File(Property.RESOURCES_PATH + "aim2.png"));
        }
        selectiongIndicatorImage = ImageIO.read(new File(Property.RESOURCES_PATH + "selecting_player.png"));
        targetPointImage = ImageIO.read(new File(Property.RESOURCES_PATH + "flag.png"));
        selectedByCursor=false;

        targetLocation=null;
        fireTimer = 0;

        mEditHealthNumbers = new LinkedList<>();
    }

    public void setLocation(Point location){
        this.location=location;
    }

    public void setLocation(int x, int y){
        this.location.x=x;
        this.location.y=y;
    }



    public static void setStaticObjects(List<StaticPhysicalGameObject> staticObjectsIn) {
        staticObjects = staticObjectsIn;
    }

    @Override
    public void update(GameController gameController) {
        setObjectForAttack(gameController);
        if(objectForAttack==null && targetLocation == null && infighting){
            autoattack(gameController);
        }
        if(objectForAttack !=null){
            drawTargetLocation = false;
            targetLocationList.clear();
            targetLocation = objectForAttack.getLocation();
            if(!objectForAttack.isDestroy()) {
                moveToObjectAndAttack(objectForAttack, gameController);
            } else{
                objectForAttack = null;
                targetLocation = null;
            }
        } else {
            drawTargetLocation = true;
            updateSpecial(gameController);
        }
    }



    /**
     * Логика движения к объекту, который хотим атаковать
     * @param object объект для атаки
     * @param gameController контроллер, предоствляющий всякую нужную нам информацию.
     *                       (он нужен тк. мы будем исопльзовать метод move, а он требует себе на вход данный объект)
     */
    private void moveToObjectAndAttack(PhysicalGameObject object, GameController gameController) {
        Point oldLocation = new Point(location);
        if(object.distanceBetweenEdge(this)> attackRadius) {
            move(gameController);
        }
        double distance = object.distanceBetweenEdge(this);
        if(distance< attackRadius){//если уже можно достать для аттаки, то будем аатаковать
            if(distance<0){  //но если объекты наложидись друг на друга, то чуть сдвинем данный объект
                int dx = location.x - oldLocation.x;
                int dy = location.y - oldLocation.y;
                double dl = Math.sqrt(dx*dx + dy*dy);
                double p = (dl + distance)/dl;
                dx*=p;
                dy*=p;
                location.setLocation(oldLocation.getX()+dx, oldLocation.getY()+dy);
            }
            attack(object);
        }
    }

    /**
     * Непосредсвтенно сама атака
     * @param object объект для атаки
     */
    private void attack(PhysicalGameObject object) {
        if(fireTimer <= 0) {
            object.addHealth(-damage);
            fireTimer = attackPause;
        }
        else {
            fireTimer--;
        }
    }


    protected abstract void updateSpecial(GameController gameController);

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
        if(isDestroy()){ return; }
        int x = location.x;
        int y = location.y;

        //отрисовка индикатора выделения
        if(selectedByCursor){
            g.drawImage(selectiongIndicatorImage, x-SELECTING_INDICATOR_IMAGE_SHIFT_X, y-SELECTING_INDICATOR_IMAGE_SHIFT_Y - additionalSelectingIndicatorShift, null);
        }


        drawPlayer(g);

        //отрисовка HP
        g.setColor(Color.black);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5, y-PLAYER_IMAGE_SHIFT_Y-12, PLAYER_IMAGE_SHIFT_X*2+5, 10);
        g.setColor(helthColor);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5+1, y-PLAYER_IMAGE_SHIFT_Y-11, (int)((PLAYER_IMAGE_SHIFT_X*2+4)*(double)helth/maxHelth), 8);

        //Отрисовка цифры, кол-ва патрон в очереди
        if(getBulletCount()>0) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(getBulletCount()), x-PLAYER_IMAGE_SHIFT_X-4, y-PLAYER_IMAGE_SHIFT_Y-14);
        }

        if(isDrawTargetLocation()) {
            //Отрисовка точек движения
            if (targetLocation != null) {
                g.drawImage(targetPointImage, targetLocation.x - 1, targetLocation.y - 27, null);
                if (targetLocationList.size() > 0) {
                    List<Point> points = new LinkedList<>(targetLocationList);
                    for (Point p : points) {
                        g.drawImage(targetPointImage, p.x - 1, p.y - 27, null);
                    }
                }
            }
        }
    }


    /**
     * Попытка выбрать объект для атаки. Смотрим, кликнули ли мы на объект или в пустое место.
     * @param gameController дёргаем из этиого параметра все объекты на поле и смотрим их координаты
     */
    private void setObjectForAttack(GameController gameController) {
        if(clickAttack!=null){
            for(Enemy o : gameController.getEnemy()){
                if(o.collision(clickAttack.x, clickAttack.y, 2)<=0){
                    objectForAttack = o;
                    break;
                }
            }
            if (gameController.getCastle().collision(clickAttack.x, clickAttack.y, 2) <= 0) {
                objectForAttack = gameController.getCastle();
            }
            clickAttack = null;
        }
    }

    private void autoattack(GameController gameController) {
        for(Enemy o : gameController.getEnemy()){
            if(o.collision(location.x, location.y, getTerritoryRadius()+attackRadius+5)<=0){
                objectForAttack = o;
                break;
            }
        }
    }


    @Override
    public void drawAfterAll(Graphics2D g) {
        if (objectForAttack != null && !objectForAttack.isDestroy()) {
            g.drawImage(aimImage, objectForAttack.getLocation().x - 14, objectForAttack.getLocation().y - 14, null);
        }

        //Отрисовка цифы урон/восстановление
        List<EditHealthNumber> healthNumbers = editGetHealthManager(null, EditHealthNumber.CLONE);//создаём копию, т.к. LinkedList не потокобезопасен
        healthNumbers.removeIf(item -> item.amountShow > 30); // убираем много раз показанные
        healthNumbers.forEach(e -> {
                    g.setColor(e.color);
                    g.drawString(e.value, location.x - e.shiftX, location.y - 2*e.amountShow++);
                }
        );
    }

    /**
     * Потокобезопасное обращение к mEditHealthNumbers
     * @param health кол-во HP, если ходит доабвить новый элемен в mEditHealthNumbers
     * @param operation операция, которую необходимо выполнить
     * @return возвращает копию mEditHealthNumbers, если operation = EditHealthNumber.CLONE, иначе возвращает NULL.
     */
    synchronized private List<EditHealthNumber> editGetHealthManager(Integer health, int operation) {
        switch (operation) {
            case EditHealthNumber.ADD: mEditHealthNumbers.add(new EditHealthNumber(health));
                break;
            case EditHealthNumber.CLONE:
                return new LinkedList<>(mEditHealthNumbers);
        }
        return  null;
    }

    protected final boolean isDrawTargetLocation(){
        return drawTargetLocation;
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
                    if (o == this || o instanceof Enemy){ continue;}//сам с собой  и с врагомне проверяем
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


    public void trySetTargetLocation(Point point, boolean isShiftDown){
        if(outsideClick(point)){ return;}
        if(clickToStaticObject(point)){return;}
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

    public void setTargetLocation(Point point){
        targetLocation = point;
    }

    private boolean outsideClick(Point point) {
        return !(point.x>windowsInfo.getBorderLeft()
                && point.x<windowsInfo.getWidth() - windowsInfo.getBorderRight()
                && point.y>windowsInfo.getBorderTop()
                && point.y<windowsInfo.getHeight() - windowsInfo.getBorderBottom());
    }

    protected boolean clickToStaticObject(Point targetClock){
        if(staticObjects==null){ return true;}
        for(StaticPhysicalGameObject o: staticObjects){
            if(o.collision(targetClock.getX(), targetClock.getY(), 0)<=0){
                return true;
            }
        }

        return false;
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
    protected abstract int getBulletCount();

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

            if(e.getButton()==MouseEvent.BUTTON2 && infighting) { //Клик по экрано CКМ
                if(isSeletedByCursor()){
                    clickAttack = new Point(e.getX(), e.getY());
                }
            }


            if(e.getButton()==MouseEvent.BUTTON3) { //Клик по экрано ПКМ
                if(isSeletedByCursor()){
                    if(objectForAttack!=null) { //если есть объект для аттаки, то целевая локация для Война -- это координаты этого объекта
                        objectForAttack = null;
                        targetLocation = null; //поэтому сброисм их
                    }
                    trySetTargetLocation(new Point(e.getX(), e.getY()), e.isShiftDown());
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

    /**
     * Класс, отвечающий за текстовое поле изменения здоровья
     * @amountShow int счётчик показов (для удаления из списка)
     * @value String значение изменения
     * @color Color цвет при выводе (зелёный/красный)
     */
    class EditHealthNumber{

        public static final int ADD = 1;

        public static final int CLONE = 2;

        int amountShow;
        String value;
        Color color;
        int shiftX;

        EditHealthNumber(int value){
            this.value = String.format("%+d", value);
            this.amountShow = 0;
            this.color = (value > 0) ? Color.GREEN : Color.RED;
            this.shiftX = (value > 0) ? 25 : 15;
        }

    }

    @Override
    public void addHealth(int h) {
        super.addHealth(h);
        if(helth < maxHelth){
            editGetHealthManager(h, EditHealthNumber.ADD);
        }
    }
}
