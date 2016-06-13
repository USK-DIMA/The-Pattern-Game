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
        this.gameController.setObjectNotifer(gameView.getGamePanel()); //устанавливаем объкт, который будет оповещать о всех нажатиях
    }

    public void startGame(){
        gameView.startGame(gameController);
    }

}
