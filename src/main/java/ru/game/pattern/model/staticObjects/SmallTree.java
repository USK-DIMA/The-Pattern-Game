package ru.game.pattern.model.staticObjects;

import ru.game.pattern.controller.Property;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 14.06.2016.
 */
public class SmallTree extends StaticPhysicalGameObject {


    private static final String IMAGE_PATH = Property.RESOURSES_PATH + "static/tree_small.png";

    private static final int TERRITORY_RADIUS = 10;

    private static final Point IMAGE_SIZE = new Point(32, 64);

    public SmallTree(Point location) throws IOException{
        super(location, TERRITORY_RADIUS, IMAGE_PATH, IMAGE_SIZE);
        printTerritoryRadius = true;
        additionalIamgeShift = -20;
    }
}
