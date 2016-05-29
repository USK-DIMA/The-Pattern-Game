package ru.game.pattern.controller;

import ru.game.pattern.model.GameBackground;
import ru.game.pattern.model.GameObject;
import ru.game.pattern.model.Player;
import ru.game.pattern.model.WindowInfo;
import ru.game.pattern.view.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Класс инкапсулироует всю логику игры
 */
public class GameControllerImpl implements GameController, Runnable{

    /**
     * Поток для обновления состояний игровых объектов. (например изменения координат на экране)
     */
    private  Thread updateThread;

    /**
     * Статус игры. Есди гра окончена, то поток Thread updateThread завершится.
     */
    volatile private GameStatus gameStatus;

    /**
     * Объект, отвечающий за изображение на фоне. Можно сделать фон динамический.
     */
    private GameBackground background;

    /**
     * Объект, содержащий в себе всю информацию об окне (например размеры окна в пикселях)
     */
    private WindowInfo windowInfo;


    private Player player;

    @Override
    public void run() {
        updateAll();
    }

    /**
     * @param windowInfo информация об окне
     */
    public GameControllerImpl(WindowInfo windowInfo) {
        this.windowInfo = windowInfo;
        player = new Player(windowInfo);
        background = new GameBackground(windowInfo);
    }

    /**
     * Запуск потока Thread updateThread
     * @param gameStatus ссылка на GameStatus gameStatus. Статус игры может меняться и другуми потоками других объектов
     */
    @Override
    public void startUpdate(GameStatus gameStatus) {
        this.gameStatus = gameStatus;

        if(updateThread==null){
            synchronized (GameControllerImpl.class){
                if(updateThread==null) {
                    updateThread = new Thread(this);
                    updateThread.start();
                }
            }
        }
    }

    /**
     * Метод, обновляющий все игровые объекты
     */
    @Override
    public void updateAll() {
        while (gameStatus.isRun()){
            try {
                Thread.sleep(Property.UPDATE_PAUSE);
            } catch (InterruptedException e) {
                System.err.println("Error of Thread.sleep in GameController.updateAll");
            }
            background.update();
            player.update();
            //// TODO: 27.05.2016 Обновить состояние всех объектов
        }
    }

    /**
     * Возвращает список всех игровых объектов
     * @return список всех игровых объектов
     */
    @Override
    public List<GameObject> getAllGameObjects(){
        List<GameObject> gameObjects =  new ArrayList<>();
        gameObjects.add(background);
        gameObjects.add(player);
        //// TODO: 27.05.2016 Придумать, как хранить и передовать все объекты
        return gameObjects;
    }


    @Override
    protected void finalize() throws Throwable {
        if(gameStatus.isRun()){
            synchronized (GameView.class){
                if(gameStatus.isRun()){
                    gameStatus.stopGame(); // В случае уничтожения объектра GameController, остановить игру
                }
            }
        }
    }

}
