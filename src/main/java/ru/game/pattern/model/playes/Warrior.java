package ru.game.pattern.model.playes;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.PhysicalGameObject;
import ru.game.pattern.model.WindowInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */


/**
 * Класс: Player-объект. Игровой объект Воин
 * @see ru.game.pattern.model.PhysicalGameObject
 * @see ru.game.pattern.model.GameObject
 * @see Player
 */
abstract public class Warrior extends Player {

    private final int attackPause;

    private final int attackRadius;

    /**
     * Скорость движения объекта Воин
     */
    private final int speed;

    /**
     * Наносимый урон
     */
    private final int damage;

    private static BufferedImage aimImage;

    /**
     * Место, куда кликнули для атаки. Если на врага не попали, то ничего не произойдёт
     */
    private Point clickAttack;

    private MouseListener mouseListener;

    /**
     * Объект, который атакуем
     */
    volatile private PhysicalGameObject objectForAttack;

    /**
     * отрисовывать ли конечную цель.
     * Прикол в том, что при попытке атаковать врага, targetLocation для война, это местоположение его врага.
     * Но при попытке атаковать врага не нужно отрисовывать флаг перемещения, хоть и targetLocation != null.
     */
    private boolean drawTargetLocation;


    public Warrior(int maxHelth, WindowInfo windowsInfo, int attackPause, int attackRadius, int speed, int damage) throws IOException {
        super(maxHelth, windowsInfo);
        this.attackPause = attackPause;
        this.attackRadius = attackRadius;
        this.speed = speed;
        this.damage = damage;
        aimImage = ImageIO.read(new File(Property.RESOURSES_PATH + "aim2.png"));
        mouseListener = new WarriorMouseListener();
        drawTargetLocation = true;
    }

    @Override
    protected int getActivBulletCount() {
        //// TODO: 08.06.2016
        return 0;
    }


    @Override
    protected boolean isAutomaticTurnImagePlayer() {
        return true;
    }

    @Override
    protected boolean isDrawTargetLocation() {
        return drawTargetLocation;
    }

    @Override
    public int getSpeed() {
        return (int)(speed * getOneMultiSpeed());
    }

    @Override
    protected void resetAction() {
        targetLocationList.clear();
        objectForAttack = null;
        targetLocation=null;
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }

    @Override
    public MouseListener getMouseListener() {
        return mouseListener;
    }

    @Override
    public void update(GameController gameController) {
        setObjectForAttack(gameController);
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
            move(gameController);//просто бег
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
                object.addHelth(-damage);
                fireTimer = attackPause;
        }
        else {
            fireTimer--;
        }
    }

    /**
     * Попытка выбрать объект для атаки. Смотрим, кликнули ли мы на объект или в пустое место.
     * @param gameController дёргаем из этиого параметра все объекты на поле и смотрим их координаты
     */
    private void setObjectForAttack(GameController gameController) {
        if(clickAttack!=null){
            for(PhysicalGameObject o : gameController.getPhysicalGameObject()){
                if(o.collision(clickAttack.x, clickAttack.y, 2)<=0){
                    objectForAttack = o;
                    break;
                }
            }
            clickAttack = null;
        }
    }

    @Override
    public void drawSpecialAfterAll(Graphics2D g){
        if(objectForAttack!=null && !objectForAttack.isDestroy()){
            g.drawImage(aimImage, objectForAttack.getLocation().x - 14, objectForAttack.getLocation().y - 14, null);
        }
    }

    class WarriorMouseListener extends PlayerMouseListener{

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON2) { //Клик по экрано CКМ
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
                    setTargetLocation(new Point(e.getX(), e.getY()), e.isShiftDown());
                }
            }
        }

    }
}
