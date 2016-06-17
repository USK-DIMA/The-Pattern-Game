package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.*;


import java.awt.*;
import java.io.IOException;

import static ru.game.pattern.controller.Property.PLYAER_FABRIC_LVL1_NEXT_UPDATE;

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
        pristInfo = new PlayerInfo(PriestLvl1.ICON_PATH, PriestLvl1.COST, "prist");
        magInfo =  new PlayerInfo(MagLvl1.ICON_PATH, MagLvl1.COST, "mag");
    }

    @Override
    public int nexUpdate() {
        return PLYAER_FABRIC_LVL1_NEXT_UPDATE;
    }

    @Override
    public int getLvl() {
        return 1;
    }

    @Override
    public Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Archer archer = new ArcherLvl1(windowInfo);
        archer.setLocation(location);
        archer.setTargetLocation(targetLocation);
        return archer;
    }

    @Override
    public Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Warrior warrior = new WarriorLvl1(windowInfo);
        warrior.setLocation(location);
        warrior.setTargetLocation(targetLocation);
        return warrior;
    }

    @Override
    public Priest createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Priest priest = new PriestLvl1(windowInfo);
        priest.setLocation(location);
        priest.setTargetLocation(targetLocation);
        return priest;
    }

    @Override
    public Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Mag mag = new MagLvl1(windowInfo);
        mag.setLocation(location);
        mag.setTargetLocation(targetLocation);
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
