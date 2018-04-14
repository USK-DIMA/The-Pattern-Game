package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.PatternGameMouseListener;
import ru.game.pattern.controller.Property;
import ru.game.pattern.view.PatternGameGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 *
 * Объект, отвечающий за фон игры.
 * Если мы захотим, чтобы изображение на фоне было динамическое, изменения вносить здесь
 */
public class GameBackground extends GameObject {

    private WindowInfo windowInfo;

    private BufferedImage image;

    private BufferedImage imageTree;

    private static final String BACKGROUND_IMAGE_PATH = "plane_lite_80.png";

    private static final String BACKGROUND_IMAGE_PATH_THREE = "plane_lite_81.png";

    private int black = 0;

    private boolean endGame = false;

    private boolean winGame = false;

    private int endGameTransparency = 0;


    public GameBackground(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        image = getResourseAsImage(BACKGROUND_IMAGE_PATH);
        imageTree = getResourseAsImage(BACKGROUND_IMAGE_PATH_THREE);
    }

    @Override
    public void drawAfterAll(PatternGameGraphics2D g) {
        g.drawImage(imageTree, 0, 0, null);
        g.setColor(Color.WHITE);
        if (Property.DEBUG_MODE) {
            g.drawRect(windowInfo.getBorderLeft(), windowInfo.getBorderTop(),
                         windowInfo.getDefaultWidth() - windowInfo.getBorderLeft() - windowInfo.getBorderRight(),
                        windowInfo.getDefaultHeight() - windowInfo.getBorderTop() - windowInfo.getBorderBottom());
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

        if(winGame){
            endGameTransparency+=4;
            if(endGameTransparency>255){
                endGameTransparency = 255;
            }
            g.setColor(new Color(0, 0, 0, endGameTransparency ));
            g.fillRect(0,0, 1280, 720);
            g.setColor(new Color(10, 255, 10, endGameTransparency));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
            g.drawString("Congratulation! You win!", 350, 360);
            g.setFont(new Font("default", Font.BOLD, 10));
        }
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }

    @Override
    public PatternGameMouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(PatternGameGraphics2D g) {
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

    public void winGame() {
        winGame = true;
    }
}
