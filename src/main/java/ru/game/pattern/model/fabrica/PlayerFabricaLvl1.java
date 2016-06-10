package ru.game.pattern.model.fabrica;

import ru.game.pattern.controller.Property;
import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.Archer;
import ru.game.pattern.model.playes.Mag;
import ru.game.pattern.model.playes.Prist;
import ru.game.pattern.model.playes.Warrior;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public class PlayerFabricaLvl1 implements  PlayerFabrica{

    BufferedImage archerIcon;

    BufferedImage warriorIcon;

    BufferedImage magIcon;

    BufferedImage pristIcon;

    public PlayerFabricaLvl1() throws IOException {
        archerIcon = ImageIO.read(new File(Property.RESOURSES_PATH + "archer_icon1.jpg"));
        warriorIcon = ImageIO.read(new File(Property.RESOURSES_PATH + "warrior_icon1.jpg"));
        magIcon = ImageIO.read(new File(Property.RESOURSES_PATH + "mag_icon1.jpg"));
        pristIcon = ImageIO.read(new File(Property.RESOURSES_PATH + "prist_icon1.jpg"));
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
    public BufferedImage getArcherImage() {
        return archerIcon;
    }

    @Override
    public BufferedImage getWarrirImage() {
        return warriorIcon;
    }

    @Override
    public BufferedImage getPristImage() {
        return pristIcon;
    }

    @Override
    public BufferedImage getMagImage() {
        return magIcon;
    }
}
