package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;

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

    protected boolean destroy;

    public GameObject() {
        this.destroy = false;
    }

    /**
     * Если надо отрисовать что-то ещё особенное под всеми объеками кроме поля
     */
    public void drawSpecialBeforeAll(Graphics2D g){

    }

    /**
     * Возвращает обработчик игрового объекта
     */
    public abstract KeyListener getKeyListener();


    /**
     * Возвращает обработчик игрового объекта
     */
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
    abstract public void update(GameController gameController);

    /**
     * Возвращает тип объекта
     * @return тип объекта
     */
    abstract public Type getType();

    public boolean isDestroy() {
        return destroy;
    }

    /**
     * Типы игровых объектов
     */
    public enum Type{
        player, bullet, board, other
    }

}
