package ru.game.pattern.view;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */


/**
 * Здесь храняться все настроки GameView.
 * У GameController тоже есть свои Property
 */
public class Property {

    /**
     * Изначально разработка игры веласть для окна 1280*720 без возможности изменить его,
     * поэтому значение этих величин имеет ключевое значение во многих местах
     */
    public static final int DEFAULT_WINDOW_WIDTH = 1280;

    public static final int DEFAULT_WINDOW_HEIGHT = 720;

    /**
     * Множитель размера окна игры:
     * WINDOW_WIDTH = DEFAULT_WINDOW_WIDTH * SCREEN_SIZE_MULTIPLIER
     * WINDOW_HEIGHT = DEFAULT_WINDOW_HEIGHT * SCREEN_SIZE_MULTIPLIER
     */
    public static double SCREEN_SIZE_MULTIPLIER = 0.9;

    /**
     * При движении курсора невозможно узнать его позицию относительно игрового окна, можно узнать
     * положение курсора только относительно всего экрана.Поэтому для корректного отображения анимации выделения
     * необходимо знать даже такие параметры как толщина рамок окна
     */
    public static final int WINDOW_BAR_HEIGHT = 38;

    public static final int WINDOW_BOARD = 15;

    /**
     * Кол-во кадров в секунду.
     * В controller.Property есть аналогичный параметр, но который служит
     * для частоты обновления состояния игровых объектов
     */
    public static final int FPS = 30;

}
