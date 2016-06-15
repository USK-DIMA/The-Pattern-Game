package ru.game.pattern.model.staticObjects;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 14.06.2016.
 */
public class InvisibleStaticObject extends StaticPhysicalGameObject {

    public InvisibleStaticObject(Point location, int territoryRadius) throws IOException {
        super(location, territoryRadius, null, null);
    }

}
