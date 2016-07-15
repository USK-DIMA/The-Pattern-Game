package ru.game.pattern.controller;

import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.Player;
import ru.game.pattern.model.staticObjects.Castle;
import ru.game.pattern.model.staticObjects.StaticPhysicalGameObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public interface GameController {

    void addEnemies(int enemyCount) throws IOException;

    List<StaticPhysicalGameObject> getStaticPhysicalGameObjects();

    void setObjectNotifer(GameControllerImpl.ObjectNotifer objectNotifer);

    WindowInfo getWindowInfo();

    /**
     * Запуск потока Thread updateThread
     * @param gameStatus ссылка на GameStatus gameStatus. Статус игры может меняться и другуми потоками других объектов
     */
    void startUpdate(GameStatus gameStatus);

    /**
     * Метод, обновляющий все игровые объекты
     */
    void updateAll();

    /**
     * Возвращает список всех игровых объектов
     * @return список всех игровых объектов
     */
    List<GameObject> getAllGameObjects();

    List<GameObject> getAllGameObjectsClone();

    /**
     * Возвращает список всех Физических игровых объектов (те, которые будут объектами на карте: игроки, элементы ландшафта и прочие)
     * @return список всех Физических игровых объектов
     */
    ArrayList<PhysicalGameObject> getPhysicalGameObject();

    //// TODO: 04.06.2016 Скорее всего надо будет создать общий родительсукий класс Bullet пока будет захардкожен только FireBall
    void addBullet(FireBall fireBall);

    GameBackground getBackgound();

    GameBoard getGameBoard();

    void addPlayer(Player player);

    List<Enemy> getEnemy();

    Castle getCastle();

    void endGame();

    void winGame();

    Menu getMenu();

    Cursor getCursor();

    public static interface ObjectNotifer{
        void addListeners(GameObject object);
    }
}
