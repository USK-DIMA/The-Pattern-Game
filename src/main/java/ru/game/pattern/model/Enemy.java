package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.playes.Player;
import ru.game.pattern.model.staticObjects.StaticPhysicalGameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static ru.game.pattern.controller.Property.*;
import static ru.game.pattern.model.Enemy.EnemyState.attack;
import static ru.game.pattern.model.Enemy.EnemyState.patrul;

/**
 * Created by Uskov Dmitry on 08.06.2016.
 */


public class Enemy extends PhysicalGameObject {

    public static final int OVERVIEW_RADIUS = ENEMY_OVERVIEW_RADIUS;

    /**
     * Сдвиг изображения объекта по оси X относительно центральной координаты объекта координаты объекта
     */
    protected static final int PLAYER_IMAGE_SHIFT_X = 14;

    /**
     * Сдвиг изображения объекта по оси Y относительно центральной координаты объекта координаты объекта
     */
    protected final static int PLAYER_IMAGE_SHIFT_Y =  19;

    private static final int SPEED = ENEMY_SPEED;

    private static final int DAMAGE = ENEMY_DAMAGE;

    private static final int ATTACK_RADIUS = ENEMY_ATTACK_RADIUS;

    private int DAMAGE_PAUSE = ENEMY_DAMAGE_PAUSE;

    private int damageTimer = DAMAGE_PAUSE;

    /**
     * Радиус, показывающий размер объекта
     */
    private int TERITORY_RADIUS = ENEMY_TERITORY_RADIUS;

    /**
     * точка, куда объекту следует двигаться
     */
    volatile protected Point targetLocation;

    /**
     * информация об окне
     */
    protected WindowInfo windowsInfo;


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

    private static FreeTargetPoint freeTargetPoints = Property.initFreeTargetPointForEnemy();

    private static List<StaticPhysicalGameObject> staticObjects = null;

    private BufferedImage imageForMoveToRight;

    private BufferedImage imageForMoveToLeft;

    private FreeTargetPoint currentFreePoint;

    private List<Point> playerLocationsForDebag;

    private Player objectForAttack = null;

    private EnemyState state = patrul;

    private Point locationForDraw = null;

    public Enemy(int maxHelth, WindowInfo windowsInfo) throws IOException {
        super(maxHelth);
        this.windowsInfo=windowsInfo;
        this.imageForMoveToRight = ImageIO.read(new File(Property.RESOURSES_PATH+"enemy_right.png"));
        this.imageForMoveToLeft = ImageIO.read(new File(Property.RESOURSES_PATH+"enemy_left.png"));
        if(new Random().nextBoolean()) {
            this.location = new Point(1091,34);
            currentFreePoint = freeTargetPoints;
        } else {
            this.location = new Point(CASTLE_LOCATION_X + 50, CASTLE_LOCATION_Y + 50);
            currentFreePoint = FreeTargetPoint.getP9();
        }

        targetPointImage = ImageIO.read(new File(Property.RESOURSES_PATH + "flag.png"));

        targetLocation= currentFreePoint.getPoint();
        fireTimer = 0;
    }

    public void reset(){
        objectForAttack = null;
        nextCurrentFreePont();
    }

    public void setLocation(Point location){
        this.location=location;
    }


