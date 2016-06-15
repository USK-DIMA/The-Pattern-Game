package ru.game.pattern.controller;

import ru.game.pattern.model.Enemy;
import ru.game.pattern.model.Enemy.FreeTargetPoint;

/**
 * Created by Uskov Dmitry on 27.05.2016.
 */
public class Property {
    public static int UPDATE_PAUSE = 33;

    public static int GAME_SPEED = 1;

    public static String RESOURSES_PATH = "src/main/resources/";

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

        return p1;
    }
}
