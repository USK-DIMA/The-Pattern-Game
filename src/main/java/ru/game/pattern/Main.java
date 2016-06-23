package ru.game.pattern;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.GameControllerImpl;
import ru.game.pattern.view.GameView;

import java.io.IOException;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        GameView gameView = new GameView();
        GameController gameController = GameControllerImpl.getInstance(gameView.getWindowInfo());
        GameManager gameManager = new GameManager(gameController, gameView);
        System.out.println("Start Game");
        gameManager.startGame();
    }
}
