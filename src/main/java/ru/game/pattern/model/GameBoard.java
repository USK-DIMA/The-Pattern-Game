package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.fabrica.PlayerFabrica;
import ru.game.pattern.model.fabrica.PlayerFabricaLvl1;
import ru.game.pattern.model.fabrica.PlayerFabricaLvl2;
import ru.game.pattern.model.fabrica.PlayerFabricaLvl3;
import ru.game.pattern.model.playes.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.model.GameObject.Type.board;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class GameBoard extends GameObject implements GameObject.GameObjectDestroyNotifer{

    private static final int BORDER = 2;

    private static final int IMAGE_SIZE = 36;

    private static final int COST_HEIGHT = 15;

    private static final int INFO_WIDTH = 70;

    private WindowInfo windowInfo;

    private PlayerFabrica playerFabrica;

    private BufferedImage archerImage;

    private BufferedImage warriorImage;

    private BufferedImage pristImage;

    private BufferedImage magImage;

    private BufferedImage moneyImage;

    private int archerCost;

    private int warriorCost;

    private int pristCost;

    private int magCost;

    private int wight;

    private int height;

    private int nextUpdate;

    private int money = 2000;

    private int playerCount = 0;

    private int maxPlayerCount = 6;

    private int tryBuyPlayerNumber = -1;

    private KeyListener keyListener;

    private int BUY_PAUSE = 5;

    private int buyTimer = BUY_PAUSE;

    Color impossibleByColor = new Color(100, 100, 100);

    private boolean isLvlUp = false;

    public GameBoard(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        moneyImage = ImageIO.read(new File(Property.RESOURSES_PATH+"money.png"));
        initFabricaByLvl(1);
        keyListener = new GameBoardKeyListener();
        wight = 4 * (IMAGE_SIZE + BORDER) + BORDER + INFO_WIDTH;
        height = IMAGE_SIZE + 2 * BORDER + COST_HEIGHT;
    }

    private void initFabricaByLvl(int i) throws IOException {
        switch (i){
            case 1: playerFabrica = new PlayerFabricaLvl1();
                break;
            case 2: playerFabrica = new PlayerFabricaLvl2();
                break;
            case 3: playerFabrica = new PlayerFabricaLvl3();
                break;
            default: return;
        }

        archerImage = ImageIO.read(new File(playerFabrica.getArcherInfo().getIconPath()));
        warriorImage = ImageIO.read(new File(playerFabrica.getWarriorInfo().getIconPath()));
        pristImage = ImageIO.read(new File(playerFabrica.getPristInfo().getIconPath()));
        magImage = ImageIO.read(new File(playerFabrica.getMagInfo().getIconPath()));

        archerCost = playerFabrica.getArcherInfo().getCost();
        warriorCost = playerFabrica.getWarriorInfo().getCost();
        pristCost = playerFabrica.getPristInfo().getCost();
        magCost = playerFabrica.getMagInfo().getCost();
        nextUpdate = playerFabrica.nexUpdate();
    }

    @Override
    public KeyListener getKeyListener() {
        return keyListener;
    }

    @Override
    public MouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, wight, height);

        /* иконки героев*/
        g.drawImage(archerImage, BORDER, BORDER, null);
        g.drawImage(warriorImage, BORDER + (IMAGE_SIZE + BORDER), BORDER, null);
        g.drawImage(pristImage, BORDER + (IMAGE_SIZE + BORDER) * 2, BORDER, null);
        g.drawImage(magImage, BORDER + (IMAGE_SIZE + BORDER) * 3, BORDER, null);

        /* затеменение иконки, если не хвататет денег*/
        g.setColor(new Color(0, 0, 0, 100));
        if (money < archerCost) {
            g.fillRect(BORDER, BORDER, IMAGE_SIZE, IMAGE_SIZE);
        }
        if (money < warriorCost) {
            g.fillRect(BORDER + (IMAGE_SIZE + BORDER), BORDER, IMAGE_SIZE, IMAGE_SIZE);
        }
        if (money < pristCost) {
            g.fillRect(BORDER+(IMAGE_SIZE+BORDER)*2, BORDER, IMAGE_SIZE, IMAGE_SIZE);
        }
        if (money < magCost) {
            g.fillRect(BORDER+(IMAGE_SIZE+BORDER)*3, BORDER, IMAGE_SIZE, IMAGE_SIZE);
        }

        /*затемнение иконки перезарядки*/
        int dy = (int)(IMAGE_SIZE * ((double)BUY_PAUSE-buyTimer)/BUY_PAUSE);
        g.fillRect(BORDER, BORDER, IMAGE_SIZE, dy);

        g.fillRect(BORDER + (IMAGE_SIZE + BORDER), BORDER, IMAGE_SIZE, dy);

        g.fillRect(BORDER+(IMAGE_SIZE+BORDER)*2, BORDER, IMAGE_SIZE, dy);

        g.fillRect(BORDER+(IMAGE_SIZE+BORDER)*3, BORDER, IMAGE_SIZE, dy);

        g.setColor(Color.WHITE);
        g.drawString("q", BORDER, BORDER+9);
        g.drawString("w", BORDER + (IMAGE_SIZE + BORDER)+2, BORDER+9);
        g.drawString("e", BORDER+(IMAGE_SIZE+BORDER)*2 + 2, BORDER+9);
        g.drawString("r", BORDER+(IMAGE_SIZE+BORDER)*3 + 2, BORDER+9);




        /*цена персонажей*/
        int y = 10;//небольшая коррикртеровка отображения цены
        int x = 5; //небольшая коррикртеровка отображения цены
        setColor(g, archerCost, money);
        g.drawString(""+archerCost, x + BORDER, BORDER*2 + IMAGE_SIZE + y);
        setColor(g, warriorCost, money);
        g.drawString(""+warriorCost, x + BORDER+(IMAGE_SIZE+BORDER), BORDER*2 + IMAGE_SIZE + y);
        setColor(g, pristCost, money);
        g.drawString(""+pristCost, x + BORDER+(IMAGE_SIZE+BORDER) * 2, BORDER*2 + IMAGE_SIZE + y);
        setColor(g, magCost, money);
        g.drawString(""+magCost, x + BORDER+(IMAGE_SIZE+BORDER) * 3, BORDER*2 + IMAGE_SIZE + y);

        /*цена обновления, кол-во героев, кол-во денег*/
        int x2 = BORDER+(IMAGE_SIZE+BORDER) * 4;
        int y2 = BORDER;

        setColor(g, nextUpdate, money);
        if(nextUpdate!=-1) {
            g.drawString("UP(U): " + nextUpdate, x2, y2 + y);
        } else {
            g.drawString("MAX LVL", x2, y2 + y);
        }

        g.setColor(Color.WHITE);
        g.drawString(playerCount +"/"+ maxPlayerCount, x2, y2 + 2*y+8);

        g.drawImage(moneyImage, x2, 3*y+6, null);
        g.drawString(": "+money, x2 + 18, 3*y+20);

    }

    /**
     * Если не хвататет денег, то цвет меняем на более тёмный, если хвататет, оставляем белым
     * @param g там, где установим нужный цвет
     * @param cost стоимость
     * @param money кол-во денег
     */
    private void setColor(Graphics2D g, int cost, int money) {
        if(money>=cost){
            g.setColor(Color.WHITE);
        } else {
            g.setColor(impossibleByColor);
        }
    }

    @Override
    public void update(GameController gameController) {
        if(isLvlUp){
            isLvlUp = false;
            lvlUp();
        }
        try {
            tryBuyPlayer(gameController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Увеличение уровня базы, если хватает денег
     */
    private void lvlUp() {
        if(money>=nextUpdate){
            money-=nextUpdate;
        }
        try {
            initFabricaByLvl(playerFabrica.getLvl()+1);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public Type getType() {
        return board;
    }

    private void setTryBuyPlayerNumber(int i) {
        if(playerCount<maxPlayerCount) {
            tryBuyPlayerNumber = i;
        }
    }

    /**
     * Попытка купить игрока. Проверяются таймер покупки, и вообще была ли нажата клавиша покупки
     * @param gameController
     * @throws IOException если не удалюсь подрузить ресурсы объекта-игрока, который мы хотим купить
     */
    private void tryBuyPlayer(GameController gameController) throws IOException {

        PlayerCreator creator = null;

        if(buyTimer>=BUY_PAUSE) {
            if (tryBuyPlayerNumber == -1) {
                return;
            }
            int cost = 0;
            int i = tryBuyPlayerNumber;
            tryBuyPlayerNumber = -1;
            switch (i) {
                case 1:
                    cost = archerCost;
                    creator = playerFabrica::createArhcer;
                    break;
                case 2:
                    cost = warriorCost;
                    creator = playerFabrica::createWarrior;
                    break;
                case 3:
                    cost = pristCost;
                    creator = playerFabrica::createPrist;
                    break;
                case 4:
                    cost = magCost;
                    creator = playerFabrica::createMag;
                    break;
            }

            if(creator==null) { return; }

            if (cost <= money) {
                money -= cost;
                Player player = creator.create(new Point(0, windowInfo.getHeight() - 100), new Point(100, windowInfo.getHeight() - 100), windowInfo);
                playerCount++;
                buyTimer=0;
                player.setPlayerDestroyNotifer(this);
                gameController.addPlayer(player);
            }
        } else {
            if(buyTimer<BUY_PAUSE) {
                buyTimer++;
            }
            tryBuyPlayerNumber = -1;
        }
    }

    @Override
    public void objectIsDistroy(GameObject player) {
        playerCount--;
    }


    class GameBoardKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());
            if(e.getKeyCode()==KeyEvent.VK_Q){
                System.out.println(1);
                setTryBuyPlayerNumber(1);
            }
            if(e.getKeyCode()==KeyEvent.VK_W){
                System.out.println(2);
                setTryBuyPlayerNumber(2);
            }
            if(e.getKeyCode()==KeyEvent.VK_E){
                System.out.println(3);
                setTryBuyPlayerNumber(3);
            }
            if(e.getKeyCode()==KeyEvent.VK_R){
                System.out.println(4);
                setTryBuyPlayerNumber(4);
            }

            if(e.getKeyCode()==KeyEvent.VK_U) {
                tryLvlUp();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * Попытка установить флаг увеличения уровня базы в true (если хвататет денег).
     * Если флаг стоит в true, то на следующей итерации метода update(...) база увеличит уровень.
     */
    private void tryLvlUp() {
        if(money>=nextUpdate){
            isLvlUp = true;
        }
    }

    @FunctionalInterface
    interface PlayerCreator{
        Player create(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;
    }
}
