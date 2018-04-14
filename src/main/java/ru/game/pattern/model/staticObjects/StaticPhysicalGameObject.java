package ru.game.pattern.model.staticObjects;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.PatternGameMouseListener;
import ru.game.pattern.controller.Property;
import ru.game.pattern.view.PatternGameGraphics2D;
import ru.game.pattern.model.PhysicalGameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.game.pattern.model.GameObject.Type.staticObject;

/**
 * Created by Uskov Dmitry on 14.06.2016.
 */
public class StaticPhysicalGameObject extends PhysicalGameObject {

    private int territoryRadius;

    private BufferedImage image;

    private Point imageSize;

    protected boolean printTerritoryRadius = Property.DEBUG_MODE;

    protected int additionalIamgeShift = 0;


    public StaticPhysicalGameObject(Point location, int territoryRadius, String imagePath, Point imageSize) throws IOException {
        super(Integer.MAX_VALUE);
        this.location = location;
        this.territoryRadius = territoryRadius;
        if(imagePath!=null) {
            this.image = getResourseAsImage(imagePath);
        }
        this.imageSize = imageSize;
    }

    @Override
    protected void resetAction() {

    }

    @Override
    public int getTerritoryRadius() {
        return territoryRadius;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }

    @Override
    public PatternGameMouseListener getMouseListener() {
        return null;
    }

    @Override
    public void draw(PatternGameGraphics2D g) {
        if(image!=null && imageSize !=null) {
            g.setColor(Color.WHITE);
            g.drawImage(image, location.x - imageSize.x / 2, location.y - imageSize.y / 2 + additionalIamgeShift, null);
        }
        if(printTerritoryRadius){
            g.drawOval(location.x - territoryRadius, location.y - territoryRadius, 2 * territoryRadius, 2 * territoryRadius);
        }
    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public Type getType() {
        return staticObject;
    }
}
