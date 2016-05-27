package ru.game.pattern.model;

import java.awt.*;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Игровой объект, созданный для примера
 * Представлят из себя обычный круг
 */
public class TestGameObject extends GameObject {

    private int x = 20;

    private int y = 20;

    private int r;

    private Color color = Color.RED;


    @Override
    public void draw(Graphics2D g) {
            g.setColor(color);
            g.fillOval(x, y, x+r, y+r);
    }

    @Override
    public void update() {

    }
}
