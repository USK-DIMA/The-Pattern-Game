package ru.game.pattern.controller;

import ru.game.pattern.model.Enemy.FreeTargetPoint;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public class Property {
    public static int UPDATE_PAUSE = 33;

    public static String RESOURSES_PATH = "src/main/resources/";

    public static boolean DEBUG_MODE = true;

    public static int START_MONEY = 300;
    public static int MONEY_PER_KILL = 25;
    public static int MAX_PLAYER_COUNT = 5;
    public static int BUY_PLAYER_PAUSE = 50;
    public static int PLAYER_TERRITORY_RADIUS = 8;

    public static int ENEMY_COUNT = 8;

    public static int CASTLE_LOCATION_X = 570;
    public static int CASTLE_LOCATION_Y = 340;

    public static int DEFAULT_ATTACK_PAUSE = 8;
    public static int DEFAULT_ATTACK_RADIUS = 8;
    public static int DEFAULT_DAMAGE = 8;

    public static int CASTLE_MAX_HELTH = 1000;
    public static int CASTLE_UPDATE_HELTH_COUNTER = 20; //раз в сколько update будут восстанавливаться hp

    public static int PLYAER_FABRIC_LVL1_NEXT_UPDATE = 300;
    public static int PLYAER_FABRIC_LVL2_NEXT_UPDATE = 900;
    public static int PLYAER_FABRIC_LVL3_NEXT_UPDATE = -1;

    public static int FIREBALL_LVL1_SPEED = 30;
    public static int FIREBALL_LVL1_TERRITORY_RADIUS = 4;
    public static int FIREBALL_LVL1_MAX_DISTANSE = 400;
    public static int FIREBALL_LVL1_DAMAGE = 10;


    public static int ENEMY_ATTACK_RADIUS = 7;
    public static int ENEMY_OVERVIEW_RADIUS = 200;
    public static int ENEMY_SPEED = 3;
    public static int ENEMY_DAMAGE = 1;
    public static int ENEMY_DAMAGE_PAUSE = 2;
    public static int ENEMY_TERITORY_RADIUS = 8;

    public static int ARCHER_LVL1_COST = 90;
    public static int ARCHER_LVL1_MAX_HELTH = 100;
    public static int ARCHER_LVL1_ATTACK_PAUSE = 5;
    public static int ARCHER_LVL1_SPEED = 5;

    public static int ARCHER_LVL2_COST = 90;
    public static int ARCHER_LVL2_MAX_HELTH = 100;
    public static int ARCHER_LVL2_ATTACK_PAUSE = 30;
    public static int ARCHER_LVL2_SPEED = 4;

    public static int ARCHER_LVL3_COST = 90;
    public static int ARCHER_LVL3_MAX_HELTH = 100;
    public static int ARCHER_LVL3_ATTACK_PAUSE = 30;
    public static int ARCHER_LVL3_SPEED = 4;

    public static int WARRIOR_LVL1_COST = 100;
    public static int WARRIOR_LVL1_MAX_HELTH = 100;
    public static int WARRIOR_LVL1_MAX_DAMAGE = 15;
    public static int WARRIOR_LVL1_ATTACK_PAUSE = 30;
    public static int WARRIOR_LVL1_SPEED = 4;
    public static int WARRIOR_LVL1_ATTACK_RADIUS = 4;

    public static int WARRIOR_LVL2_COST = 100;
    public static int WARRIOR_LVL2_MAX_HELTH = 100;
    public static int WARRIOR_LVL2_MAX_DAMAGE = 15;
    public static int WARRIOR_LVL2_ATTACK_PAUSE = 30;
    public static int WARRIOR_LVL2_SPEED = 4;
    public static int WARRIOR_LVL2_ATTACK_RADIUS = 4;

    public static int WARRIOR_LVL3_COST = 100;
    public static int WARRIOR_LVL3_MAX_HELTH = 100;
    public static int WARRIOR_LVL3_MAX_DAMAGE = 15;
    public static int WARRIOR_LVL3_ATTACK_PAUSE = 30;
    public static int WARRIOR_LVL3_SPEED = 4;
    public static int WARRIOR_LVL3_ATTACK_RADIUS = 4;

    public static int MAG_LVL1_COST = 100;
    public static int MAG_LVL1_MAX_HELTH = 100;
    public static int MAG_LVL1_MAX_MANA = 150;
    public static int MAG_LVL1_MANA_LOSSES = 1;
    public static int MAG_LVL1_MANA_ADDING = 1;
    public static int MAG_LVL1_SPEED = 6;
    public static int MAG_LVL1_FREEZE_RADIUS = 200;
    public static int MAG_LVL1_INVISE_PAUSE = 900;
    public static double MAG_LVL1_FREEZE = 0.5;

    public static int MAG_LVL2_COST = 100;
    public static int MAG_LVL2_MAX_HELTH = 100;
    public static int MAG_LVL2_MAX_MANA = 150;
    public static int MAG_LVL2_MANA_LOSSES = 1;
    public static int MAG_LVL2_MANA_ADDING = 1;
    public static int MAG_LVL2_SPEED = 6;
    public static int MAG_LVL2_FREEZE_RADIUS = 120;
    public static int MAG_LVL2_INVISE_PAUSE = 180;
    public static double MAG_LVL2_FREEZE = 0.4;

    public static int MAG_LVL3_COST = 100;
    public static int MAG_LVL3_MAX_HELTH = 100;
    public static int MAG_LVL3_MAX_MANA = 150;
    public static int MAG_LVL3_MANA_LOSSES = 1;
    public static int MAG_LVL3_MANA_ADDING = 1;
    public static int MAG_LVL3_SPEED = 6;
    public static int MAG_LVL3_FREEZE_RADIUS = 90;
    public static int MAG_LVL3_INVISE_PAUSE = 180;
    public static double MAG_LVL3_FREEZE = 0.0;

    public static int PRIST_LVL1_COST = 100;
    public static int PRIST_LVL1_MAX_HELTH = 100;
    public static int PRIST_LVL1_MAX_MANA = 150;
    public static int PRIST_LVL1_MANA_LOSSES = 1;
    public static int PRIST_LVL1_MANA_ADDING = 1;
    public static int PRIST_LVL1_SPEED = 6;
    public static int PRIST_LVL1_HILL_RADIUS = 90;
    public static int PRIST_LVL1_HELTH_HILL = 5;
    public static int PRIST_LVL1_HILL_PAUSE = 15;

    public static int PRIST_LVL2_COST = 100;
    public static int PRIST_LVL2_MAX_HELTH = 100;
    public static int PRIST_LVL2_MAX_MANA = 150;
    public static int PRIST_LVL2_MANA_LOSSES = 1;
    public static int PRIST_LVL2_MANA_ADDING = 1;
    public static int PRIST_LVL2_SPEED = 6;
    public static int PRIST_LVL2_HILL_RADIUS = 90;
    public static int PRIST_LVL2_HELTH_HILL = 5;
    public static int PRIST_LVL2_HILL_PAUSE = 15;

    public static int PRIST_LVL3_COST = 100;
    public static int PRIST_LVL3_MAX_HELTH = 100;
    public static int PRIST_LVL3_MAX_MANA = 150;
    public static int PRIST_LVL3_MANA_LOSSES = 1;
    public static int PRIST_LVL3_MANA_ADDING = 1;
    public static int PRIST_LVL3_SPEED = 6;
    public static int PRIST_LVL3_HILL_RADIUS = 90;
    public static int PRIST_LVL3_HELTH_HILL = 5;
    public static int PRIST_LVL3_HILL_PAUSE = 15;

    public static FreeTargetPoint initFreeTargetPointForEnemy() {
        FreeTargetPoint p1 = new FreeTargetPoint();
        FreeTargetPoint p2 = new FreeTargetPoint();
        FreeTargetPoint p3 = new FreeTargetPoint();
        FreeTargetPoint p4 = new FreeTargetPoint();
        FreeTargetPoint p5 = new FreeTargetPoint();
        FreeTargetPoint p6 = new FreeTargetPoint();
        FreeTargetPoint p7 = new FreeTargetPoint();
        FreeTargetPoint p8 = new FreeTargetPoint();
        FreeTargetPoint p9 = new FreeTargetPoint();
        FreeTargetPoint p10 = new FreeTargetPoint();
        FreeTargetPoint p11 = new FreeTargetPoint();
        FreeTargetPoint p12 = new FreeTargetPoint();
        FreeTargetPoint p13 = new FreeTargetPoint();
        FreeTargetPoint p14 = new FreeTargetPoint();
        FreeTargetPoint p15 = new FreeTargetPoint();
        FreeTargetPoint p16 = new FreeTargetPoint();
        FreeTargetPoint p17 = new FreeTargetPoint();
        p1.setPoint(1091, 142);
        p2.setPoint(526, 170);
        p3.setPoint(851, 299);
        p4.setPoint(1103, 410);
        p5.setPoint(624, 337);
        p6.setPoint(741, 579);
        p7.setPoint(1006, 553);
        p8.setPoint(555, 674);
        p9.setPoint(442, 534);
        p10.setPoint(327, 632);
        p11.setPoint(210, 521);
        p12.setPoint(89, 603);
        p13.setPoint(105, 309);
        p14.setPoint(350, 318);
        p15.setPoint(193, 93);
        p16.setPoint(526, 47);
        p17.setPoint(735, 133);

        p1.addNextPint(p2);
        p1.addNextPint(p3);
        p1.addNextPint(p4);


        p2.addNextPint(p16);
        p2.addNextPint(p17);
        p2.addNextPint(p15);
        p2.addNextPint(p1);

        p3.addNextPint(p1);
        p3.addNextPint(p5);
        p3.addNextPint(p7);

        p4.addNextPint(p1);
        p4.addNextPint(p7);

        p5.addNextPint(p3);
        p5.addNextPint(p6);
        p5.addNextPint(p14);
        p5.addNextPint(p9);

        p6.addNextPint(p5);
        p6.addNextPint(p7);
        p6.addNextPint(p8);
        p6.addNextPint(p9);

        p7.addNextPint(p6);
        p7.addNextPint(p3);
        p7.addNextPint(p4);

        p8.addNextPint(p10);
        p8.addNextPint(p6);

        p9.addNextPint(p10);
        p9.addNextPint(p6);
        p9.addNextPint(p5);

        p10.addNextPint(p9);
        p10.addNextPint(p8);
        p10.addNextPint(p11);

        p11.addNextPint(p10);
        p11.addNextPint(p12);
        p11.addNextPint(p13);
        p11.addNextPint(p14);

        p12.addNextPint(p11);
        p12.addNextPint(p13);

        p13.addNextPint(p11);
        p13.addNextPint(p12);
        p13.addNextPint(p14);
        p13.addNextPint(p15);

        p14.addNextPint(p11);
        p14.addNextPint(p12);
        p14.addNextPint(p2);
        p14.addNextPint(p5);

        p15.addNextPint(p13);
        p15.addNextPint(p2);

        p16.addNextPint(p2);
        p16.addNextPint(p17);

        p17.addNextPint(p16);
        p17.addNextPint(p2);

        FreeTargetPoint.setP9(p9);
        return p1;
    }
}
