package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// The main window where game is played
public class RunGame extends JFrame {
    private static final int INTERVAL = 10;

    private Game game;
    private GamePanel gp;
    private InfoPanel ip;
    private StatPanel sp;
    private InteractivePanel bp;

    // EFFECTS: sets up window in which game will be played
    public RunGame() {
        super("A Game Inspired by Space Invaders");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        game = new Game();
        gp = new GamePanel(game);
        ip = new InfoPanel(game);
        sp = new StatPanel(game);
        bp = new InteractivePanel(game);
        add(gp);
        add(ip, BorderLayout.NORTH);
        add(sp, BorderLayout.SOUTH);
        add(bp, BorderLayout.EAST);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                requestFocus();
                game.update();
                gp.repaint();
                ip.update();
                sp.update();
                bp.update();
            }
        });
        t.start();
    }

    // a key handler to respond to key events
    private class KeyHandler extends KeyAdapter {

        // MODIFIES: this
        // EFFECTS: sends keycode for game and panels to handle
        @Override
        public void keyPressed(KeyEvent e) {
            game.handleKey(e.getKeyCode());
        }

        // MODIFIES: this, game
        // EFFECTS: sends keycode to game to handle
        @Override
        public void keyReleased(KeyEvent e) {
            game.handleKeyReleased(e.getKeyCode());
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
}
