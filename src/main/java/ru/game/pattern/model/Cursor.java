package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.PatternGameMouseListener;
import ru.game.pattern.view.PatternGameGraphics2D;
import ru.game.pattern.view.Property;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created by Uskov Dmitry on 31.05.2016.
 */
public class Cursor extends GameObject {

    /**
     * true, если рамку выделения курсором необходимо отрисовывать и проводить какое-то обновление
     */
    volatile private boolean drawAndUpdate;

    /**
     * информация об окне
     */
    private WindowInfo windowInfo;


    //TODO заменить этот набор точек на объект класса Point
    /**
     * Координаты курсора при нажатии ЛКМ на поле
     */
    private int startX;

    private int startY;

    //TODO заменить этот набор точек на объект класса Point
    /**
     * Координаты курсора при движении курсора по экрану при зажатой ЛКМ
     */
    private int endX;

    private int endY;

    /**
     * Цвет рамки выделения курсором
     */
    private Color color = Color.YELLOW;

    /**
     * Обработчик событий
     */
    private CursorMouseListener mouseListener;

    /**
     * Обработчик событий
     */
    private CursorKeyListener keyListener;

    /**
     * Коллекция игровых объектов, которые будут проверяться на выделение курсором/бубут выделяться курсторм
     * и которые вообще, как-то будут взаимодейстовать с курсором
     * возможно название поля надо будет изменить
     */
    private List<PhysicalGameObject> selectingGameObjects;

    /**
     * Координата X левого верхнего угла прямоугольника выделения курсором,
     * независимо от того, было ли выделение произведено из нижнего правого угла или любого другоуго
     */
    private int leftX;

    /**
     * Координата Y левого верхнего угла прямоугольника выделения курсором,
     * независимо от того, было ли выделение произведено из нижнего правого угла или любого другоуго
     */
    private int upY;

    /**
     * Координата X правого нижнего угла прямоугольника выделения курсором,
     * независимо от того, было ли выделение произведено из нижнего правого угла или любого другоуго
     */
    private int rightX;

    /**
     * Координата Y правого нижнего угла прямоугольника выделения курсором,
     * независимо от того, было ли выделение произведено из нижнего правого угла или любого другоуго
     */
    private int downY;


    public Cursor(WindowInfo windowInfo, List<PhysicalGameObject> selectingGameObjects) {
        this.windowInfo = windowInfo;
        this.mouseListener = new CursorMouseListener();
        this.keyListener = new CursorKeyListener();
        this.selectingGameObjects = selectingGameObjects;
    }

    @Override
    public KeyListener getKeyListener() {
        return keyListener;
    }

    @Override
    public PatternGameMouseListener getMouseListener(){
        return mouseListener;
    }

    @Override
    public void draw(PatternGameGraphics2D g) {
        if(drawAndUpdate){
            g.setColor(color);
            //если выделять будем НЕ из левого верзнего угла, а из любого другого
            if(startX>endX){
                leftX = endX;
                rightX = startX;
            }else {
                leftX = startX;
                rightX = endX;
            }

            if(startY>endY){
                upY = endY;
                downY = startY;
            }else {
                upY = startY;
                downY = endY;
            }
            g.drawRect(leftX, upY, rightX - leftX, downY - upY);
        }
    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public void updateDuringPause(GameController gameController) {
        if(drawAndUpdate){
            Point location = MouseInfo.getPointerInfo().getLocation();
           // int x = (int) (location.getX() / Property.SCREEN_SIZE_MULTIPLIER);
           // int y = (int) (location.getY()/ Property.SCREEN_SIZE_MULTIPLIER);
            int x = (int) location.getX();
            int y = (int) location.getY();
            int frameX = windowInfo.getFrameLocation().getLocation().x;
            int frameY = windowInfo.getFrameLocation().getLocation().y;
            endX = x - frameX - 8;// - windowInfo.getWindowsBarHeight(); //TODO Не понимаю, почему именно с этими числами работает корректно
            endY = y - frameY - 30;// - windowInfo.getWindowBoard();
            endX = (int)(endX/Property.SCREEN_SIZE_MULTIPLIER);
            endY = (int)(endY/Property.SCREEN_SIZE_MULTIPLIER);
        }
    }

    @Override
    public Type getType() {
        return Type.other;
    }

    /**
     * Обработчик действий мышью для курсора
     */
    class CursorMouseListener extends PatternGameMouseListener{

        @Override
        public void patternGameMouseClicked(MouseEvent e) {

        }

        @Override
        public void patternGameMousePressed(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1) { //выделение объектов на экране (отрисовка рамки)
                drawAndUpdate = true;
                startX = e.getX();
                startY = e.getY();
                endX = e.getX();
                endY = e.getY();
            }
        }

        @Override
        public void patternGameMouseReleased(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1) { //выделение объектов на экране (сообщение объектам, что они выделены)
                drawAndUpdate = false;
                for (PhysicalGameObject o : selectingGameObjects) {
                    Point location = o.getLocation();
                    if (location != null) {
                        int x = location.x;
                        int y = location.y;
                        if (x >= leftX && x <= rightX && y >= upY && y <= downY) { //Только те, которые попали в выделенную область
                            o.setSelectedByCursor(true);
                        } else if(!e.isShiftDown()){ //остальные становяться не выделенными (если не нажата клавиша shift)
                            o.setSelectedByCursor(false);
                        }
                    }
                }
            }

        }

        @Override
        public void patternGameMouseEntered(MouseEvent e) {

        }

        @Override
        public void patternGameMouseExited(MouseEvent e) {
        }
    }

    /**
     * Обработчик нажатия клавиш для курсора
     */
    class CursorKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == 0 || e.getKeyCode() == 192 ){//буква ё
                for (PhysicalGameObject o : selectingGameObjects) {
                    o.setSelectedByCursor(true);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_S){ //буква s
                selectingGameObjects.stream()
                        .filter(o -> o.isSeletedByCursor())
                        .forEach(PhysicalGameObject::resetAction);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
