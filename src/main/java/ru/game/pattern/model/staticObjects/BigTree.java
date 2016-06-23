package ru.game.pattern.model.staticObjects;

import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 14.06.2016.
 */
public class BigTree extends StaticPhysicalGameObject {


    private static final String IMAGE_PATH = Property.RESOURSES_PATH + "static/tree_big.png";

    private static final String LEAVES_IMAGE_PATH = Property.RESOURSES_PATH + "static/tree_big_leaves.png";

    private static final int TERRITORY_RADIUS = 18;

    private static final Point IMAGE_SIZE = new Point(64, 96);

    private BufferedImage leavesImage;

    public BigTree(Point location) throws IOException{
        super(location, TERRITORY_RADIUS, IMAGE_PATH, IMAGE_SIZE);
        leavesImage = ImageIO.read(new File(LEAVES_IMAGE_PATH));
        additionalIamgeShift = -24;
    }


    @Override
    public void drawAfterAll(Graphics2D g) {
        g.drawImage(leavesImage, location.x - IMAGE_SIZE.x / 2, location.y - IMAGE_SIZE.y / 2 + additionalIamgeShift, null);
    }
}
