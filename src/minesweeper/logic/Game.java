package minesweeper.logic;

import minesweeper.logic.myexceptions.GameWonException;
import minesweeper.logic.traps.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A jatek mukodeseert felelos osztaly
 */
public class Game implements Serializable {
    /** A palya magassaga*/
    private int height;
    /** A palya szelessege*/
    private int width;
    /** A palyan levo csapdak aranya*/
    private double percent;
    /** Akar-e a jatekos az extra tipusu csapdakbol, vagy csak a sima aknakeresot szeretne*/
    private boolean extratraps;
    /** Felfedezett mezok szama*/
    private int revealedfieldnumber;
    /** Az eltelt masodpercek szama*/
    private int time;
    /** A palya mezoinek kollekcioja*/
    private ArrayList<ArrayList<Field>> map;

    /**
     * Konstruktor, ami beallit par alap erteket a jateknak
     */
    public Game() {
        height = 20;
        width = 20;
        percent = 0.25;
        revealedfieldnumber = 0;
        extratraps = false;
    }

    /**
     * Uj jatek inditasa
     */
    public void newGame() {
        revealedfieldnumber=0;

        //mezők legyártása és maphez adása
        map = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            ArrayList<Field> newrow = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Field f = new Field(i, j);
                newrow.add(f);
            }
            map.add(newrow);
        }

        //csapdák számának megállapítása
        long numoftraps = Math.round(height * width * percent);
        Random r = new Random();
        int x;
        int y;
        //csapdák helyének megállapítása
        for (int i = 0; i < numoftraps; i++) {
            do {
                x = r.nextInt(height);
                y = r.nextInt(width);
            } while (map.get(x).get(y).getTrap() != null); //ha olyat sorsol, ahol már van, akkor
            int type = 0;
            //ha be van kapcsolva, a többi fajta akna akkor sorsol, hogy milyen legyen
            if (extratraps) {
                type = r.nextInt(3);
            }
            Trap t = null;
            switch (type) {
                case 0 -> t = new Mine();
                case 1 -> t = new Beartrap();
                case 2 -> t = new Detonator();
            }
            map.get(x).get(y).setTrap(t);
        }

        //szomszédok megadása a mezőknek
        setAllNeighbours();
        time = 0;
    }

    /**
     * Jatekallas fajlba mentese
     * @throws IOException hiba eseten kivetelt dob
     */
    public void saveGame() throws IOException {
        FileOutputStream f = new FileOutputStream("gamesave.dat");
        ObjectOutputStream out = new ObjectOutputStream(f);
        out.writeObject(this);
        out.close();
    }

    /** Jatekallas fajlbol betoltese
     *  @throws IOException hiba eseten kivetelt dob
     *  @throws ClassNotFoundException hiba eseten kivetelt dob
     */
    public void loadGame() throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream("gamesave.dat");
        ObjectInputStream in = new ObjectInputStream(f);
        Game loaded = (Game) in.readObject();
        in.close();
        //adatok atmasolasa ebbe a jatekba
        this.height = loaded.height;
        this.width = loaded.width;
        this.map = loaded.map;
        this.percent = loaded.percent;
        this.extratraps = loaded.extratraps;
        this.time=loaded.time;
        this.revealedfieldnumber=loaded.revealedfieldnumber;
        setAllNeighbours();
    }

    /**
     * Az osszes mezo szomszedjat beallitja
     */
    private void setAllNeighbours() {
        for (ArrayList<Field> fields : map) {
            for (Field f : fields) {
                f.setGame(this);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i!=0 || j!=0) {
                            try {
                                f.addNeighbour(map.get(f.getX() + i).get(f.getY() + j));
                            } catch (IndexOutOfBoundsException nincs) {
                             /*ha a szélén van, és emiatt nincs ilyen szomszédja (pl 0,0-nak a -1-es helyeken), akkor elkapjuk a dobott kivételt,
                                 egyébként nincs baj, mert helyesen fog működni*/
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Noveli a megtalalt mezok szamat, ha megvan az osszes jatek megnyerve kivetelt dob
     * @throws GameWonException gyozelem eseten ezt dobja
     */
    public void incRevealedfieldnumber() throws GameWonException{
        revealedfieldnumber++;
        if (revealedfieldnumber>height*width*(1-percent)-1){
                throw new GameWonException();
        }
    }

    //setterek és getterek

    /**
     * A palya magassagat adja meg
     * @return height erteke
     */
    public int getHeight() {
        return height;
    }

    /**
     * A palya magassagat allitja
     * @param height erre az ertekre allitja
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * A palya szelesseget adja meg
     * @return width erteke
     */
    public int getWidth() {
        return width;
    }

    /**
     * A palya szelesseget allitja
     * @param width erre az ertekre allitja
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Az aknak aranyat adja meg
     * @return percent erteke
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Az aknak aranyat allitja be
     * @param percent erre az ertekre allitja
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Beallitja kellenek-e az extra tipusu csapdak
     * @param extratraps erre az ertekre allitja
     */
    public void setExtratraps(boolean extratraps) {
        this.extratraps = extratraps;
    }

    /**
     * Visszaadja a palyat
     * @return map kollekcio
     */
    public ArrayList<ArrayList<Field>> getMap() {
        return map;
    }

    /**
     * Noveli az idot 1 masodperccel
     */
    public void timeSpent (){
        time++;
    }

    /**
     * Az eltelt idot adja vissza
     * @return time erteke
     */
    public int getTime() {
        return time;
    }
}
