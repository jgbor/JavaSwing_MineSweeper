package minesweeper.logic.traps;

import minesweeper.logic.myexceptions.GameOverException;

/**
 * Az osztaly egy hagyomanyos aknakeresos aknat valosit meg
 */
public class Mine extends Trap{
    /**
     * Ha megtalaljak az aknat jatek vege kivetelt dob
     * @throws GameOverException jatek vege kivetel
     */
    public void found() throws GameOverException {
        throw new GameOverException();
    }
}
