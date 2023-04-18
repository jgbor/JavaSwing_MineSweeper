package minesweeper.logic.traps;

import minesweeper.logic.myexceptions.GameOverException;
import org.junit.Test;

public class MineTest {

    @Test(expected = GameOverException.class)
    public void foundtest() throws GameOverException{
        Mine m= new Mine();
        m.found();
    }
}