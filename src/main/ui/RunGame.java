package ui;

import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;


// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// The main window where game is played
public class RunGame extends JFrame {
    private static final int INTERVAL = 10;
    private static final String JSON_STORE = "./data/game.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Game game;
    private GamePanel gp;
    private InfoPanel ip;
//    private StatPanel sp;
//    private BuffPanel bp;
//    private WeaponPanel wp;

    // EFFECTS: sets up window in which game will be played
    public RunGame() {
        super("A Game Inspired by Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        game = new Game();
        gp = new GamePanel(game);
        ip = new InfoPanel(game);
//        sp = new StatPanel(game);
//        bp = new BuffPanel(game);
//        wp = new WeaponPanel(game);
        add(gp);
        add(ip, BorderLayout.NORTH);
//        add(sp);
//        add(bp);
//        add(wp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update();
                gp.repaint();
                ip.update();
//                sp.update();
//                bp.update();
//                wp.update();
            }
        });
        t.start();
    }

    // a key handler to respond to key events
    private class KeyHandler extends KeyAdapter {
        private final Set<Integer> pressed = new HashSet<Integer>();

        // MODIFIES: this
        // EFFECTS: adds pressed keycode to pressed and sends it to game and panels to handle
        @Override
        public void keyPressed(KeyEvent e) {
            pressed.add(e.getKeyCode());
            game.handleKey(pressed);
//            sp.handleKey(e.getKeyCode());
//            bp.handleKey(e.getKeyCode());
//            wp.handleKey(e.getKeyCode());
        }

        // MODIFIES: this, game
        // EFFECTS: removes released keycode from pressed and sends it to game to handle
        @Override
        public void keyReleased(KeyEvent e) {
            pressed.remove(e.getKeyCode());
            game.handleKey(pressed);
        }
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // play the game
    public static void main(String[] args) {
        new RunGame();
    }

    // EFFECTS: saves the game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
