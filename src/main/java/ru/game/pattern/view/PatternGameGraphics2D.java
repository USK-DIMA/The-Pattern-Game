package ru.game.pattern.view;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Uskov Dmitry on 06.07.2016.
 */
public class PatternGameGraphics2D {

    Graphics2D graphics2d;

    private double mult = Property.SCREEN_SIZE_MULTIPLIER;

    public PatternGameGraphics2D(Graphics2D graphics2d) {
        this.graphics2d = graphics2d;
    }

    public Graphics2D getGraphics2d() {
        return graphics2d;
    }

    public void setColor(Color color) {
        graphics2d.setColor(color);
    }

    public void fillRect(int x, int y, int width, int height) {
        graphics2d.fillRect((int)(x*mult), (int)(y*mult), (int)(width*mult), (int)(height*mult));
    }

    public void drawString(String message, int x, int y) {
        graphics2d.drawString(message, (int)(x*mult), (int)(y*mult));
    }

    public void drawImage(BufferedImage bufferedImage, int x, int y, int width, int height, Object o) {
        graphics2d.drawImage(bufferedImage, (int)(x*mult), (int)(y*mult), (int)(width*mult), (int)(height*mult), null);
    }

    public void setFont(Font font) {
        Font realFont = new Font(font.getName(),font.getStyle(), (int)(font.getSize()*mult));
        graphics2d.setFont(realFont);
    }

    public void drawImage(BufferedImage bufferedImage, int x, int y, Object o) {
        try {
            drawImage(bufferedImage, x, y, bufferedImage.getWidth(), bufferedImage.getHeight(), o);
        }
        catch (NullPointerException e){
            System.err.println("NullPointerException: bufferedImage == null");
        }
    }

    public void drawOval(int x, int y, int x2, int y2) {
        graphics2d.drawOval((int)(x*mult), (int)(y*mult), (int)(x2*mult), (int)(y2*mult));
    }

    public void drawRect(int borderLeft, int borderTop, int i, int i1) {
        graphics2d.drawRect((int)(borderLeft*mult), (int)(borderTop*mult), (int)(i*mult), (int)(i1*mult));
    }

    public void drawLine(int x, int y, int x1, int y1) {
        graphics2d.drawLine((int)(x*mult), (int)(y*mult), (int)(x1*mult), (int)(y1*mult));
    }

    public void fillOval(int i, int i1, int i2, int i3) {
        graphics2d.fillOval((int)(i*mult), (int)(i1*mult), (int)(i2*mult), (int)(i3*mult));
    }

    public void setStroke(BasicStroke stroke) {
        graphics2d.setStroke(stroke);
    }

    public void setRenderingHint(RenderingHints.Key keyAntialiasing, Object valueAntialiasOn) {
        graphics2d.setRenderingHint(keyAntialiasing, valueAntialiasOn);
    }

    public void setComposite(AlphaComposite composite) {
        graphics2d.setComposite(composite);
    }
}
