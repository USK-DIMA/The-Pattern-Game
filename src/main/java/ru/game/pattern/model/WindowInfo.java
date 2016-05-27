package ru.game.pattern.model;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Класс, хронящий в себе всю необходимую информацию об окне.
 * объект этого класса передаётся из GameView другим объектам, которым нужна информация об окне (например об размере окна)
 */
public class WindowInfo {

    private int width;

    private int height;

    public WindowInfo(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
