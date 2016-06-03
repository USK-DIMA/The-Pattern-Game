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

    private String BACKGROUND_IMAGE_PATH = Property.RESOURSES_PATH + "plane_lite_80.jpg";


    public GameBackground(WindowInfo windowInfo) throws IOException {
        this.windowInfo = windowInfo;
        image = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
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

}
