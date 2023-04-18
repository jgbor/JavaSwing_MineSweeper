package minesweeper.logic.traps;

import minesweeper.logic.Field;
import minesweeper.logic.myexceptions.*;

import java.util.ArrayList;

/**
 * Az osztaly egy olyan csapdat valosit meg, amit, ha megtalalnak, felfedi az osszes korulotte levő mezot
 */
public class Detonator extends Trap{
    /**
     * Ha megtalaljak a detonatort, felfedi az osszes korulotte levo mezot
     * @throws GameOverException jatek elvesztve kivetel, ha a szomszedos mezokon
     * @throws GameWonException jatek megnyerve kivetelt dob, ha felfedi kozben az osszes mezot
     */
    public void found() throws GameOverException, GameWonException {
        ArrayList<Field> neighbours = place.getNeighbours();
        for (Field n : neighbours) {
            if (n.isFlagged()){
                //ha meg van jelölve, akkor kiszedi a zászlót
                n.changeFlagged();
            }
            try {
                n.revealed();
            }catch (StuckException ignored){}
        }
    }
}
