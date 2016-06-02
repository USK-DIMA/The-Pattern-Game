package ru.game.pattern.controller;

import ru.game.pattern.model.*;
import ru.game.pattern.view.GameView;

import java.io.IOException;
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

    //private Player player;

    private Cursor cursor;

    private List<GameObject> allGameObjects;

    private ArrayList<PhysicalGameObject> physicalGameObjects;

    @Override
    public void run() {
        updateAll();
    }

    /**
     * @param windowInfo информация об окне
     */
    public GameControllerImpl(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        allGameObjects = new ArrayList<>();
        physicalGameObjects = new ArrayList<>();
        background = new GameBackground(windowInfo);
        cursor = new Cursor(windowInfo, physicalGameObjects);

        Player player1 = new Player(windowInfo);
        Player player2 = new Player(windowInfo);
        player2.setLocation(100, 100);
        Player player3 = new Player(windowInfo);
        player3.setLocation(300, 300);

        /**Порядок добваленных элементов аналогичен порядку отрисовке на экране */
        allGameObjects.add(background);
        allGameObjects.add(player1);
        allGameObjects.add(player2);
        allGameObjects.add(player3);
        allGameObjects.add(cursor);

        physicalGameObjects.add(player1);
        physicalGameObjects.add(player2);
        physicalGameObjects.add(player3);
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

            for(GameObject o : allGameObjects){
                o.update(this);
            }
        }
    }

    /**
     * Возвращает список всех игровых объектов
     * @return список всех игровых объектов
     */
    @Override
    public List<GameObject> getAllGameObjects(){
        return allGameObjects;
    }

    public ArrayList<PhysicalGameObject> getPhysicalGameObject() {
        return physicalGameObjects;
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