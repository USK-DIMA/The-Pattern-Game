package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;
import ru.game.pattern.model.fabrica.PlayerFabrica;
import ru.game.pattern.model.fabrica.PlayerFabricaLvl1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.model.GameObject.Type.board;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class GameBoard extends GameObject {

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

    private int money = 80;

    private int kill = 0;

    private int dead = 0;

    Color impossibleByColor = new Color(100, 100, 100);

    public GameBoard(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        moneyImage = ImageIO.read(new File(Property.RESOURSES_PATH+"money.png"));
        playerFabrica = createFabricaByLvl(1);
        wight = 4 * (IMAGE_SIZE + BORDER) + BORDER + INFO_WIDTH;
        height = IMAGE_SIZE + 2 * BORDER + COST_HEIGHT;
    }

    private PlayerFabrica createFabricaByLvl(int i) throws IOException {
        switch (i){
            case 1:
                PlayerFabrica fabrica = new PlayerFabricaLvl1();
                archerImage = ImageIO.read(new File(fabrica.getArcherInfo().getIconPath()));
                warriorImage = ImageIO.read(new File(fabrica.getWarriorInfo().getIconPath()));
                pristImage = ImageIO.read(new File(fabrica.getPristInfo().getIconPath()));
                magImage = ImageIO.read(new File(fabrica.getMagInfo().getIconPath()));

                archerCost = fabrica.getArcherInfo().getCost();
                warriorCost = fabrica.getWarriorInfo().getCost();
                pristCost = fabrica.getPristInfo().getCost();
                magCost = fabrica.getMagInfo().getCost();
                nextUpdate = fabrica.nexUpdate();
                return fabrica;
            default: return null;
        }
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
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
            g.fillRect(BORDER, BORDER, 36, 36);
        }
        if (money < warriorCost) {
            g.fillRect(BORDER + (IMAGE_SIZE + BORDER), BORDER, 36, 36);
        }
        if (money < pristCost) {
            g.fillRect(BORDER+(IMAGE_SIZE+BORDER)*2, BORDER, 36, 36);
        }
        if (money < magCost) {
            g.fillRect(BORDER+(IMAGE_SIZE+BORDER)*3, BORDER, 36, 36);
        }


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

        /*цена обновления, убийства/сметри, кол-во денег*/
        int x2 = BORDER+(IMAGE_SIZE+BORDER) * 4;
        int y2 = BORDER;

        setColor(g, nextUpdate, money);

        g.drawString("UP(U): "+nextUpdate, x2, y2 + y);

        g.setColor(Color.WHITE);
        g.drawString(kill+"/"+dead, x2, y2 + 2*y+8);

        g.drawImage(moneyImage, x2, 3*y+6, null);
        g.drawString(": "+money, x2 + 18, 3*y+20);

    }

    /**
     * Если не хвататет денег, то цвет меняем на более тёмный, если хвататет, оставляем белым
     * @param g
     * @param cost
     * @param money
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

    }

    @Override
    public Type getType() {
        return board;
    }
}
