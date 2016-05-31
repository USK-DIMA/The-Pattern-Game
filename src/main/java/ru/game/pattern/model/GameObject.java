package ru.game.pattern.model;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Родительский класс для всекх игровых обхъектов
 */
abstract public class GameObject {


    public abstract KeyListener getKeyListener();

    public abstract MouseListener getMouseListener();

    /**
     * В метожн происходит отрисовка объекта игры
     * @param g объект, на котором должна происходить отрисовка
     */
    abstract public void draw(Graphics2D g);

    /**
     * В методе должно происходить обновление состояния объекта
     * (например изменение положение его координат)
     */
    abstract public void update();


    abstract public Type getType();

    public boolean isSeletedByCursor(){
        return false;
    };

    public void setClickCursorLocation(Point point){

    }

    public  void setSelectedByCursor(boolean selectedByCursor){//не всем объектам надо быть выделяемыми курсором

    };

    public Point getLocation(){
        return null;
    }

    public enum Type{
        player, other
    }

}
