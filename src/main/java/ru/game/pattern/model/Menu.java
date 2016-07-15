package ru.game.pattern.model;

import ru.game.pattern.controller.GameController;
import ru.game.pattern.controller.GameStatus;
import ru.game.pattern.controller.PatternGameMouseListener;
import ru.game.pattern.controller.Property;
import ru.game.pattern.view.PatternGameGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 24.06.2016.
 */
public class Menu extends GameObject {

    private GameStatus gameStatus;

    private WindowInfo windowInfo;

    private PatternGameMouseListener menuMouseListener;

    private BufferedImage menuBackground;

    private int BUTTON_WIDTH = 350;

    private int BUTTON_HEIGHT = 80;

    private int BUTTON_BORDER = 5;

    private int buttonY = 180;

    private boolean buttonStartActive = false;

    private boolean buttonGuideActive = false;

    private boolean showGuid = false;

    public Menu(GameStatus gameStatus, WindowInfo windowInfo) throws IOException {
        this.gameStatus = gameStatus;
        this.windowInfo = windowInfo;
        menuMouseListener = new MenuMouseListener();
        menuBackground = ImageIO.read(new File(Property.RESOURCES_PATH+"menu.png"));
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }

    @Override
    public PatternGameMouseListener getMouseListener() {
        return menuMouseListener;
    }

    @Override
    public void draw(PatternGameGraphics2D g) {
        g.drawImage(menuBackground, 0,0, windowInfo.getDefaultWidth(), windowInfo.getDefaultHeight(), null);
        drawButtonStart(g);
        drawButtonGuide(g);

        if(showGuid){
            g.setColor(new Color(0,0,0, 220));
            g.fillRect(0,0,windowInfo.getDefaultWidth(), windowInfo.getDefaultHeight());
           /* graphics2d.setColor(Color.WHITE);
            graphics2d.setFont(new Font("TimesRoman", Font.PLAIN, 48));
            graphics2d.drawString(Property.GUIDE, 0, windowInfo.getDefaultHeight());
            graphics2d.setFont(new Font("default", Font.BOLD, 10));*/
            try {
                g.drawImage(ImageIO.read(new File(Property.RESOURCES_PATH+"guide.png")), 0,0, windowInfo.getDefaultWidth(),
                        windowInfo.getDefaultHeight(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void drawButtonStart(PatternGameGraphics2D g) {
        Color borderColor;
        Color buttonColor;
        Color textColor;

        if(buttonStartActive) {
            borderColor = new Color(0, 80, 0);
            buttonColor = new Color(0, 130, 0);
            textColor = new Color(220, 220, 220);
        } else {
            borderColor = new Color(0, 100, 0);
            buttonColor = new Color(0, 150, 0);
            textColor = Color.WHITE;
        }

        g.setColor(borderColor);
        g.fillRect( (windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2 - BUTTON_BORDER, buttonY -BUTTON_BORDER, BUTTON_WIDTH+2*BUTTON_BORDER, BUTTON_HEIGHT+2*BUTTON_BORDER);
        g.setColor(buttonColor);
        g.fillRect( (windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        g.setColor(textColor);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
        g.drawString("Start", windowInfo.getDefaultWidth()/2 -60 , buttonY + BUTTON_HEIGHT - 20);
        g.setFont(new Font("default", Font.BOLD, 10));


    }

    private void drawButtonGuide(PatternGameGraphics2D g) {
        Color borderColor;
        Color buttonColor;
        Color textColor;
        if(buttonGuideActive) {
            borderColor = new Color(0, 80, 0);
            buttonColor = new Color(0, 130, 0);
            textColor = new Color(220, 220, 220);
        } else {
            borderColor = new Color(0, 100, 0);
            buttonColor = new Color(0, 150, 0);
            textColor = Color.WHITE;
        }
        g.setColor(borderColor);
        g.fillRect( (windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2 - BUTTON_BORDER, buttonY + BUTTON_HEIGHT -BUTTON_BORDER + 70 , BUTTON_WIDTH+2*BUTTON_BORDER, BUTTON_HEIGHT+2*BUTTON_BORDER);
        g.setColor(buttonColor);
        g.fillRect( (windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2, buttonY + BUTTON_HEIGHT + 70, BUTTON_WIDTH, BUTTON_HEIGHT);
        g.setColor(textColor);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
        g.drawString("User Guide", windowInfo.getDefaultWidth()/2 -130 , buttonY + 2*BUTTON_HEIGHT - 20 + 70);
        g.setFont(new Font("default", Font.BOLD, 10));
    }

    @Override
    public void update(GameController gameController) {

    }

    @Override
    public Type getType() {
        return Type.other;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    private boolean inButtonStart(int x, int y){
        return (x>(windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2 - BUTTON_BORDER
                && x<(windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2 - BUTTON_BORDER + BUTTON_WIDTH+2*BUTTON_BORDER
                && y> buttonY -BUTTON_BORDER && y<buttonY -BUTTON_BORDER+BUTTON_HEIGHT+2*BUTTON_BORDER);
    }

    private boolean inButtonGuide(int x, int y){
        return (x>(windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2 - BUTTON_BORDER
                &&x<(windowInfo.getDefaultWidth() - BUTTON_WIDTH)/2 - BUTTON_BORDER + BUTTON_WIDTH+2*BUTTON_BORDER
                &&y> buttonY + BUTTON_HEIGHT -BUTTON_BORDER + 70
                &&y< buttonY + BUTTON_HEIGHT -BUTTON_BORDER + 70 + BUTTON_HEIGHT+2*BUTTON_BORDER);
    }

    class MenuMouseListener extends PatternGameMouseListener{

        @Override
        public void patternGameMouseClicked(MouseEvent e) {
            //startGame();
        }

        @Override
        public void patternGameMousePressed(MouseEvent e) {

            if(inButtonStart(e.getX(), e.getY())){
                buttonStartActive = true;
            }

            if(inButtonGuide(e.getX(), e.getY())){
                buttonGuideActive = true;
            }
        }

        @Override
        public void patternGameMouseReleased(MouseEvent e) {
            if(showGuid){
                showGuid = false;
                return;
            }
            if(buttonStartActive && inButtonStart(e.getX(), e.getY())){
                startGame();
            }
            if(buttonGuideActive && inButtonGuide(e.getX(), e.getY())){
                startGuid();
            }

            buttonStartActive = false;
            buttonGuideActive = false;
        }

        @Override
        public void patternGameMouseEntered(MouseEvent e) {

        }

        @Override
        public void patternGameMouseExited(MouseEvent e) {

        }
    }

    private void startGuid() {
        showGuid = true;
    }

    private void startGame() {
        gameStatus.setMenu(false);
        destroy();
    }
}
