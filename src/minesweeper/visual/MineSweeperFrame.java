package minesweeper.visual;

import minesweeper.logic.Field;
import minesweeper.logic.Game;
import minesweeper.logic.Player;
import minesweeper.logic.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A foablak, ez a fo osztalya a programnak
 */
public class MineSweeperFrame extends JFrame {
    /** Maga a palya*/
    private JPanel table;
    /** Uj jatek gomb*/
    private JButton newgame;
    /** Ora helye*/
    private JLabel clock;
    /** Menu a fejlecben*/
    private MineSweeperMenuBar menubar;
    /** A jatek adatai*/
    private Game gamedata;
    /** A jatekosok adatai*/
    private PlayerData playerdata;
    /** Az ora*/
    private Timer t;

    /**
     * Konstruktor
     */
    public MineSweeperFrame(){
        //új játék kezdése
        gamedata= new Game();
        gamedata.newGame();
        playerdata= new PlayerData();

        //ablak alapadatai
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Aknakereső 2.0");
        setSize(600,500);
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
        setIconImage(icon.getImage());
        setLocation(500,50);
        setResizable(false);
        setLayout(new BorderLayout());

        //menubar hozzáadása
        menubar= new MineSweeperMenuBar(gamedata,playerdata, this);
        setJMenuBar(menubar);

        table= new JPanel();

        JScrollPane scrollPane= new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);

        //az a pálya feletti rész megvalósitása (egy óra, alatta egy új játék gomb
        JPanel upper= new JPanel();
        upper.setLayout(new BorderLayout());
        newgame= new JButton("Új játék");
        newgame.addActionListener(new NewGameButtonListener());
        JPanel forbutton= new JPanel();
        forbutton.add(newgame);
        upper.add(forbutton,BorderLayout.SOUTH);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        clock = new JLabel(sdf.format(0));
        JPanel forclock= new JPanel();
        forclock.add(clock);
        upper.add(forclock,BorderLayout.NORTH);
        add(upper,BorderLayout.NORTH);

        //óra működése
        t= new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamedata.timeSpent();
                clock.setText(sdf.format(new Date(1000*gamedata.getTime())));
            }
        });
        t.start();

        refresh();
        pack();
    }

    /**
     * A palya mezoinek a ujrarajzolasa, frissitese
     */
    public void refresh(){
        //ha nem megy az ora elinditjuk
        if (!t.isRunning()){
            clock.setText(new SimpleDateFormat("mm:ss").format(new Date(1000*gamedata.getTime())));
            t.start();
        }
        //leszedjuk az osszes elozo gombot
        table.removeAll();
        table.setLayout(new GridLayout(gamedata.getHeight(),gamedata.getWidth()));
        for (int i=0;i<gamedata.getHeight();i++){
            for(int j=0;j<gamedata.getWidth();j++){
                table.add(new MineButton(gamedata.getMap().get(i).get(j),this));
            }
        }
        repaint();
        //megfelelő ablakméret beállítása
        if (gamedata.getHeight()>30){
            setPreferredSize(new Dimension(25*gamedata.getWidth(),700));
        }else {
            setPreferredSize(null);
        }
        pack();
    }

    /**
     * Jatek befejezese: palya letiltasa, ora leallitasa, gyozelem eseten rangsorba rakas
     * @param victory nyert-e a jatekos
     */
    public void gameFinished(boolean victory){
        t.stop();
        //pálya letiltása
        for (Component c : table.getComponents()) {
            c.setEnabled(false);
        }
        if (!victory) {
            JOptionPane.showMessageDialog(this,"Vesztettél :(");
        }else{
            //győzelem esetén bekérjük a nevet, és berakjuk a játékost a listába
            String name = JOptionPane.showInputDialog("Nyertél, add meg a neved!");
            Player p= new Player(name, (int) (gamedata.getHeight()*gamedata.getWidth()*gamedata.getPercent()*10000/gamedata.getTime()),
                    gamedata.getTime());
            playerdata.addPlayer(p);
            playerdata.saveData();
        }
        repaint();
    }

    /**
     * Jatekos elakadasa, az osszes nem szomszedos mezo letiltasa
     * @param pressed melyik gombot nyomtuk le, ami ezt kivaltotta
     */
    public void stuck(MineButton pressed){
        for (Component c : table.getComponents()) {
            Field f= pressed.getF();
            //az összes nem szomszédos mező letiltása
            if (!f.getNeighbours().contains(((MineButton) c).getF()) || ((MineButton) c).getF().isExplored()) {
                c.setEnabled(false);
            }else{
                c.setEnabled(true);
            }
        }
    }

    /**
     * Uj gomb mukodesete leiro Listener
     */
    private class NewGameButtonListener implements ActionListener{
        /**
         * Gomb mukodese
         * @param e event
         */
        public void actionPerformed(ActionEvent e) {
            gamedata.newGame();
            clock.setText(new SimpleDateFormat("mm:ss").format(0));
            t.start();
            refresh();
        }
    }

    /**
     * Main fuggveny, letrehoz egy fo ablakot es megjelenitit
     * @param args argumentumok
     */
    public static void main(String[] args) {
        MineSweeperFrame msframe= new MineSweeperFrame();
        msframe.setVisible(true);
    }
}
