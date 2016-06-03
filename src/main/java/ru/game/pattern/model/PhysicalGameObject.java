package ru.game.pattern.model;

import java.awt.*;

/**
 * Created by Uskov Dmitry on 02.06.2016.
 */

/**
 * Родительский класс всех физических игровы объектов, т.е. тех, которые будут размещены на поле
 * содеражат в себе расширенный необходимый функционал по сравнению с классом GameObject
 */
public abstract class PhysicalGameObject extends GameObject {

    /**
     * местонахождение объекта
     */
    protected Point location;

    /**
     * Провека, выбран ли данный объект курсором
     * Данный метод является методом по умолчанию для всех дочерних классов.
     * По умолчанию объекты нельзя выделить курсором.
     * Чтобы реализовать эту возможность надо переопределить данный метод в дочернем классе, реализовавв там необходимую логику
     * @return true, если выбран, иначе false
     */
    public boolean isSeletedByCursor(){
        return false;
    };

    /**
     * Передает информацию объекту, выбран ли он курсором.
     * Данный метод является методом по умолчанию для всех дочерних классов.
     * По умолчанию объекты нельзя выделить курсором. Поэтому этот метод пустой
     * Чтобы реализовать эту возможность надо переопределить данный метод в дочернем классе, реализовавв там необходимую логику
     * @param selectedByCursor true, если объект выделен курсором, иначе false
     */
    public void setSelectedByCursor(boolean selectedByCursor){

    }

    /**
     * в данный метод передаются координаты, куда клинкнули курсором на экране (как приавло, нажатие ПКМ)
     * P.S. Как правило, этот метод вызывается, только у тех объектов, у котороых isSeletedByCursor() = true.
     * Но некоторым объектам иногда неоходимо получать информацию всегда обо всех кликах (как правило ПКМ) на экране.
     * @param point координаты, куда кликнули курсором.
     */
    abstract public void setClickCursorLocation(Point point);

    /**
     * Возвращает местоположение объекта на карте без учёта его размера (грубо говоря, возвращает координаты центра объекта)
     * @return местоположение объекта на карте
     */
    public Point getLocation(){
        return location;
    }

    /**
     * отмена выполнения всех действий
     */
    abstract void resetAction();

    /**
     * Возвращает радиус объекта (то пространство, которое он занимает)
     * @return
     */
    abstract public int getTerritoryRadius();

    /**
     * Возвращает расстояние между объектами.
     * @param gameObject  объект, с которым проверяется столкновение
     * @return расстояние между объектами. Если объекты налегли друг на друга, то число отрицательное.
     */
     public int collision(PhysicalGameObject gameObject){
        if(gameObject==null){ return 0;}
        int x = location.x;
        int y = location.y;
        int x2 = gameObject.getLocation().x;
        int y2 = gameObject.getLocation().y;
        double length = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return (int)(length - (getTerritoryRadius() + gameObject.getTerritoryRadius()));
    }
    /**
     * Возвращает расстояние между объектами.
     * Аналог функции abstract public int collision(PhysicalGameObject gameObject), но с другими вход параметрами
     * @param x координата x объекта, с которым проверяем столкновение
     * @param y координата y объекта, с которым проверяем столкновение
     * @param teritoryRadius радиус, обозначающий размер объекта, с которым проверяем столкновение
     * @return расстояние между объектами. Если объекты налегли друг на друга, то число отрицательное.
     */
    public int collision(double x, double y, int teritoryRadius){
        int x2 = location.x;
        int y2 = location.y;
        double length = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return (int)(length - (getTerritoryRadius() + teritoryRadius));
    }

    /**
     * Возвращает Point, где Point.x соответсвует dx, Point.y соответсвует dy,
     * на основании местонахождения объекта, точку куда надо придыть и скорости объекта
     * @param location
     * @param targetLocation
     * @param speed
     * @return
     */
    /*public static Point getDeltaBySpeed(Point location, Point targetLocation, int speed){
        int x = location.x;
        int y = location.y;
        double dx;
        double dy;
        double targetX = targetLocation.getX();
        double targetY = targetLocation.getY();
        if(targetX - x!=0) {
            double tan = Math.abs((targetY - y) / (targetX - x));
            dx = speed / Math.sqrt(1 + tan * tan);
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
            if(speed <Math.abs(targetY - y)) {
                dy = speed;
            } else {
                dy = Math.abs(targetY - y);
            }
            dy*=Math.signum(targetLocation.getY() - y);
        }
        return new Point((int)dx, (int)dy);
    }*/
}
