package ru.game.pattern.model.fabrica;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.*;


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
        archerInfo = new PlayerInfo(ArcherLvl1.ICON_PATH, ArcherLvl1.COST, "archer");
        warriorInfo = new PlayerInfo(WarriorLvl1.ICON_PATH, WarriorLvl1.COST, "warrior");
        pristInfo = new PlayerInfo(PristLvl1.ICON_PATH, PristLvl1.COST, "prist");
        magInfo =  new PlayerInfo(MagLvl1.ICON_PATH, MagLvl1.COST, "mag");
    }

    @Override
    public int nexUpdate() {
        return 300;
    }

    @Override
    public int getLvl() {
        return 1;
    }

    @Override
    public Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Archer archer = new ArcherLvl1(windowInfo);
        archer.setLocation(location);
        archer.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return archer;
    }

    @Override
    public Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Warrior warrior = new WarriorLvl1(windowInfo);
        warrior.setLocation(location);
        warrior.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return warrior;
    }

    @Override
    public Prist createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Prist prist = new PristLvl1(windowInfo);
        prist.setLocation(location);
        prist.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return prist;
    }

    @Override
    public Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Mag mag = new MagLvl1(windowInfo);
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
