package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.PlayerInfo;
import ru.game.pattern.model.WindowInfo;
import ru.game.pattern.model.playes.*;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class PlayerFabricaLvl3 implements  PlayerFabrica{

    private PlayerInfo archerInfo;

    private PlayerInfo warriorInfo;

    private PlayerInfo pristInfo;

    private PlayerInfo magInfo;

    public PlayerFabricaLvl3() throws IOException {
        archerInfo = new PlayerInfo(ArcherLvl3.ICON_PATH, ArcherLvl3.COST, "archer");
        warriorInfo = new PlayerInfo(WarriorLvl3.ICON_PATH, WarriorLvl3.COST, "warrior");
        pristInfo = new PlayerInfo(PristLvl3.ICON_PATH, PristLvl3.COST, "prist");
        magInfo =  new PlayerInfo(MagLvl3.ICON_PATH, MagLvl3.COST, "mag");
    }

    @Override
    public int nexUpdate() {
        return -1;
    }

    @Override
    public int getLvl() {
        return 3;
    }

    @Override
    public Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Archer archer = new ArcherLvl3(windowInfo);
        archer.setLocation(location);
        archer.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return archer;
    }

    @Override
    public Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Warrior warrior = new WarriorLvl3(windowInfo);
        warrior.setLocation(location);
        warrior.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return warrior;
    }

    @Override
    public Prist createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Prist prist = new PristLvl3(windowInfo);
        prist.setLocation(location);
        prist.setTargetLocation(targetLocation, false);
        //// TODO: 09.06.2016 выкл флажёк при выходе
        return prist;
    }

    @Override
    public Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException {
        Mag mag = new MagLvl3(windowInfo);
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
