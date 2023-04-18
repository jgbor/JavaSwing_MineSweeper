package minesweeper.visual;

import minesweeper.logic.Game;
import minesweeper.logic.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Menut megvalosito osztaly
 */
public class MineSweeperMenuBar extends JMenuBar {
    /** Mentes es betoltes menu*/
    private JMenu saveandload;
    /** Beallitasok menu*/
    private JMenu settings;
    /** Ranglista menu*/
    private JMenu ranglist;
    /** Jatek adatai*/
    private Game gamedata;
    /** Jatekosok adatai*/
    private PlayerData playerdata;
    /** Foablak*/
    private MineSweeperFrame mainframe;

    /**
     * Konstruktor
     * @param g jatek adatai
     * @param p jatekosok adatai
     * @param mainframe foablak
     */
    public MineSweeperMenuBar(Game g, PlayerData p, MineSweeperFrame mainframe){
        gamedata=g;
        playerdata=p;
        this.mainframe = mainframe;

        //betöltő/mentő menü
        saveandload = new JMenu("Játékállás");
        JMenuItem save= new JMenuItem("Mentés");
        JMenuItem load= new JMenuItem("Megnyitás");
        save.addActionListener(new SaveListener());
        load.addActionListener(new LoadListener());
        saveandload.add(save);
        saveandload.add(load);
        add(saveandload);

        //ranglista menü
        ranglist = new JMenu("Ranglista");
        JMenuItem list = new JMenuItem("Ranglista");
        list.addActionListener(new LeaderboardListener());
        ranglist.add(list);
        add(ranglist);

        //beállítások menü
        settings = new JMenu("Beállítások");
        JMenuItem set= new JMenuItem("Játék beállításai");
        JMenuItem exit= new JMenuItem("Kilépés");
        exit.addActionListener(new ExitListener());
        set.addActionListener(new SettingsListener());
        settings.add(set);
        settings.add(exit);
        add(settings);
    }

    /**
     * Exit opcio Listenerje
     */
    private class ExitListener implements ActionListener {
        /**
         * Exit mukodese
         * @param e event
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Mentes opcio Listenerje
     */
    private class SaveListener implements ActionListener {
        /**
         * Mentes mukodese
         * @param e event
         */
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Igen","Nem"};
            int n = JOptionPane.showOptionDialog(MineSweeperMenuBar.this,
                    "Biztosan el szeretnéd menteni az állást?","Biztosan?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
            if (n==0) {
                try {
                    gamedata.saveGame();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(MineSweeperMenuBar.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Betoltes opcio Listenerje
     */
    private class LoadListener implements ActionListener {
        /**
         * Betoltes mukodese
         * @param e event
         */
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Igen","Nem"};
            int n = JOptionPane.showOptionDialog(MineSweeperMenuBar.this,
                    "Biztosan be szeretnéd tölteni a mentett állást?","Biztosan?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
            if (n==0) {
                try {
                    gamedata.loadGame();
                    mainframe.refresh();
                } catch (IOException | ClassNotFoundException exception) {
                    JOptionPane.showMessageDialog(MineSweeperMenuBar.this, "Hiba betöltés közben", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Ranglista opcio Listenerje
     */
    private class LeaderboardListener implements ActionListener{
        /**
         * Ranglista ablak megnyitasa
         * @param e event
         */
        public void actionPerformed(ActionEvent e) {
            //megnyitunk egy új ablakot a táblázattal
            JDialog frame= new JDialog(mainframe,"Ranglista", true);
            frame.setSize(450,300);
            frame.setLocation(525,200);
            frame.setResizable(false);
            JTable table= new JTable(playerdata);
            table.setFillsViewportHeight(true);
            JScrollPane kozep = new JScrollPane(table);
            frame.add(kozep, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }

    /**
     * Beallitasok opcio Listenerje
     */
    private class SettingsListener implements ActionListener{
        /**
         * Beallitasok ablak megnyitasa
         * @param e event
         */
        public void actionPerformed(ActionEvent e) {
            JDialog frame= new JDialog(mainframe,"Beállítások",true);
            frame.setLocation(525,200);
            frame.setResizable(false);

            //lehetséges méretek
            int maxs=40;
            int mins=10;
            Integer[] sizes = new Integer[maxs-mins+1];
            for(int i=mins;i<=maxs;i++){
                sizes[i-mins]=i;
            }

            //lehetseéges arányok
            int maxp=50;
            int minp=15;
            Integer[] percentages = new Integer[maxp-minp+1];
            for(int i=minp;i<=maxp;i++){
                percentages[i-minp]=i;
            }

            JLabel height = new JLabel("Magasság");
            JLabel width = new JLabel("Szélesség");
            JLabel percentage = new JLabel("Aknák %-a a pályán");
            JButton ok = new JButton("OK");
            JComboBox<Integer> h= new JComboBox<>(sizes);
            JComboBox<Integer> w= new JComboBox<>(sizes);
            JComboBox<Integer> p= new JComboBox<>(percentages);
            //extra típusú aknákhoz JCheckBox
            JCheckBox extras = new JCheckBox("Extra csapdatípusok");

            JPanel upper = new JPanel();
            upper.setLayout(new FlowLayout());
            upper.add(height);
            upper.add(h);
            upper.add(width);
            upper.add(w);
            upper.add(percentage);
            upper.add(p);
            upper.add(extras);

            frame.add(upper,BorderLayout.NORTH);
            frame.add(ok,BorderLayout.SOUTH);

            //OK gomb Listenerje
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gamedata.setHeight((int) h.getSelectedItem());
                    gamedata.setWidth((int) w.getSelectedItem());
                    gamedata.setPercent(0.01*(int) p.getSelectedItem());
                    gamedata.setExtratraps(extras.isSelected());
                    frame.dispose();
                    gamedata.newGame();
                    mainframe.refresh();
                }
            });

            frame.pack();
            frame.setVisible(true);
        }
    }
}
