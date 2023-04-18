package minesweeper.logic;

import java.io.Serializable;
import java.util.ArrayList;

import minesweeper.logic.myexceptions.GameOverException;
import minesweeper.logic.myexceptions.GameWonException;
import minesweeper.logic.myexceptions.StuckException;
import minesweeper.logic.traps.*;

/**
 * Az osztaly egy mezot valosit meg
 */
public class Field implements Serializable {
    /** A rajta levo csapda, ha nincs rajta, akkor az erteke null*/
    private Trap trap;
    /** Meg van-e jelolve a mezo*/
    private boolean flagged;
    /** A mezo x koordinataja*/
    private int x;
    /** A mezo y koordinataja*/
    private int y;
    /** Hany db csapda van a mezo korul*/
    private transient int trapsaround=0;
    /** Fel lett-e mar fedezve a mezo*/
    private boolean explored;
    /** A mezo szomszedjainak a listaja*/
    private transient ArrayList<Field> neighbours;
    /** Melyik jatekhoz tartozik a mezo*/
    private transient Game game;

    /**
     * A mezo konstruktora
     * @param x A mezo x koordinataja
     * @param y A mezo y koordinataja
     */
    public Field(int x, int y){
        this.x=x;
        this.y=y;
        trap = null;
        trapsaround=0;
        neighbours=new ArrayList<>();
        explored=false;
    }

    /**
     * Szomszedos mezo hozzaadasa
     * @param neighbour A hozzaadando mezo
     */
    public void addNeighbour(Field neighbour) {
        if (neighbours==null){
            neighbours=new ArrayList<>();
        }
        if (neighbour.trap!=null)
            trapsaround++;
        neighbours.add(neighbour);
    }

    /**
     * A mezo felfedese, ha van rajta csapda, meghivja arra a found() fuggvenyt, vagy, ha 0 csapda van korulotte, akkor a szomszedos mezoket is felfedi
     * @throws GameOverException jatek vege
     * @throws GameWonException jatek megnyerve
     * @throws StuckException jatekos megakad
     */
    public void revealed() throws GameOverException, GameWonException, StuckException {
        if (!explored) {
            explored = true;
            if (trap != null) {
                trap.found();
            } else {
                game.incRevealedfieldnumber();
                if (trapsaround == 0) {
                    for (Field n : neighbours) {
                        n.revealed();
                    }
                }
            }
        }
    }

    //setterek és getterek
    /**
     * A jeloles fel/levetele
     */
    public void changeFlagged() {
        flagged= !flagged;
    }

    /**
     * Megmondja, hogy meg van-e jelolve a mezo
     * @return flagged erteke
     */
    public boolean isFlagged(){
        return flagged;
    }

    /**
     * Visszaadja a mezon levo csapdat
     * @return a mezon levo csapda
     */
    public Trap getTrap() {
        return trap;
    }

    /**
     * A mezore teszi a kapott csapdat
     * @param t a mezore rakando csapda
     */
    public void setTrap(Trap t) {
        this.trap = t;
        trap.setPlace(this);
    }

    /**
     * Az x koordinatat adja vissza
     * @return x erteke
     */
    public int getX() {
        return x;
    }

    /**
     * Az y koordinatat adja vissza
     * @return y erteke
     */
    public int getY() {
        return y;
    }

    /**
     * Megmondja, hany csapda van a mezo korul
     * @return trapsaround erteke
     */
    public int getTrapsaround() {
        return trapsaround;
    }

    /**
     * A szomszedok listajat adja vissza
     * @return szomszedok listaja
     */
    public ArrayList<Field> getNeighbours() {
        return neighbours;
    }

    /**
     * Megmondja, fel lett-e mar fedezve a mezo
     * @return explored erteke
     */
    public boolean isExplored() {
        return explored;
    }

    /**
     * Beallitja, melyik jatekhoz tartozik ez a mezo
     * @param game a jatek amihez tartozik
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
