package ru.game.pattern.controller;

import ru.game.pattern.view.*;
import ru.game.pattern.view.Property;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Uskov Dmitry on 15.07.2016.
 */
public class PatternGameMouseListener implements MouseListener{

    public void patternGameMouseClicked(MouseEvent e) {

    }

    public void patternGameMousePressed(MouseEvent e) {

    }

    public void patternGameMouseReleased(MouseEvent e) {

    }

    public void patternGameMouseEntered(MouseEvent e) {

    }

    public void patternGameMouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        patternGameMouseClicked(convert(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        patternGameMousePressed(convert(e));
    }

    private MouseEvent convert(MouseEvent e) {
        return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(),
                (int)(e.getX()/ Property.SCREEN_SIZE_MULTIPLIER),
                (int)(e.getY()/ Property.SCREEN_SIZE_MULTIPLIER),
                e.getClickCount(), e.isPopupTrigger());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        patternGameMouseReleased(convert(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        patternGameMouseEntered(convert(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        patternGameMouseExited(convert(e));
    }
}
