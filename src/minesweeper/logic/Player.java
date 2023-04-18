package minesweeper.logic;

import java.io.Serializable;
import java.sql.Time;

/**
 * Egy jatekos adatait tarolja a ranglistahoz
 */
public class Player implements Serializable {
    /** A jatekos neve*/
    private String name;
    /** A jatekos pontszama*/
    private int score;
    /** A jatekanak az ideje*/
    private Time time;

    /**
     * Konstruktor
     * @param name jatekos neve
     * @param score jatekos pontszama
     * @param seconds jatek ideje masodpercben
     */
    public Player(String name, int score, int seconds) {
        this.name = name;
        this.score = score;
        time = new Time((seconds-3600)*1000);
    }

    //setterek és getterek
    /**
     * A jatekos nevet adja meg
     * @return name erteke
     */
    public String getName() {
        return name;
    }

    /**
     * A jatekos pontszamat adja meg
     * @return score erteke
     */
    public int getScore() {
        return score;
    }

    /**
     * A jatekos idejet adja vissza Time classban
     * @return time erteke
     */
    public Time getTime() {
        return time;
    }
}
