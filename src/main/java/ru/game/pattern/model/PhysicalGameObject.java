package ru.game.pattern.model;

import java.awt.*;

/**
 * Created by Uskov Dmitry on 02.06.2016.
 */
public abstract class PhysicalGameObject extends GameObject {

    public boolean isSeletedByCursor(){
        return false;
    };

    public void setSelectedByCursor(boolean selectedByCursor){

    }

    abstract public void setClickCursorLocation(Point point);

    abstract public Point getLocation();

    /**
     * отмена выполнения всех действий
     */
    abstract void resetAction();

    /**
     * возвращает расстояние между объектами.
     * @param gameObject  объект, с которым проверяется столкновение
     * @return расстояние между объектами. Если обкте налегли друг на друга, то число отрицательное.
     */
    abstract public int collision(PhysicalGameObject gameObject);

    /**
     * Возвращает радиус объекта (то пространство, которое он занимает)
     * @return
     */
    abstract public int getTerritoryRadius();

    public abstract int collision(double x, double y, int teritoryRadius);
}
