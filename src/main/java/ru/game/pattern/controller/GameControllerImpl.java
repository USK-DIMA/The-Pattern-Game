package ru.game.pattern.controller;

import ru.game.pattern.model.*;
import ru.game.pattern.model.Cursor;
import ru.game.pattern.model.playes.*;
import ru.game.pattern.model.staticObjects.*;
import ru.game.pattern.view.GameView;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<StaticPhysicalGameObject> staticPhysicalGameObjects;

    private Castle castle;
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
        staticPhysicalGameObjects = new ArrayList<>();
        background = new GameBackground(windowInfo);
        cursor = new Cursor(windowInfo, physicalGameObjects);
        gameBoard = new GameBoard(windowInfo);

        initStaticObjects();
        initEnemy();
        allGameObjects.add(cursor);
        castle = new Castle(new Point(1000, 300));
        addStaticObject(castle);
        //allGameObjects.add(castle);
        Player.setStaticObjects(getStaticPhysicalGameObjects());
    }

    private void initEnemy() throws IOException {
        Enemy object = new Enemy(100, windowInfo);
        object.setLocation(new Point(1091,34));
        object.setPlayerDestroyNotifer(gameBoard);
        allGameObjects.add(object);
        physicalGameObjects.add(object);
/*
        object = new Enemy(100, windowInfo);
        object.setLocation(new Point(1091,34));
        allGameObjects.add(object);
        physicalGameObjects.add(object);

        object = new Enemy(100, windowInfo);
        object.setLocation(new Point(1091,34));
        allGameObjects.add(object);
        physicalGameObjects.add(object);

        object = new Enemy(100, windowInfo);
        object.setLocation(new Point(1091,34));
        allGameObjects.add(object);
        physicalGameObjects.add(object);

        object = new Enemy(100, windowInfo);
        object.setLocation(new Point(1091,34));
        allGameObjects.add(object);
        physicalGameObjects.add(object);
        */
    }

    private void initStaticObjects() throws IOException{
        initInvisibleStaticObject();

        addStaticObject(new Stone(new Point(323, 542)));
        addStaticObject(new Stone(new Point(70, 198)));
        addStaticObject(new Stone(new Point(600, 103)));
        addStaticObject(new Stone(new Point(798, 353)));
        addStaticObject(new Stone(new Point(1022, 348)));
        addStaticObject(new Stone(new Point(1022, 348)));


        addStaticObject(new SmallTree(new Point(585, 590)));
        addStaticObject(new MediumTree(new Point(528, 615)));
        addStaticObject(new BigTree(new Point(566, 629)));

        addStaticObject(new MediumTree(new Point(407, 406)));
        addStaticObject(new MediumTree(new Point(658, 220)));

        addStaticObject(new SmallTree(new Point(277, 182)));
        addStaticObject(new MediumTree(new Point(257, 192)));

        addStaticObject(new MediumTree(new Point(219, 642)));
        addStaticObject(new MediumTree(new Point(901, 648)));

        addStaticObject(new BigTree(new Point(985, 281)));

        addStaticObject(new BigTree(new Point(855, 501)));
        addStaticObject(new SmallTree(new Point(800, 499)));
    }

    private void initInvisibleStaticObject() throws IOException {
        addStaticObject(new InvisibleStaticObject(new Point(85, 70), 13));
        addStaticObject(new InvisibleStaticObject(new Point(1100, 650), 20));
        addStaticObject(new InvisibleStaticObject(new Point(1125, 625), 20));
        addStaticObject(new InvisibleStaticObject(new Point(1150, 600), 20));
        addStaticObject(new InvisibleStaticObject(new Point(757, 80), 18));
        addStaticObject(new InvisibleStaticObject(new Point(757, 55), 18));
        addStaticObject(new InvisibleStaticObject(new Point(329, 81), 15));

        addStaticObject(new InvisibleStaticObject(new Point(49, 358), 14));
        addStaticObject(new InvisibleStaticObject(new Point(1137, 433), 13));

        addStaticObject(new InvisibleStaticObject(new Point(11, 443), 18));

        addStaticObject(new InvisibleStaticObject(new Point(20, 313), 18));

        addStaticObject(new InvisibleStaticObject(new Point(1175, 540), 60));

        addStaticObject(new InvisibleStaticObject(new Point(1225, 490), 60));

        addStaticObject(new InvisibleStaticObject(new Point(910, 75), 60));

        addStaticObject(new InvisibleStaticObject(new Point(25, 655), 20));

        addStaticObject(new InvisibleStaticObject(new Point(952, 706), 35));
        addStaticObject(new InvisibleStaticObject(new Point(1007, 695), 35));
        addStaticObject(new InvisibleStaticObject(new Point(1064, 710), 30));
        addStaticObject(new InvisibleStaticObject(new Point(1221, 674), 65));


        addStaticObject(new InvisibleStaticObject(new Point(8, 115), 35));

        addStaticObject(new InvisibleStaticObject(new Point(1171, 261), 20));
        addStaticObject(new InvisibleStaticObject(new Point(1199, 229), 20));
        addStaticObject(new InvisibleStaticObject(new Point(1255, 164), 45));
        addStaticObject(new InvisibleStaticObject(new Point(1262, 278), 45));

        addStaticObject(new InvisibleStaticObject(new Point(1254, 422), 40));

        addStaticObject(new InvisibleStaticObject(new Point(1345, 0), 200));

        addStaticObject(new InvisibleStaticObject(new Point(986, 83), 30));
        addStaticObject(new InvisibleStaticObject(new Point(989, 15), 45));

        addStaticObject(new InvisibleStaticObject(new Point(145, 690), 10));
        addStaticObject(new InvisibleStaticObject(new Point(85, 690), 10));

        addStaticObject(new InvisibleStaticObject(new Point(140, 675), 10));
        addStaticObject(new InvisibleStaticObject(new Point(90, 675), 10));


        addStaticObject(new InvisibleStaticObject(new Point(172, 693), 20));

        addStaticObject(new InvisibleStaticObject(new Point(61, 698), 18));

        addStaticObject(new InvisibleStaticObject(new Point(694, -5), 50));
    }

    private void addStaticObject(StaticPhysicalGameObject object) {
        allGameObjects.add(object);
        physicalGameObjects.add(object);
        staticPhysicalGameObjects.add(object);
    }

    @Override
    public List<StaticPhysicalGameObject> getStaticPhysicalGameObjects() {
        return staticPhysicalGameObjects;
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
    public List<Enemy> getEnemy() {
        List<Enemy> enemies = allGameObjects.stream()
                .filter(o -> o instanceof Enemy)
                .map(o -> (Enemy) o)
                .collect(Collectors.toList());
        return enemies;
    }

    @Override
    public Castle getCastle() {
        return castle;
    }

    @Override
    public void endGame() {
        background.endGame();
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

    @Override
    public void winGame() {
        background.winGame();
    }
}
