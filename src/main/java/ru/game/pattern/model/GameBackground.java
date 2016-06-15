package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Объект, отвечающий за фон игры.
 * Если мы захотим, чтобы изображение на фоне было динамическое, изменения вносить здесь
 */
public class GameBackground extends GameObject {

    private WindowInfo windowInfo;

    private BufferedImage image = null;

    private BufferedImage imageTree = null;

    private String BACKGROUND_IMAGE_PATH = Property.RESOURSES_PATH + "plane_lite_80.png";

    private String BACKGROUND_IMAGE_PATH_THREE = Property.RESOURSES_PATH + "plane_lite_81.png";

    private int black = 0;

    private boolean endGame = false;

    private int endGameTransparency = 0;


    public GameBackground(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        image = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        imageTree = ImageIO.read(new File(BACKGROUND_IMAGE_PATH_THREE));
    }

    @Override
    public void drawSpecialAfterAll(Graphics2D g) {
        g.drawImage(imageTree, 0, 0, null);
        g.setColor(Color.WHITE);
        if (Property.DEBUG_MODE) {
            g.drawRect(windowInfo.getBorderLeft(), windowInfo.getBorderTop(),
                         windowInfo.getWidth() - windowInfo.getBorderLeft() - windowInfo.getBorderRight(),
                        windowInfo.getHeight() - windowInfo.getBorderTop() - windowInfo.getBorderBottom());
        }

        if(black>0) {
            g.setColor(new Color(0, 0, 0, black));
            g.fillRect(0,0, 1280, 720);
            black-=5;
        }
        if(endGame){
            endGameTransparency+=4;
            if(endGameTransparency>255){
                endGameTransparency = 255;
            }
            g.setColor(new Color(0, 0, 0, endGameTransparency ));
            g.fillRect(0,0, 1280, 720);
            g.setColor(new Color(255, 255, 255, endGameTransparency));
            g.drawString("Game Over", 620, 360);
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
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public Type getType() {
        return Type.other;
    }

    public void setBlack(int i) {
        black = i;
    }

    public void endGame() {
        endGame = true;
    }
}
