package minesweeper.logic;

import minesweeper.logic.myexceptions.GameOverException;
import minesweeper.logic.myexceptions.GameWonException;
import minesweeper.logic.myexceptions.StuckException;
import minesweeper.logic.traps.Mine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {
    private Field f;

    @Before
    public void setUp(){
        f= new Field(0,0);
    }

    @Test
    public void addNeighbourTest() {
        Field neighbour= new Field(0,1);
        f.addNeighbour(neighbour);
        assertTrue(f.getNeighbours().contains(neighbour));
    }

    @Test(expected = NullPointerException.class)
    public void revealedTest() throws GameOverException, GameWonException, StuckException {
        f.revealed();
        assertTrue("Nem sikerül felfedni a mezőt",f.isExplored());
    }

    @Test
    public void flaggedTest() {
        assertFalse("Kezdetben meg van jelölve",f.isFlagged());
        f.changeFlagged();
        assertTrue("Nem sikerül megjelölni",f.isFlagged());
    }

    @Test
    public void trapTest() {
        assertNull(f.getTrap());
        Mine m = new Mine();
        f.setTrap(m);
        assertSame(m,f.getTrap());
    }

    @Test
    public void getX() {
        int x=4;
        Field field= new Field(x,0);
        assertEquals(x,field.getX());
    }

    @Test
    public void getY() {
        int y=24;
        Field field= new Field(0,y);
        assertEquals(y,field.getY());
    }

    @Test
    public void getTrapsaroundTest() {
        assertEquals(0,f.getTrapsaround());

    }
}