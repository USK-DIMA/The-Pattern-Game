package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;

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
public class GameBackground extends GameObject{

    public static final Color color = Color.BLUE;

    private WindowInfo windowInfo;

    private BufferedImage image = null;


    public GameBackground(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        image = ImageIO.read(new File("src/main/resources/plane_lite_80.jpg"));
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
        //g.setColor(color);
        //g.fillRect(0, 0, windowInfo.getWidth(),  windowInfo.getHeight());
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public Type getType() {
        return Type.other;
    }

}
