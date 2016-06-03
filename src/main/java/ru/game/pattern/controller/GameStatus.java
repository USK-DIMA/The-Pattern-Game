package ru.game.pattern.controller;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */

/**
 * Объект, содержащий в себе информацию о состоянии игры.
 * Пока это только параметр, показывающий началась ли игра или уже закончилась.
 * Позже возможно добавить состояние "Пауза"
 */
public class GameStatus {

    volatile private boolean run;

    public GameStatus() {
        this.run = true;
    }

    public boolean isRun() {
        return run;
    }

    public void stopGame(){
        run=false;
    }

}
