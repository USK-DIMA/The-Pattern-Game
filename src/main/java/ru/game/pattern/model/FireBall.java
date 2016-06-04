package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.GameStatus;
import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 03.06.2016.
 */
public class FireBall extends PhysicalGameObject {

    private static final int SPEED = 20;

    private static final int territoryRadius = 2;

    private static final int MAX_DISTANSE = 300;

    private static final int DAMAGE = 25;

    private GameObject parant;

    private static BufferedImage image;

    private int dx;

    private int dy;

    private Point startLocation;

    public FireBall(Point location, Point targetLocation, int objectTerritoryRadius, GameObject parant) throws IOException {
        super(1);
        if(targetLocation.equals(location)){
            destroy=true;
            return;
        }
        this.location = location;
        this.startLocation = new Point(location);
        this.parant=parant;
        double targetX = targetLocation.getX();
        double targetY = targetLocation.getY();
        //// TODO: 04.06.2016 По желанию можно убрать теперь, т.к. теперь каждый fireball знает своего родителя и не будет ему наносить урон
        int radius = objectTerritoryRadius+territoryRadius; //шар начинает движение не из центра объекта, а чуть дальше.
        if(image==null){
            image = ImageIO.read(new File(Property.RESOURSES_PATH + "fireball_min.png"));
        }

        if(targetX - location.x!=0) {
            double tan = Math.abs((targetY - location.y) / (targetX - location.x));
            dx = (int)(SPEED / Math.sqrt(1 + tan * tan));
            dx*= Math.signum(targetX - location.x);

            dy = (int)Math.abs(dx * tan);
            dy *= Math.signum(targetY - location.y);
        }
        else {
            dx=0;
            if(SPEED <Math.abs(targetY - location.y)) {
                dy = SPEED;
            } else {
                dy = (int)Math.abs(targetY - location.y);
            }
            dy*=Math.signum(targetLocation.getY() - location.y);
        }

        int n = (int)((Math.sqrt(dx*dx + dy+ dy)/radius)+1);
        this.location.x+=dx*n;
        this.location.y+=dy*n;
    }

    public boolean isDestroy() {
        return destroy;
    }

    @Override
    public void setClickCursorLocation(Point point, boolean isShiftDown) {

    }


    @Override
    void resetAction() {

    }

    @Override
    public int getTerritoryRadius() {
        return territoryRadius;
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
        if(destroy){ return;}
        g.drawImage(image, location.x-5, location.y-5, null);
    }

    @Override
    public void update(GameController gameController) {
        if(location.x>gameController.getWindowInfo().getWidth()+territoryRadius || location.x<-territoryRadius ||
          location.y>gameController.getWindowInfo().getHeight()+territoryRadius || location.y<-territoryRadius){
            System.out.println("D1");
            destroy = true;
        }
        for(PhysicalGameObject o : gameController.getPhysicalGameObject()){
            if(o==parant){
                continue;//если патрон выпустил этот объект
            }
            if(o.collision(this)<=0){
                o.addHelth(-DAMAGE);
                System.out.println("D2");
                destroy = true;
                break;
            }
        }
        this.location.x += dx;
        this.location.y += dy;
        int dx = location.x - startLocation.x;
        int dy = location.y - location.y;
        if(Math.sqrt(dx*dx + dy*dy)>=MAX_DISTANSE){
            destroy = true;
        }
    }

    @Override
    public Type getType() {
        return Type.bullet;
    }
}
