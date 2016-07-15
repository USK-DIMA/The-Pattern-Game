package ru.game.pattern.model.staticObjects;

import ru.game.pattern.controller.Property;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 14.06.2016.
 */
public class Stone extends StaticPhysicalGameObject {


    private static final String IMAGE_PATH = Property.RESOURCES_PATH + "static/stone.png";

    private static final int TERRITORY_RADIUS = 20;

    private static final Point IMAGE_SIZE = new Point(39, 48);

    public Stone(Point location) throws IOException{
        super(location, TERRITORY_RADIUS, IMAGE_PATH, IMAGE_SIZE);
    }
}
