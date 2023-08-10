package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// the panel where info is displayed
public class InfoPanel extends JPanel {
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private static final String SCORE_TXT = "Score: ";
    private static final String HEALTH_TXT = "Health: ";
    private Game game;
    private JLabel score;
    private JLabel health;
    private JButton loadGame;
    private JButton saveGame;

    // EFFECTS: sets the background colour and draws the initial labels
    public InfoPanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));
        score = new JLabel(SCORE_TXT + game.getScore());
        score.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        health = new JLabel(HEALTH_TXT + game.getPlayer().getHealth());
        health.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(score);
        add(Box.createHorizontalStrut(10));
        add(health);
        add(Box.createHorizontalStrut(10));
        setSaveButton();
        add(Box.createHorizontalStrut(10));
        setLoadButton();
    }

    // MODIFIES: this
    // EFFECTS: sets a JButton that saves game when pressed
    private void setSaveButton() {
        saveGame = new JButton("Save Game");
        saveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.saveGame();
            }
        });
        add(saveGame);
    }

    // MODIFIES: this
    // EFFECTS: sets a JButton that loads game when pressed
    private void setLoadButton() {
        loadGame = new JButton("Load Game");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.loadGame();
            }
        });
        add(loadGame);
    }

    // MODIFIES: this
    // EFFECTS: updates score and health to reflect game state
    public void update() {
        score.setText(SCORE_TXT + game.getScore());
        health.setText(HEALTH_TXT + game.getPlayer().getHealth());
//        }
    }
}
