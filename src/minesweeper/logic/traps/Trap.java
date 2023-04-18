package minesweeper.logic.traps;

import minesweeper.logic.Field;
import minesweeper.logic.myexceptions.*;

import java.io.Serializable;

/**
 * Absztrakt osztaly, mely az osszes csapa
 */
public abstract class Trap implements Serializable {
    /**
     * A csapda helye
     */
    protected Field place;

    /**
     * A csapda megtalalasa
     * @throws GameOverException jatek vege
     * @throws GameWonException jatek megnyerve
     * @throws StuckException jatekos megakad
     */
    public void found() throws GameOverException, GameWonException, StuckException {}

    /**
     * Beallitja a csapda helyet
     * @param place a beallitando mezo
     */
    public void setPlace(Field place) {
        this.place = place;
    }
}
