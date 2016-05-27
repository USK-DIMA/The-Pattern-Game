package ru.game.pattern.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * На данном будет накладываться объект Graphics2D g.
 * На объекте Graphics2D g происходит отрисовка всех объектов. после чего, этот объект g отображается на GamePanel
 */
public class GamePanel extends JPanel {

    private int width;

    private int height;

    public GamePanel(int width, int height) {
        super();
        this.width=width;
        this.height=height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }
}