    public static void setStaticObjects(List<StaticPhysicalGameObject> staticObjectsIn) {
        staticObjects = staticObjectsIn;
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
    public int getSpeed() {
        return (int)(SPEED * getOneMultiSpeed());
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }

    @Override
    public MouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(Graphics2D g) {
        if(isDestroy()){ return; }
        int x = location.x;
        int y = location.y;

        if(Property.DEBUG_MODE) {
            g.setColor(Color.WHITE);
            g.drawOval(location.x - OVERVIEW_RADIUS, location.y - OVERVIEW_RADIUS, 2 * OVERVIEW_RADIUS, 2 * OVERVIEW_RADIUS);

            if(playerLocationsForDebag!=null) {
                for (Point p : playerLocationsForDebag) {
                    g.drawLine(location.x, location.y, p.x, p.y);
                }
            }
        }

        //отрисовка тела
        if (locationForDraw != null && locationForDraw.x < location.x) {
            playerImageForDraw = getImageForMoveToLeft();
        } else {
            playerImageForDraw = getImageForMoveToRight();
        }


        float opacity = 0.5f;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        g.drawImage(playerImageForDraw, location.x - PLAYER_IMAGE_SHIFT_X, location.y - PLAYER_IMAGE_SHIFT_Y, null);

        opacity = 1.0f;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        //отрисовка HP
        g.setColor(Color.black);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5, y-PLAYER_IMAGE_SHIFT_Y-12, PLAYER_IMAGE_SHIFT_X*2+5, 10);
        g.setColor(helthColor);
        g.fillRect(x-PLAYER_IMAGE_SHIFT_X-5+1, y-PLAYER_IMAGE_SHIFT_Y-11, (int)((PLAYER_IMAGE_SHIFT_X*2+4)*(double)helth/maxHelth), 8);

    }

    @Override
    public void update(GameController gameController) {

        if(Property.DEBUG_MODE){
            playerLocationsForDebag = new ArrayList<>();
            playerLocationsForDebag.addAll(gameController
                    .getPhysicalGameObject().stream()
                    .filter(o -> o instanceof Player)
                    .map(o -> new Point(o.getLocation())).collect(Collectors.toList()));
        }


                switch(state){
                    case patrul:

                        if(moveToLocation(targetLocation)){
                            nextCurrentFreePont();
                        }
                        //попытка найти игрока
                        objectForAttack = findPlayerForAttack(gameController);
                        if(objectForAttack!=null){
                            state = attack;
                        }
                        break;
                    case attack:
                         if(objectForAttack != null) {
                            moveToLocation(objectForAttack.getLocation(), objectForAttack.getTerritoryRadius()+ATTACK_RADIUS);
                            attackPlayer(objectForAttack);
                        } else {
                            state = patrul;
                        }
                        break;
                    case attackDistant:
                        break;
                    case escape:
                        break;
                }

    }



    private void attackPlayer(Player player) {
        if(damageTimer>=DAMAGE_PAUSE) {
            if (player.collision(location.x, location.y, TERITORY_RADIUS+ATTACK_RADIUS) <= 0) {
                damageTimer=0;
                player.addHealth(-DAMAGE);
                addHealth(DAMAGE);
            }
        } else {
            damageTimer++;
        }

        if(objectForAttack == null || objectForAttack.isDestroy()){
            state = patrul;
            objectForAttack = null;
        }
    }

    private Player findPlayerForAttack(GameController gameController) {
        List<Player> visiblePalyers = new ArrayList<>();

        List<PhysicalGameObject> playesInRadius = gameController.getPhysicalGameObject().stream() //объекты, находящиеся в радиусе виденья
                .filter(p1-> p1 instanceof Player && p1.collision(location.x, location.y, OVERVIEW_RADIUS)<=0 )
                .collect(Collectors.toList());

        for(PhysicalGameObject playerObject : playesInRadius){
            int xp = playerObject.getLocation().x;
            int yp = playerObject.getLocation().y;
            int xe = location.x;
            int ye = location.y;
            double k = ((double)ye-yp)/(xe-xp);
            double b = ye - k*xe;
            double da = 1+k*k;

            //проверяем, прячится ли игрок хоть за одним статическим объектом
            boolean hide = false;
            for(PhysicalGameObject staticObjects : gameController.getStaticPhysicalGameObjects()) {
                int x0 = staticObjects.getLocation().x;
                int y0 = staticObjects.getLocation().y;
                int r = staticObjects.getTerritoryRadius();
                double db = 2 * k * b - 2 * x0 - 2 * k * y0;
                double dc = b * b - 2 * b * y0 - r * r + x0 * x0 + y0 * y0;
                if (db * db - 4 * da * dc >= 0 && playerObject.distanceBetweenCenter(staticObjects)<distanceBetweenCenter(playerObject) && distanceBetweenCenter(staticObjects)<distanceBetweenCenter(playerObject)) {
                    hide = true; //если удалось спрятаться хоть за одним предметом
                }
            }

            if(!hide) {
                visiblePalyers.add((Player)playerObject);
            }
        }

        if(visiblePalyers.size()>0) {
            return visiblePalyers.get(new Random().nextInt(visiblePalyers.size()));
        } else {
            return null;
        }
    }


