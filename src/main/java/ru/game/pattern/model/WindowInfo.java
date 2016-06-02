package ru.game.pattern.model;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Класс, хронящий в себе всю необходимую информацию об окне.
 * объект этого класса передаётся из GameView другим объектам, которым нужна информация об окне (например об размере окна)
 */
public class WindowInfo {

    private int width;

    private int height;

    private JFrame frame;

    private int windowBoard;

    private int windowsBarHeight;

    public WindowInfo(int width, int height, JFrame frame) {
        this.width = width;
        this.height = height;
        this.frame = frame;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getFrameLocation() {
        return frame.getLocation();
    }

    public void setWindowBoard(int windowBoard) {
        this.windowBoard = windowBoard;
    }

    public void setWindowsBarHeight(int windowsBarHeight) {
        this.windowsBarHeight = windowsBarHeight;
    }

    public int getWindowBoard() {
        return windowBoard;
    }

    public int getWindowsBarHeight() {
        return windowsBarHeight;
    }
}
