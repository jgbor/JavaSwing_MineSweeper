package minesweeper.logic.traps;

import minesweeper.logic.Field;
import minesweeper.logic.myexceptions.GameOverException;
import minesweeper.logic.myexceptions.GameWonException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetonatorTest {
    private Field f;

    @Before
    public void setUp(){
        f= new Field(0,1);
        for (int i =0;i<3;i++){
            Field n =new Field(0,i);
            f.addNeighbour(n);
        }
    }

    @Test(expected = NullPointerException.class)
    public void foundTest() throws GameOverException, GameWonException {
        Detonator detonator= new Detonator();
        detonator.setPlace(f);
        detonator.found();
        for (Field n : f.getNeighbours()){
            assertTrue(n.isExplored());
        }
    }
}