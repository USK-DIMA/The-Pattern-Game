package ru.game.pattern.controller;

/**
 * Created by Uskov Dmitry on 27.05.2016.
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
        System.out.println("Status game: RUN has FALSE");
        run=false;
    }

}
