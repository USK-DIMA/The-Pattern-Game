package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.PlayerInfo;
import ru.game.pattern.model.WindowInfo;
import ru.game.pattern.model.playes.*;

import java.awt.*;
import java.io.IOException;

import static ru.game.pattern.controller.Property.PLYAER_FABRIC_LVL2_NEXT_UPDATE;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class PlayerFabricaLvl2 implements  PlayerFabrica{

    private PlayerInfo archerInfo;

    private PlayerInfo warriorInfo;

    private PlayerInfo pristInfo;

    private PlayerInfo magInfo;

    public PlayerFabricaLvl2() throws IOException {
        archerInfo = new PlayerInfo(ArcherLvl2.ICON_PATH, ArcherLvl2.COST, "archer");
        warriorInfo = new PlayerInfo(WarriorLvl2.ICON_PATH, WarriorLvl2.COST, "warrior");
        pristInfo = new PlayerInfo(PriestLvl2.ICON_PATH, PriestLvl2.COST, "prist");
        magInfo =  new PlayerInfo(MagLvl2.ICON_PATH, MagLvl2.COST, "mag");
    }

    @Override
    public int nexUpdate() {
        return PLYAER_FABRIC_LVL2_NEXT_UPDATE;
    }

    @Override
    public int getLvl() {
        return 2;
    }

    @Override
    public Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Archer archer = new ArcherLvl2(windowInfo);
        archer.setLocation(location);
        archer.setTargetLocation(targetLocation);
        return archer;
    }

    @Override
    public Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Warrior warrior = new WarriorLvl2(windowInfo);
        warrior.setLocation(location);
        warrior.setTargetLocation(targetLocation);
        return warrior;
    }

    @Override
    public Priest createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Priest priest = new PriestLvl2(windowInfo);
        priest.setLocation(location);
        priest.setTargetLocation(targetLocation);
        return priest;
    }

    @Override
    public Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Mag mag = new MagLvl2(windowInfo);
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
