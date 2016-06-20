package ru.game.pattern.model.staticObjects;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.controller.Property.*;

/**
 * Created by Anton on 20.06.2016.
 */
public class Castle extends StaticPhysicalGameObject {
    private static final String IMAGE_PATH = Property.RESOURSES_PATH + "static/castle.png";
    private static final int TERRITORY_RADIUS = 60;
    private static final Point IMAGE_SIZE = new Point(138, 264);
    private int mHealthAddCounter = 1;
    private BufferedImage image;

    public Castle(Point location) throws IOException {
        super(location, TERRITORY_RADIUS, IMAGE_PATH, IMAGE_SIZE);
        helth = CASTLE_MAX_HELTH;
        additionalIamgeShift = -80;
        if(IMAGE_PATH!=null) {
            this.image = ImageIO.read(new File(IMAGE_PATH));
        }
    }

    @Override
    public void drawAfterAll(Graphics2D g) {
        super.draw(g);
        //отрисовка HP
        g.setColor(Color.black);
        g.fillRect(location.x - IMAGE_SIZE.x/2 - 5, location.y - IMAGE_SIZE.y/2 - 90, IMAGE_SIZE.x+10, 10);
        g.setColor(Color.RED);
        g.fillRect(location.x - IMAGE_SIZE.x/2 - 5+1, location.y - IMAGE_SIZE.y/2 - 90+1,
                (int)((IMAGE_SIZE.x+10)*(double)helth/ CASTLE_MAX_HELTH)-1, 8);
    }

    @Override
    public void update(GameController gameController) {
        super.update(gameController);
        //Восстановление HP
        if(mHealthAddCounter % CASTLE_UPDATE_HELTH_COUNTER == 0 && helth < CASTLE_MAX_HELTH) {
            helth += 1;
            mHealthAddCounter = 0;
        }
        mHealthAddCounter++;
    }
}
