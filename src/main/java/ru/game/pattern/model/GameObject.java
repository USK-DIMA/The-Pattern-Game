package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.model.playes.Player;

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

    private GameObjectDestroyNotifer gameObjectDestroyNotifer = null;

    public GameObject() {
        this.destroy = false;
    }

    /**
     * Если надо отрисовать что-то ещё особенное под всеми объеками кроме поля
     */
    public void drawBeforeAll(Graphics2D g){

    }

    /**
     * Если надо отрисовать что-то ещё особенное под всеми объеками кроме поля
     */
    public void drawAfterAll(Graphics2D g){

    }

    /**
     * Устанавливаем объект, который будем оповещать о уничтожении данного объекта
     * @param gameObjectDestroyNotifer объект, которы йнеобходимо оповестить
     */
    public void setPlayerDestroyNotifer(GameObjectDestroyNotifer gameObjectDestroyNotifer) {
        this.gameObjectDestroyNotifer = gameObjectDestroyNotifer;
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
     * Разрушаем объект и оповещаем об этом наблюдателя
     */
    public void destroy(){
        destroy = true;
        if(gameObjectDestroyNotifer!=null) {
            gameObjectDestroyNotifer.objectIsDistroy(this);
        }
    }

    /**
     * Типы игровых объектов
     */
    public enum Type{
        player, bullet, board, staticObject, other
    }

    /**
     * Объекты, реализующие данный интерфейс можно будет оповестить об уничтожении какого-либо игрово объекта
     */
    public interface GameObjectDestroyNotifer {
        void objectIsDistroy(GameObject player);
    }

}
