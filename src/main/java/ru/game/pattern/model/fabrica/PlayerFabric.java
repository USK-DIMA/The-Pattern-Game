package ru.game.pattern.model.fabrica;

import ru.game.pattern.model.*;
import ru.game.pattern.model.playes.Archer;
import ru.game.pattern.model.playes.Mag;
import ru.game.pattern.model.playes.Priest;
import ru.game.pattern.model.playes.Warrior;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Uskov Dmitry on 09.06.2016.
 */
public interface PlayerFabric {

    /**
     * Возвращает цену обновления до след уровня.
     * @return Цена обновления до след уровня или -1, если обновиться невозможно (например максимальный уровень обновления уже достигнут)
     */
    int nexUpdate();

    /**
     * Возвращает уровень фабрики. Отсчёт начинается с 1.
     * @return уровень фабрики
     */
    int getLvl();

    /**
     * Создает объект-игрока лучника.
     * @param location начальное положение игрока (как правило за экраном)
     * @param targetLocation куда будет двигаться объект при создании
     * @param windowInfo инофрмация об окне
     * @return объект-игрок лучник
     * @throws IOException если не удалось подгрузить ресурсы
     */
    Archer createArhcer(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    /**
     * Создает объект-игрока война.
     * @param location начальное положение игрока (как правило за экраном)
     * @param targetLocation куда будет двигаться объект при создании
     * @param windowInfo инофрмация об окне
     * @return объект-игрок лучник
     * @throws IOException если не удалось подгрузить ресурсы
     */
    Warrior createWarrior(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    /**
     * Создает объект-игрока жреца.
     * @param location начальное положение игрока (как правило за экраном)
     * @param targetLocation куда будет двигаться объект при создании
     * @param windowInfo инофрмация об окне
     * @return объект-игрок лучник
     * @throws IOException если не удалось подгрузить ресурсы
     */
    Priest createPrist(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    /**
     * Создает объект-игрока мага.
     * @param location начальное положение игрока (как правило за экраном)
     * @param targetLocation куда будет двигаться объект при создании
     * @param windowInfo инофрмация об окне
     * @return объект-игрок лучник
     * @throws IOException если не удалось подгрузить ресурсы
     */
    Mag createMag(Point location, Point targetLocation, WindowInfo windowInfo) throws IOException;

    /**
     * Возвращает информацию об объекте-игроке лучнике (как правильно, для каждого уровня фабрики она разная)
     */
    PlayerInfo getArcherInfo();

    /**
     * Возвращает информацию об объекте-игроке войне (как правильно, для каждого уровня фабрики она разная)
     */
    PlayerInfo getWarriorInfo();

    /**
     * Возвращает информацию об объекте-игроке жреце (как правильно, для каждого уровня фабрики она разная)
     */
    PlayerInfo getPristInfo();

    /**
     * Возвращает информацию об объекте-игроке маге (как правильно, для каждого уровня фабрики она разная)
     */
    PlayerInfo getMagInfo();

}
