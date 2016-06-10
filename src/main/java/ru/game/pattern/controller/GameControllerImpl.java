package ru.game.pattern.controller;

import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.*;
import ru.game.pattern.view.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Класс инкапсулироует всю логику игры
 * @see ru.game.pattern.controller.GameController
 * @see java.lang.Runnable
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

    /**
     * Объект класса, содержащий в себе всю логику и отрисовку действий, связанных с курсором
     */
    private Cursor cursor;

    private GameBoard gameBoard;

    /**
     * коллекция всех игровых объектов
     */
    volatile private List<GameObject> allGameObjects;

    /**
     * коллекция всех физческих игровых объектов
     */
    volatile private ArrayList<PhysicalGameObject> physicalGameObjects;

    /**
     * Объект, куда будут передоваться только что созданные игровые объекты для регистрации листенера
     */
    volatile private ObjectNotifer objectNotifer;

    /**
     * @see java.lang.Runnable
     */
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
        allGameObjects = Collections.synchronizedList(allGameObjects);
        physicalGameObjects = new ArrayList<>();
        background = new GameBackground(windowInfo);
        cursor = new Cursor(windowInfo, physicalGameObjects);
        gameBoard = new GameBoard(windowInfo);

        Archer archer1 = new Archer(windowInfo);
        archer1.setLocation(100, 100);
        Archer archer2 = new Archer(windowInfo);
        archer2.setLocation(200, 200);
        Archer archer3 = new Archer(windowInfo);
        archer3.setLocation(300, 300);
        Warrior warrior = new Warrior(windowInfo);
        warrior.setLocation(400, 400);
        Prist prist = new Prist(windowInfo);
        prist.setLocation(500, 500);
        Mag mag = new Mag(windowInfo);
        mag.setLocation(600, 600);

        /**Порядок добваленных элементов аналогичен порядку отрисовке на экране */
        allGameObjects.add(archer1);
        allGameObjects.add(archer2);
        allGameObjects.add(archer3);
        allGameObjects.add(warrior);
        allGameObjects.add(cursor);
        allGameObjects.add(prist);
        allGameObjects.add(mag);

        physicalGameObjects.add(archer1);
        physicalGameObjects.add(archer2);
        physicalGameObjects.add(archer3);
        physicalGameObjects.add(warrior);
        physicalGameObjects.add(prist);
        physicalGameObjects.add(mag);
    }

    @Override
    public void setObjectNotifer(ObjectNotifer objectNotifer) {
        this.objectNotifer = objectNotifer;
    }

    @Override
    public WindowInfo getWindowInfo() {
        return windowInfo;
    }

    /**
     * Запуск потока Thread updateThread
     * @param gameStatus ссылка на GameStatus gameStatus. Статус игры может меняться и другуми потоками других объектов
     */
    @Override
    public void startUpdate(GameStatus gameStatus) {
        this.gameStatus = gameStatus;

        if(updateThread==null){
            synchronized (GameControllerImpl.class){ //один объект класса GameControllerImpl должен порождать только один поток для обновления
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
                Thread.sleep(Property.UPDATE_PAUSE); //просто пауза
            } catch (InterruptedException e) {
                System.err.println("Error of Thread.sleep in GameController.updateAll");
            }

            getBackgound().update(this);
            for(int i=0; i<allGameObjects.size(); i++){
                GameObject o = allGameObjects.get(i);
                if(o.isDestroy()){
                    allGameObjects.remove(o);
                    physicalGameObjects.remove(o);
                    i--;
                    continue;
                }
                o.update(this);
            }
            getGameBoard().update(this);
        }
    }

    @Override
    public List<GameObject> getAllGameObjects() {
        return allGameObjects;
    }

    @Override
    public List<GameObject> getAllGameObjectsClone(){
        return new ArrayList<>(allGameObjects);
    }

    @Override
    public ArrayList<PhysicalGameObject> getPhysicalGameObject() {
        return physicalGameObjects;
    }

    @Override
    public void addBullet(FireBall fireBall) {
        //// TODO: 04.06.2016 Пока добавим патроны в список всех объектов.
        allGameObjects.add(fireBall);
        physicalGameObjects.add(fireBall);
    }

    @Override
    public GameBackground getBackgound() {
        return background;
    }

    @Override
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void addPlayer(Player player) {
        allGameObjects.add(player);
        physicalGameObjects.add(player);
        objectNotifer.addListeners(player);
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
