package ru.game.pattern;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.GameControllerImpl;
import ru.game.pattern.view.GameView;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameControllerImpl();
        GameView gameView = new GameView();
        GameManager gameManager = new GameManager(gameController, gameView);
        System.out.println("Start Game");
        gameManager.startGame();
    }
}
