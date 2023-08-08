package ui;


// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// The panel where the game is rendered

import model.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final String OVER = "Game Over!";
    private Game game;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isOver()) {
            gameOver(g);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws the game
    private void drawGame(Graphics g) {
        drawPlayer(g);
        drawEnemies(g);
        drawBullets(g);
        drawItems(g);
    }

    // MODIFIES: g
    // EFFECTS: draws player onto g
    private void drawPlayer(Graphics g) {
        Player p = game.getPlayer();
        Color savedCol = g.getColor();
        g.setColor(Player.COLOR);
        g.fillRect(p.getX() - Player.SIZE / 2, p.getY() - Player.SIZE / 2, Player.SIZE, Player.SIZE);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS: draws the enemies onto g
    private void drawEnemies(Graphics g) {
        for (Enemy next : game.getEnemies()) {
            drawEnemy(g, next);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws the enemy e onto g
    private void drawEnemy(Graphics g, Enemy e) {
        Color savedCol = g.getColor();
        g.setColor(Enemy.COLOR);
        g.fillOval(e.getX() - e.getXsize() / 2, e.getY() - e.getYsize() / 2, e.getXsize(), e.getYsize());
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws the bullets onto g
    private void drawBullets(Graphics g) {
        for (Bullet next : game.getEnemyBullets()) {
            drawBullet(g, next, Bullet.ECOLOR);
        }
        for (Bullet next : game.getPlayerBullets()) {
            drawBullet(g, next, Bullet.PCOLOR);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the bullet b onto g with color c
    private void drawBullet(Graphics g, Bullet b, Color c) {
        Color savedCol = g.getColor();
        g.setColor(c);
        g.fillOval(b.getX() - b.getRadius(), b.getY() - b.getRadius(), b.getRadius() * 2, b.getRadius() * 2);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws items onto g
    private void drawItems(Graphics g) {
        for (Item next : game.getItems()) {
            drawItem(g, next);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws item i onto g
    private void drawItem(Graphics g, Item i) {
        Color savedCol = g.getColor();
        if (i.getIdentifier() == "Buff") {
            g.setColor(Buff.COLOR);
        } else {
            g.setColor(Weapon.COLOR);
        }
        g.fillOval(i.getX() - Item.RADIUS, i.getY() - Item.RADIUS, Item.RADIUS * 2, Item.RADIUS * 2);
        g.setColor(savedCol);
    }


    // MODIFIES: g
    // EFFECTS:  draws game over message onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position ypos
    private void centreString(String str, Graphics g, FontMetrics fm, int ypos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, ypos);
    }
}
