package ru.game.pattern.view;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.GameStatus;
import ru.game.pattern.model.GameObject;
import ru.game.pattern.model.WindowInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Класс, отвечающий за визульное представление игры
 */
public class GameView implements  Runnable{

    public static final int WIDTH = Property.WINDOW_WIDTH;

    public static final int HEIGHT = Property.WINDOW_HEIGHT;

    /**
     * Окно игры
     */
    private final JFrame mainFrame = new JFrame("The Pattern Game");

    /**
     * Игровая панель
     */
    private final GamePanel gamePanel = new GamePanel(WIDTH, HEIGHT);

    /**
     * Контроллер. В нём сокрыта логика игры
     */
    private GameController gameController;

    /**
     * Поток, в котором будет происходить отрисовка всех игровых объектов на объекте BufferedImage image через объект Graphics2D g
     */
    private Thread drawThread;

    /**
     * На данном объекте будет происхордить отрисовка всех игровых объектов, посчле чего этот объект выведется на экран
     */
    private BufferedImage image;


    /**
     * Данный объект принадлежит объекту BufferedImage image,
     * т.е.
     * image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
     * g = (Graphics2D) image.getGraphics();
     * через данный объект происходит отрисовка
     */
    private Graphics2D g;

    /**
     * Данный объект содержит всю необходимую информацию об окне.
     * инициализируется в методе  public void startGame(...)
     */
    private WindowInfo windowInfo;

    volatile private GameStatus gameStatus;

    public GameView() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(WIDTH+Property.WINDOW_BOARD, HEIGHT+Property.WINDOW_BAR_HEIGHT)); //+ оконная рамка и + верхня панель окна с названием и иконкойыв
        mainFrame.pack();
        mainFrame.setContentPane(gamePanel);
        mainFrame.setLocationRelativeTo(null);
        windowInfo = new WindowInfo(WIDTH, HEIGHT, mainFrame);
        windowInfo.setWindowBoard(Property.WINDOW_BOARD);
        windowInfo.setWindowsBarHeight(Property.WINDOW_BAR_HEIGHT);
    }

    public void startGame(GameController gameController) {
        this.gameController = gameController;
        gameStatus = new GameStatus();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gameController.startUpdate(gameStatus);
        gamePanel.addGameObjectListeners(gameController.getAllGameObjects());
        gamePanel.addGameObjectListener(gameController.getGameBoard());
        this.startDraw();
        mainFrame.setVisible(true);
    }


    public WindowInfo getWindowInfo() {
        return windowInfo;
    }

    /**
     * Запуск потока для отрисовки элоементов
     */
    private void startDraw() {
        if(drawThread==null){
            synchronized (GameView.class){
                if(drawThread==null) {
                    drawThread = new Thread(this);
                    drawThread.start();
                }
            }
        }
    }

    @Override
    public void run() {
        drawAll();
    }

    /**
     * Метод, отрисовывающий все игровые объекты на BufferedImage image с помощью Graphics2D g.
     */
    private void drawAll() {
        while (gameStatus.isRun()){
            try {
                Thread.sleep(1000/Property.FPS);
            } catch (InterruptedException e) {
                System.err.println("Error of Thread.sleep in GameView.drawAll");
            }

            gameController.getBackgound().draw(g);

            List<GameObject> gameObjectList = gameController.getAllGameObjectsClone();
            for(GameObject o : gameObjectList){
                o.drawBeforeAll(g); //Отрисовка объектов из контроллера
            }
            for(GameObject o : gameObjectList) {
                    o.draw(g); //Отрисовка объектов из контроллера
            }
            for(GameObject o : gameObjectList){
                o.drawAfterAll(g); //Отрисовка объектов из контроллера
            }
            gameController.getGameBoard().draw(g);
            gameController.getBackgound().drawAfterAll(g);

            if(gameStatus.isPause()){
                g.setColor(new Color(0,0,0, 170));
                g.fillRect(0,0, windowInfo.getWidth(), windowInfo.getHeight());
                g.setColor(Color.WHITE);
                g.drawString("Pause", windowInfo.getWidth()/2 , windowInfo.getHeight()/2);
            }


            gameDraw();
        }
    }

    /**
     * Вывод изображения, содержащего в себе отрисованные объекты (т.е BufferedImage image)на экран
     */
    private void gameDraw() {
        Graphics g2 = gamePanel.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }


    @Override
    protected void finalize() throws Throwable {
        if(gameStatus.isRun()){
            synchronized (GameView.class){
                if(gameStatus.isRun()){
                    gameStatus.stopGame(); // В случае уничтожения объектра GameView, остановить игру
                }
            }
        }
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
