package ru.game.pattern.model;

/**
 * Created by Uskov Dmitry on 10.06.2016.
 */

/**
 * Информация об объекте игроке, необходимая объекту класса GameBorder
 * для получения инофрмации об объекте-игроке ДО создания самого объекта.
 * Возможно этот класс будет исопльзоваться и для обменаи нформации между другими объектами,
 * но первоночально он создавлася для передачи информации из PlayerFabric в GameBorder
 */
public class PlayerInfo {

    /**
     * Путь до изображения с иконкой персонажа (та, что отобращается на панели)
     */
    private String iconPath;

    /**
     * Стоимость персонажа
     */
    private int cost;

    /**
     * Просто тэг. Вдруг пригодится
     */
    private String playerTag;

    public PlayerInfo(String iconPath, int cost, String playerTag) {
        this.iconPath = iconPath;
        this.cost = cost;
        this.playerTag = playerTag;
    }

    public String getIconPath() {
        return iconPath;
    }

    public int getCost() {
        return cost;
    }

    public String getPlayerTag() {
        return playerTag;
    }
}
