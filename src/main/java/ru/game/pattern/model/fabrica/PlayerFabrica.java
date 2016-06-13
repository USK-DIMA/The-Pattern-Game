package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.Archer;
import ru.game.pattern.model.playes.Mag;
import ru.game.pattern.model.playes.Prist;
import ru.game.pattern.model.playes.Warrior;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public interface PlayerFabrica {

    /**
     * Возвращает цену обновления до след уровня.
     * @return Цена обновления до след уровня или -1, если обновиться невозможно (например максимальный уровень обновления уже достигнут)
     */
    int nexUpdate();

    int getLvl();

    Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    Prist createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    PlayerInfo getArcherInfo();

    PlayerInfo getWarriorInfo();

    PlayerInfo getPristInfo();

    PlayerInfo getMagInfo();

}
