package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.*;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public interface PlayerFabrica {
    Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    Prist createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

}
