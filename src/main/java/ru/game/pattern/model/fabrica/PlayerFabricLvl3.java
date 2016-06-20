package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.PlayerInfo;
import ru.game.pattern.model.WindowInfo;
import ru.game.pattern.model.playes.*;

import java.awt.*;
import java.io.IOException;

import static ru.game.pattern.controller.Property.PLYAER_FABRIC_LVL3_NEXT_UPDATE;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class PlayerFabricLvl3 implements PlayerFabric {

    private PlayerInfo archerInfo;

    private PlayerInfo warriorInfo;

    private PlayerInfo pristInfo;

    private PlayerInfo magInfo;

    public PlayerFabricLvl3() throws IOException {
        archerInfo = new PlayerInfo(ArcherLvl3.ICON_PATH, ArcherLvl3.COST, "archer");
        warriorInfo = new PlayerInfo(WarriorLvl3.ICON_PATH, WarriorLvl3.COST, "warrior");
        pristInfo = new PlayerInfo(PriestLvl3.ICON_PATH, PriestLvl3.COST, "prist");
        magInfo =  new PlayerInfo(MagLvl3.ICON_PATH, MagLvl3.COST, "mag");
    }

    @Override
    public int nexUpdate() {
        return PLYAER_FABRIC_LVL3_NEXT_UPDATE;
    }

    @Override
    public int getLvl() {
        return 3;
    }

    @Override
    public Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Archer archer = new ArcherLvl3(windowInfo);
        archer.setLocation(location);
        archer.setTargetLocation(targetLocation);
        return archer;
    }

    @Override
    public Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Warrior warrior = new WarriorLvl3(windowInfo);
        warrior.setLocation(location);
        warrior.setTargetLocation(targetLocation);
        return warrior;
    }

    @Override
    public Priest createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Priest priest = new PriestLvl3(windowInfo);
        priest.setLocation(location);
        priest.setTargetLocation(targetLocation);
        return priest;
    }

    @Override
    public Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Mag mag = new MagLvl3(windowInfo);
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
