package minesweeper.visual;

import minesweeper.logic.Field;
import minesweeper.logic.myexceptions.*;
import minesweeper.logic.traps.Detonator;
import minesweeper.logic.traps.Mine;
import minesweeper.logic.traps.Trap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Egy mezo gombjat megvalosito osztaly
 */
public class MineButton extends JButton {
    /** A gombhoz tartozo mezo*/
    private Field f;
    /** A szamokhoz tartozo kepek tombje*/
    private ImageIcon[] numbers;
    /** A foablak*/
    private MineSweeperFrame mainframe;

    /*
     * Szamok tombjenek inicicalizalasa
     */
    {
        numbers = new ImageIcon[9];
        for (int i=0;i<9;i++){
            numbers[i]= new ImageIcon(getClass().getResource("/"+i+".png"));
        }
    }

    /**
     * Megmondja egy csapdahoz melyik kepfajl tartozik
     * @param t a csapda, amihez a kepet keressuk
     * @return a t-hez tartozo kep
     */
    private ImageIcon getTrapImage(Trap t){
        ImageIcon i;
        if (t instanceof Mine) {
            i = new ImageIcon(getClass().getResource("/mine.png"));
        }else if(t instanceof Detonator){
            i = new ImageIcon(getClass().getResource("/detonator_switch.png"));
        }else{
            i= new ImageIcon(getClass().getResource("/beartrap.png"));
        }
        return i;
    }

    /**
     * Konstruktor
     * @param field a gombhoz tartozo mezo
     * @param mainframe a foablak
     */
    public MineButton(Field field, MineSweeperFrame mainframe){
        this.f=field;
        this.mainframe = mainframe;
        setPreferredSize(new Dimension(21,21));
        addMouseListener(new MineButtonListener());
        if (!f.isFlagged()) {
            if (f.isExplored()) {
                if (f.getTrap() == null) {
                    setIcon(numbers[f.getTrapsaround()]);
                }else{
                    setIcon(getTrapImage(f.getTrap()));
                }
            } else {
                setIcon(new ImageIcon(getClass().getResource("/unexplored.png")));
            }
        }else{
            setIcon(new ImageIcon(getClass().getResource("/flag.png")));
        }
    }

    /**
     * A gombhoz tartozo MouseButtonListener
     */
    private class MineButtonListener extends MouseAdapter {
        /**
         * Bal egergomb eseten felfedjuk a mezot, jobb egergomb eseten megjeloljuk
         * @param e event
         */
        public void mouseClicked(MouseEvent e) {
            if (MineButton.this.isEnabled()) {
                try {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (!f.isFlagged()) {
                            if (f.getTrap() == null) {
                                setIcon(numbers[f.getTrapsaround()]);
                            } else {
                                setIcon(getTrapImage(f.getTrap()));
                            }
                            f.revealed();
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        if (!f.isExplored()) {
                            f.changeFlagged();
                            if (f.isFlagged()) {
                                setIcon(new ImageIcon(getClass().getResource("/flag.png")));
                            } else {
                                setIcon(new ImageIcon(getClass().getResource("/unexplored.png")));
                            }
                        }
                    }
                    mainframe.refresh();
                    //ha kapunk valami kivetelt mezo nezesenel, mert ott csapda vol, akkor azt itt kezeljuk le
                } catch (GameOverException ex) {
                    mainframe.gameFinished(false);
                }catch (GameWonException     ex){
                    mainframe.gameFinished(true);
                }catch (StuckException ex){
                    mainframe.stuck(MineButton.this);
                }
            }
        }
    }

    /**
     * A gombhoz tartozo mezot adja vissza
     * @return a mezo
     */
    public Field getF() {
        return f;
    }
}
