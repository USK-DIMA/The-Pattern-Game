package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;

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

    volatile private boolean drawAndUpdate;

    private WindowInfo windowInfo;


    //TODO заменить этот набор точек на два объекта класса Point
    /**
     * Координаты курсора при нажатии ЛКМ на поле
     */
    private int startX;

    private int startY;

    /**
     * Координаты курсора при движении курсора по экрану при зажатой ЛКМ
     */
    private int endX;

    private int endY;

    private Color color = Color.YELLOW;

    private CursorMouseListener mouseListener;

    private CursorKeyListener keyListener;

    private List<PhysicalGameObject> selectingGameObjects;

    private int leftX;

    private int upY;

    private int rightX;

    private int downY;

    /**
     * нажата ли клавиша shift
     */
    private boolean shiftKey;

    public Cursor(WindowInfo windowInfo, List<PhysicalGameObject> selectingGameObjects) {
        this.windowInfo = windowInfo;
        this.mouseListener = new CursorMouseListener();
        this.keyListener = new CursorKeyListener();
        this.selectingGameObjects = selectingGameObjects;
        shiftKey = false;
    }

    @Override
    public KeyListener getKeyListener() {
        return keyListener;
    }

    @Override
    public MouseListener getMouseListener(){
        return mouseListener;
    }

    @Override
    public void draw(Graphics2D g) {
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
        if(drawAndUpdate){
            Point location = MouseInfo.getPointerInfo().getLocation();
            int x = (int) location.getX();
            int y = (int) location.getY();
            int frameX = windowInfo.getFrameLocation().getLocation().x;
            int frameY = windowInfo.getFrameLocation().getLocation().y;
            endX = x - frameX - 8;// - windowInfo.getWindowsBarHeight(); //TODO Не понимаю, почему именно с этими числами работает корректно
            endY = y - frameY - 30;// - windowInfo.getWindowBoard();
        }
    }

    @Override
    public Type getType() {
        return Type.other;
    }

    class CursorMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("mousePressed");
            if(e.getButton()==MouseEvent.BUTTON1) {
                drawAndUpdate = true;
                startX = e.getX();
                startY = e.getY();
                endX = e.getX();
                endY = e.getY();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1) {
                drawAndUpdate = false;
                System.out.println("mouseReleased");

                for (PhysicalGameObject o : selectingGameObjects) {
                    Point location = o.getLocation();
                    if (location != null) {
                        int x = location.x;
                        int y = location.y;
                        if (x >= leftX && x <= rightX && y >= upY && y <= downY) {
                            o.setSelectedByCursor(true);
                        } else if(!shiftKey){
                            o.setSelectedByCursor(false);
                        }
                    }
                }
            }

            if(e.getButton()==MouseEvent.BUTTON3) {
                System.out.println("mouseReleased BUTTON3");
                for(PhysicalGameObject o: selectingGameObjects){
                    if(o.isSeletedByCursor()){
                        o.setClickCursorLocation(new Point(e.getX(), e.getY()));
                    }
                }
                //// TODO: 31.05.2016 реализвать движение объектов по нажатию клавиши
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouseEntered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
           // System.out.println("mouseExited");
        }
    }

    class CursorKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyCode() == 0){//буква ё или 0
                for (PhysicalGameObject o : selectingGameObjects) {
                    o.setSelectedByCursor(true);
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SHIFT){
                shiftKey = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SHIFT){
                shiftKey = false;
            }
        }
    }
}
