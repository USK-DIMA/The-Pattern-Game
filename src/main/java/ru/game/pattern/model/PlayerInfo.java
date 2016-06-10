package ru.game.pattern.model;

/**
 * Created by Uskov Dmitry on 10.06.2016.
 */
public class PlayerInfo {

    private String iconPath;

    private int cost;

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
