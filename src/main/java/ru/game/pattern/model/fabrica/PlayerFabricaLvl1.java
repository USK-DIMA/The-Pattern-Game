package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.*;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class PlayerFabricaLvl1 implements  PlayerFabrica{
    @Override
    public Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Archer archer = new Archer(windowInfo);
        archer.setLocation(location);
        archer.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return archer;
    }

    @Override
    public Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Warrior warrior = new Warrior(windowInfo);
        warrior.setLocation(location);
        warrior.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return warrior;
    }

    @Override
    public Prist createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Prist prist = new Prist(windowInfo);
        prist.setLocation(location);
        prist.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return prist;
    }

    @Override
    public Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Mag mag = new Mag(windowInfo);
        mag.setLocation(location);
        mag.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return mag;
    }
}
