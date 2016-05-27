package ru.game.pattern;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.view.GameView;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public class GameManager {

    private GameController gameController;

    private GameView gameView;

    public GameManager(GameController gameController, GameView gameView) {
        this.gameController = gameController;
        this.gameView = gameView;
    }

    public void startGame(){
        gameView.startGame(gameController);
    }

}
