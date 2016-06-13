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
public class Warrior extends Player {

    private final static int ATTACK_PAUSE = 30;

    private final static int ATTACK_RADIUS = 8;

    /**
     * Скорость движения объекта Воин
     */
    public final static int SPEED = 9;

    public final static int DAMAGE = 15;

    public static final int COST = 100;

    public static final String ICON_PATH = Property.RESOURSES_PATH + "warrior_icon1.jpg";

    /**
     * Максимальное кол-во здоровья объекта Воин
     */
    private static int MAX_HELTH = 100;

    /**
     * Изображение игрового объекта при движении вправо
     */
    private static BufferedImage playerRightImage;

    /**
     * Изображение игрового объекта при движении влево
     */
    private static BufferedImage playerLeftImage;

    private static BufferedImage aimImage;

    private Point clickAttack;

    private MouseListener mouseListener;

    volatile private PhysicalGameObject objectForAttack;

    private boolean drawTargetLocation;

    public Warrior(WindowInfo windowsInfo) throws IOException {
        super(MAX_HELTH, windowsInfo);
        playerRightImage = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_right.png"));
        playerLeftImage = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_left.png"));
        aimImage = ImageIO.read(new File(Property.RESOURSES_PATH + "aim2.png"));
        mouseListener = new WarriorMouseListener();
        drawTargetLocation = true;
    }

    @Override
    protected BufferedImage getImageForMoveToLeft() {
        return playerLeftImage;
    }

    @Override
    protected BufferedImage getImageForMoveToRight() {
        return playerRightImage;
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
        return (int)(SPEED * getOneMultiSpeed());
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

    private void moveToObjectAndAttack(PhysicalGameObject object, GameController gameController) {
        Point oldLocation = new Point(location);
        if(object.distanceBetweenEdge(this)>ATTACK_RADIUS) {
            move(gameController);
        }
        double distance = object.distanceBetweenEdge(this);
        if(distance< ATTACK_RADIUS){//если уже можно достать для аттаки, то будем аатаковать
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

    private void attack(PhysicalGameObject object) {
        if(fireTimer <= 0) {
                object.addHelth(-DAMAGE);
                fireTimer = ATTACK_PAUSE;
        }
        else {
            fireTimer--;
        }
    }

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
    protected void drawSpecial(Graphics2D g){
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
