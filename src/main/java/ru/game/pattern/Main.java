package ru.game.pattern;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.GameControllerImpl;
import ru.game.pattern.view.GameView;
import ru.game.pattern.view.Property;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        double w = sSize.getWidth();
        double h = sSize.getHeight();
        double ww = w/ Property.DEFAULT_WINDOW_WIDTH;
        double hh = h/Property.DEFAULT_WINDOW_HEIGHT;
        Property.SCREEN_SIZE_MULTIPLIER =  Math.min(ww, hh)*0.9;
        System.out.println(w+"*"+h);
        System.out.println(Property.SCREEN_SIZE_MULTIPLIER);


        GameView gameView = new GameView();
        GameController gameController = new GameControllerImpl(gameView.getWindowInfo());
        GameManager gameManager = new GameManager(gameController, gameView);
        System.out.println("Start Game");
        gameManager.startGame();
    }
}
