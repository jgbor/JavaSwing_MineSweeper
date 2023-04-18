package minesweeper.logic.traps;

import minesweeper.logic.myexceptions.StuckException;
import org.junit.Test;

public class BeartrapTest {

    @Test(expected = StuckException.class)
    public void foundtest() throws StuckException{
        Beartrap bt= new Beartrap();
        bt.found();
    }
}