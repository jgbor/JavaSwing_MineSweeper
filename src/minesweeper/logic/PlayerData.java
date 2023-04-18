package minesweeper.logic;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Az osztaly egy ranglistat valosit meg a jatekosok pontszama alapjan
 */
public class PlayerData extends AbstractTableModel implements Serializable {
    /** A jatekosok listaja*/
    private ArrayList<Player> players;

    /**
     * Konstruktor, amely fajlbol beolvassa a korabbi adatokat, hiba eseten ures listat hoz letre
     */
    public PlayerData(){
        try {
            FileInputStream f = new FileInputStream("leaderboard.dat");
            ObjectInputStream in = new ObjectInputStream(f);
            players = (ArrayList<Player>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException exception) {
            players= new ArrayList<>();
        }
    }

    /**
     * Sorok szamat adja vissza
     * @return sorok szama
     */
    public int getRowCount() {
        return players.size();
    }

    /**
     * Oszlopok szamat adja vissza
     * @return 4, mert ennyi oszlopbol all
     */
    public int getColumnCount() {
        return 4;
    }

    /**
     * Adott helyen levo erteket ad vissza a tablazatbol
     * @param rowIndex hanyadik sorbol
     * @param columnIndex hanyadik oszlopbol
     * @return a kert adat
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player player = players.get(rowIndex);
        switch (columnIndex){
            case 0:
                return rowIndex+1;
            case 1:
                return player.getName();
            case 2:
                return player.getScore();
            default:
                return player.getTime();
        }
    }

    /**
     * Az oszlop nevet adja vissza
     * @param column hanyadik oszlop
     * @return az oszlop neve
     */
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Helyezés";
            case 1:
                return "Név";
            case 2:
                return "Pontszám";
            default:
                return "Idő";
        }
    }

    /**
     * Jatekos hozzaadasa a listahoz
     * @param p ezt a jatekost kell beilleszteni
     */
    public void addPlayer(Player p){
        players.add(p);
        //lista ujrarendezese
        players.sort(new Comparator<Player>() {
            public int compare(Player o1, Player o2) {
                return o2.getScore()-o1.getScore();
            }
        });
        super.fireTableRowsInserted(0,players.size());
    }

    /**
     * Adatok kimentese fajlba
     */
    public void saveData(){
        try {
            FileOutputStream f = new FileOutputStream("leaderboard.dat");
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(players);
            out.close();
        }catch (IOException ignored){}
    }
}
