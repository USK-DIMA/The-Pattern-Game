package ru.game.pattern.model.fabrica;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.Archer;
import ru.game.pattern.model.playes.Mag;
import ru.game.pattern.model.playes.Prist;
import ru.game.pattern.model.playes.Warrior;


import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class PlayerFabricaLvl1 implements  PlayerFabrica{

    private PlayerInfo archerInfo;

    private PlayerInfo warriorInfo;

    private PlayerInfo pristInfo;

    private PlayerInfo magInfo;

    public PlayerFabricaLvl1() throws IOException {
        archerInfo = new PlayerInfo(Archer.ICON_PATH, Archer.COST, "archer");
        warriorInfo = new PlayerInfo(Warrior.ICON_PATH, Warrior.COST, "warrior");
        pristInfo = new PlayerInfo(Prist.ICON_PATH, Prist.COST, "prist");
        magInfo =  new PlayerInfo(Mag.ICON_PATH, Mag.COST, "mag");
    }

    @Override
    public int nexUpdate() {
        return 300;
    }

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

    @Override
    public PlayerInfo getArcherInfo() {
        return archerInfo;
    }

    @Override
    public PlayerInfo getWarriorInfo() {
        return warriorInfo;
    }

    @Override
    public PlayerInfo getPristInfo() {
        return pristInfo;
    }

    @Override
    public PlayerInfo getMagInfo() {
        return magInfo;
    }

}
