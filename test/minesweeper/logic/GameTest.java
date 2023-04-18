package minesweeper.logic;

import minesweeper.logic.myexceptions.GameWonException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private Game g;

    @Before
    public void setUp(){
        g=new Game();
    }

    @Test
    public void constructorTest(){
        assertEquals(20,g.getHeight());
        assertEquals(20,g.getWidth());
        assertEquals(0.25, g.getPercent(),0.001);
        assertEquals(0,g.getTime());
        assertNull(g.getMap());
    }

    @Test
    public void setHeightTest() {
        g.setHeight(34);
        assertEquals(34,g.getHeight());
    }

    @Test
    public void setWidthTest() {
        g.setWidth(21);
        assertEquals(21,g.getWidth());
    }

    @Test
    public void setPercentTest() {
        g.setPercent(0.56);
        assertEquals(0.56,g.getPercent(),0.001);
    }

    @Test
    public void getMapTest() {
        g.newGame();
        assertNotNull(g.getMap());
    }

    @Test
    public void spentTimeTest(){
        int i=0;
        for (i=0;i<10;i++){
            g.timeSpent();
        }
        assertEquals(i,g.getTime());
    }

    @Test(expected = GameWonException.class)
    public void incRevealedfieldnumberTest() throws GameWonException {
        g.setPercent(99);
        for (int i=0;i<5;i++){
            g.incRevealedfieldnumber();
        }
    }
}