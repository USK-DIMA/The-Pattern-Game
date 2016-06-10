package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.model.fabrica.PlayerFabrica;
import ru.game.pattern.model.fabrica.PlayerFabricaLvl1;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import static ru.game.pattern.model.GameObject.Type.board;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class GameBoard extends GameObject {

    private static final int BORDER = 2;

    private static final int IMAGE_SIZE = 36;

    private WindowInfo windowInfo;

    private PlayerFabrica playerFabrica;

    public GameBoard(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        playerFabrica = createFabricaByLvl(1);
    }

    private PlayerFabrica createFabricaByLvl(int i) throws IOException {
        switch (i){
            case 1: return new PlayerFabricaLvl1();
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
        int wight;
        int height;
        wight = 4 * (IMAGE_SIZE + BORDER) + BORDER;
        height = IMAGE_SIZE + 2 * BORDER;
        g.setColor(Color.black);
        g.fillRect(0, 0, wight, height);
        g.drawImage(playerFabrica.getArcherImage(), BORDER, BORDER, null);
        g.drawImage(playerFabrica.getWarrirImage(), BORDER+(IMAGE_SIZE+BORDER), BORDER, null);
        g.drawImage(playerFabrica.getPristImage(), BORDER+(IMAGE_SIZE+BORDER)*2, BORDER, null);
        g.drawImage(playerFabrica.getMagImage(), BORDER+(IMAGE_SIZE+BORDER)*3, BORDER, null);
    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public Type getType() {
        return board;
    }
}
