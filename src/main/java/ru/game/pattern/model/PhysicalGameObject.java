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
     * Максимальное кол-во здоровья объекта
     */
    protected int maxHelth;

    /**
     * Кол-во здоровья объекта
     */
    protected int helth;

    private double multSpeed = 1;

    public PhysicalGameObject(int maxHelth) {
        this.maxHelth = maxHelth;
        this.helth = maxHelth;
    }

    /**
     * Провека, выбран ли данный объект курсором
     * Данный метод является методом по умолчанию для всех дочерних классов.
     * По умолчанию объекты нельзя выделить курсором.
     * Чтобы реализовать эту возможность надо переопределить данный метод в дочернем классе, реализовавв там необходимую логику
     *
     * @return true, если выбран, иначе false
     */
    public boolean isSeletedByCursor() {
        return false;
    }

    ;

    /**
     * Передает информацию объекту, выбран ли он курсором.
     * Данный метод является методом по умолчанию для всех дочерних классов.
     * По умолчанию объекты нельзя выделить курсором. Поэтому этот метод пустой
     * Чтобы реализовать эту возможность надо переопределить данный метод в дочернем классе, реализовавв там необходимую логику
     *
     * @param selectedByCursor true, если объект выделен курсором, иначе false
     */
    public void setSelectedByCursor(boolean selectedByCursor) {

    }


    /**
     * Возвращает местоположение объекта на карте без учёта его размера (грубо говоря, возвращает координаты центра объекта)
     *
     * @return местоположение объекта на карте
     */
    public Point getLocation() {
        return location;
    }

    /**
     * отмена выполнения всех действий
     */
    protected abstract void resetAction();

    /**
     * Возвращает радиус объекта (т.е. пространство, которое он занимает)
     *
     * @return
     */
    abstract public int getTerritoryRadius();

    /**
     * Возвращает расстояние между объектами.
     *
     * @param gameObject объект, с которым проверяется столкновение.
     * @return расстояние между объектами. Если объекты налегли друг на друга, то число отрицательное.
     * Если передана ссылка на этот же объект возвращает ~0.
     * Если gameObject==null, возвращает ~0.
     */
    public int collision(PhysicalGameObject gameObject) {
        if (gameObject == null) {
            return Integer.MAX_VALUE;
        }
        if (gameObject == this) {
            return Integer.MAX_VALUE;
        }
        int x = location.x;
        int y = location.y;
        int x2 = gameObject.getLocation().x;
        int y2 = gameObject.getLocation().y;
        double length = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return (int) (length - (getTerritoryRadius() + gameObject.getTerritoryRadius()));
    }

    /**
     * Возвращает расстояние между объектами.
     * Аналог функции abstract public int collision(PhysicalGameObject gameObject), но с другими вход параметрами
     *
     * @param x              координата x объекта, с которым проверяем столкновение
     * @param y              координата y объекта, с которым проверяем столкновение
     * @param teritoryRadius радиус, обозначающий размер объекта, с которым проверяем столкновение
     * @return расстояние между объектами. Если объекты налегли друг на друга, то число отрицательное.
     */
    public int collision(double x, double y, int teritoryRadius) {
        int x2 = location.x;
        int y2 = location.y;
        double length = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return (int) (length - (getTerritoryRadius() + teritoryRadius));
    }

    /**
     * Добавляет жизней персонажу, но не может добавить больше максимума. Отрицательное значение соответсвует урону.
     * Если жизней меньше или равно 0, объект сачитаетсы уничтоженным, т.е. destroy = true;
     *
     * @param h на сколько надо увеличить жизни
     */
    public void addHealth(int h) {
        helth += h;
        if (helth > maxHelth) {
            helth = maxHelth;
        }
        if (helth <= 0) {
            if (!isDestroy()) {
                destroy();
            }
        }
    }

    /**
     * Находит расстояние между центрами объктов
     *
     * @param object объект, с которым надо найти расстояние от центров
     * @return расстояние между центрами.
     */
    public double distanceBetweenCenter(PhysicalGameObject object) {
        int dx = object.location.x - location.x;
        int dy = object.location.y - location.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Находит расстояние между краями объктов (т.е минимальное между ними)
     *
     * @param object объект, с которым надо найти расстояние от краёв
     * @return расстояние между объектами. Число отрицательное, если объекты налегли дрург на друга
     */
    public double distanceBetweenEdge(PhysicalGameObject object) {
        int dx = object.location.x - location.x;
        int dy = object.location.y - location.y;
        return Math.sqrt(dx * dx + dy * dy) - object.getTerritoryRadius() - this.getTerritoryRadius();
    }

    /**
     * Возвращает скокрость объекта
     *
     * @return скорость объекта
     */
    public abstract int getSpeed();

    /**
     * Устанавдивает множитель скорости на одну итерацию
     *
     * @param multSpeed
     */
    public void setOneMultiSpeed(double multSpeed) {
        this.multSpeed = multSpeed;
    }

    /**
     * Возвращает множитель скорости, обнуляя его
     *
     * @return
     */
    public double getOneMultiSpeed() {
        double ans = multSpeed;
        multSpeed = 1;
        return ans;
    }

}