    /**
     * @param targLocation
     * @return true, если достигли цели или цели нет
     */
    private boolean moveToLocation(Point  targLocation, int r) {
        int dx = targLocation.x - location.x;
        int dy = targLocation.y - location.y;
        double l = Math.sqrt(dx*dx + dy*dy);
        if(l<=r) {
            return true;
        }
        return moveToLocation(targLocation);
    }


    /**
     * @param targLocation
     * @return true, если достигли цели или цели нет
     */
    private boolean moveToLocation(Point  targLocation) {
        //if(objectForAttack!=null){
        //    if(objectForAttack.collision(location.x, location.y, TERITORY_RADIUS + ATTACK_RADIUS)<=0){
        //        return true;
        //    }
        //}
        if(targLocation == null || location.equals(targLocation)) {return true;}
        locationForDraw = targLocation;
        int x = location.x;
        int y = location.y;

        double dx;
        double dy;
        double targetX = targLocation.getX();
        double targetY = targLocation.getY();
        if(targetX != x) {
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
            }
            dy *= Math.signum(targLocation.getY() - y);
        }

        location.x += dx;
        location.y += dy;
        if(location.equals(targetLocation)){
            return true;
        }

        return false;
    }

    private void nextCurrentFreePont() {
        currentFreePoint = currentFreePoint.getNext();
        targetLocation = currentFreePoint.getPoint();
    }


    @Override
    public Point getLocation() {
        return location;
    }

    public BufferedImage getImageForMoveToRight() {
        return imageForMoveToRight;
    }

    public BufferedImage getImageForMoveToLeft() {
        return imageForMoveToLeft;
    }

    public void trySetTargetLocation(Point point) {
        if (outsideClick(point)) {
            return;
        }
        if (clickToStaticObject(point)) {
            return;
        }

        targetLocation = point;
        if (targetLocation.x > location.x) {
            playerImageForDraw = getImageForMoveToRight();
        } else {
            playerImageForDraw = getImageForMoveToLeft();
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

    @Override
    protected void resetAction() {

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

    public static class FreeTargetPoint{

        private FreeTargetPoint beforePoint = null;

        private Point point;

        private static FreeTargetPoint p9 = null;

        private List<FreeTargetPoint> nextPoints = new ArrayList<>();

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public void setPoint(int x, int y) {
            this.point = new Point(x, y);
        }

        public List<FreeTargetPoint> getNextPoints() {
            return nextPoints;
        }

        public void setNextPoints(List<FreeTargetPoint> nextPoints) {
            this.nextPoints = nextPoints;
        }

        public void addNextPint(FreeTargetPoint point){
            nextPoints.add(point);
        }

        public FreeTargetPoint getNext() {
            int nextPointCount = nextPoints.size();
            int index = new Random().nextInt(nextPointCount);
            FreeTargetPoint next = nextPoints.get(index);
            if(next == beforePoint){
                index = (index+1)%nextPointCount;
                next = nextPoints.get(index);
            }
            next.setBeforePoint(this);

            return next;
        }

        public static FreeTargetPoint getP9(){
            return p9;
        }

        public static void setP9(FreeTargetPoint p9Input) {
            p9 = p9Input;
        }

        public void setBeforePoint(FreeTargetPoint beforePoint) {
            this.beforePoint = beforePoint;
        }
    }


    enum EnemyState{
        patrul, attack, attackDistant, escape
    }

}

