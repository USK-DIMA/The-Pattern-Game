package ru.game.pattern.controller;

import ru.game.pattern.model.GameObject;
import ru.game.pattern.model.WindowInfo;

import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public interface GameController {

    /**
     * Запуск потока Thread updateThread
     * @param gameStatus ссылка на GameStatus gameStatus. Статус игры может меняться и другуми потоками других объектов
     * @param windowInfo информация об окне
     */
    void startUpdate(GameStatus gameStatus, WindowInfo windowInfo);

    /**
     * Метод, обновляющий все игровые объекты
     */
    void updateAll();
    
    /**
     * Возвращает список всех игровых объектов
     * @return список всех игровых объектов
     */
    List<GameObject> getAllGameObjects();
}
