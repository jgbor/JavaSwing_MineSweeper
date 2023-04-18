package minesweeper.logic.traps;

import minesweeper.logic.myexceptions.StuckException;

/**
 * Az osztaly egy olyan csapdat valosit meg, amit, ha megtalalnak, utana csak a szomszedos mezore lehet lepni
 */
public class Beartrap extends Trap{
    /**
     * Ha megtalaljak a csapdat beragadas kivetelt dob
     * @throws StuckException beragadas kivetel
     */
    public void found() throws StuckException {
        throw new StuckException();
    }
}
