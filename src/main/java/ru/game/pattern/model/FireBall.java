package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 03.06.2016.
 */

/**
 * Класс: Объект-пуля. Объектами-пулями стреляют герои дальнего боя.
 * Этот класс представляет собой одну реализацию такого класса.
 * Объекты данного класса будут создаваться во время стрельбы.
 */
public abstract class FireBall extends PhysicalGameObject {

    private final int speed;

    private final int territoryRadius;

    private final int maxDistanse;

    private final int damage;

    private GameObject parant;

    private double dx;

    private double dy;

    private Point startLocation;

    public FireBall(Point location, Point targetLocation, int objectTerritoryRadius, GameObject parant, int territoryRadius, int maxDistanse, int damage, int speed) throws IOException {
        super(1);
        this.territoryRadius = territoryRadius;
        this.maxDistanse = maxDistanse;
        this.damage = damage;
        this.speed = speed;
        if(targetLocation.equals(location)){
            destroy();
            return;
        }
        this.location = location;
        this.startLocation = new Point(location);
        this.parant=parant;
        double targetX = targetLocation.getX();
        double targetY = targetLocation.getY();
        //// TODO: 04.06.2016 возможно можно убрать теперь, т.к. теперь каждый fireball знает своего родителя и не будет ему наносить урон
        int radius = objectTerritoryRadius+territoryRadius; //шар начинает движение не из центра объекта, а чуть дальше.

        if(targetX - location.x!=0) {
            double tan = Math.abs((targetY - location.y) / (targetX - location.x));
            dx = (int)(getSpeed() / Math.sqrt(1 + tan * tan));
            dx*= Math.signum(targetX - location.x);

            dy = (int)Math.abs(dx * tan);
            dy *= Math.signum(targetY - location.y);
        }
        else {
            dx=0;
            if(getSpeed() <Math.abs(targetY - location.y)) {
                dy = getSpeed();
            } else {
                dy = (int)Math.abs(targetY - location.y);
            }
            dy*=Math.signum(targetLocation.getY() - location.y);
        }

        int n = (int)((Math.sqrt(dx*dx + dy+ dy)/radius)+1);
        this.location.x+=dx*n;
        this.location.y+=dy*n;
    }

    @Override
    protected void resetAction() {

    }

    @Override
    public int getTerritoryRadius() {
        return territoryRadius;
    }

    @Override
    public int getSpeed() {
        return speed;
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
        if(isDestroy()){ return;}
        g.drawImage(getImageForDraw(), location.x-5, location.y-5, null);
    }

    @Override
    public void update(GameController gameController) {
        if(location.x>gameController.getWindowInfo().getWidth()+territoryRadius || location.x<-territoryRadius ||
          location.y>gameController.getWindowInfo().getHeight()+territoryRadius || location.y<-territoryRadius){
            destroy();
        }
        for(PhysicalGameObject o : gameController.getPhysicalGameObject()){
            if(o==parant){
                continue;//если патрон выпустил этот объект
            }
            if(o.collision(this)<=0){
                o.addHealth(-damage);
                destroy();
                break;
            }
        }
        this.location.x += dx*getOneMultiSpeed();
        this.location.y += dy*getOneMultiSpeed();
        int dx = location.x - startLocation.x;
        int dy = location.y - location.y;
        if(Math.sqrt(dx*dx + dy*dy)>= maxDistanse){
            destroy();
        }
    }

    @Override
    public Type getType() {
        return Type.bullet;
    }

    abstract protected BufferedImage getImageForDraw();
}
