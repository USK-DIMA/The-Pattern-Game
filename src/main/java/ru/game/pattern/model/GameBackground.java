package ru.game.pattern.model;

import java.awt.*;

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


    public GameBackground(WindowInfo windowInfo) {
        this.windowInfo = windowInfo;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, windowInfo.getWidth(),  windowInfo.getHeight());
    }

    @Override
    public void update() {

    }
}
