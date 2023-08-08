package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;

public class StatPanel extends JPanel {
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private static final String MAX_HEALTH = "Max Health: ";
    private static final String ATTACK = "Bonus Attack: ";
    private static final String SPEED = "Movement Speed: ";
    private static final String FIRE_RATE = "Fire Rate: ";
    private Game game;
    private JLabel maxhealth;
    private JLabel attack;
    private JLabel speed;
    private JLabel firerate;

    // EFFECTS: sets the background colour and draws the initial labels
    public StatPanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));
        maxhealth = new JLabel(MAX_HEALTH + game.getPlayer().getMaxHealth());
        maxhealth.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        attack = new JLabel(ATTACK + game.getPlayer().getBonusAttack());
        attack.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        speed = new JLabel(SPEED + game.getPlayer().getSpeed());
        speed.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        firerate = new JLabel(FIRE_RATE + game.getPlayer().getFireRate() + "%");
        firerate.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        add(maxhealth);
        add(Box.createHorizontalStrut(10));
        add(attack);
        add(Box.createHorizontalStrut(10));
        add(speed);
        add(Box.createHorizontalStrut(10));
        add(firerate);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: updates labels to reflect game state
    public void update() {
        maxhealth.setText(MAX_HEALTH + game.getPlayer().getMaxHealth());
        attack.setText(ATTACK + game.getPlayer().getBonusAttack());
        speed.setText(SPEED + game.getPlayer().getSpeed());
        firerate.setText(FIRE_RATE + game.getPlayer().getFireRate() + "%");
    }
}
