package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// a bullet, having damage, speed, radius, mybullet
public class Bullet implements Writable {
    public static final Color PCOLOR = new Color(233, 43, 18);

    public static final Color ECOLOR = new Color(130, 37, 4);

    private int damage;
    private int speed;
    private int radius;
    private boolean mybullet; //true if bullet is player's bullet, false otherwise

    private int xpos;
    private int ypos;
    private int xdirection;
    private int ydirection;
    private String size;

    // EFFECTS: initializes a bullet facing down
    public Bullet(String size, boolean mb, int x, int y) {
        this.size = size;
        mybullet = mb;
        xpos = x;
        ypos = y;
        xdirection = 0;
        ydirection = -1;
        switch (size) {
            case "LARGE":
                damage = 9;
                speed = 2;
                radius = 12;
                break;
            case "MEDIUM":
                damage = 3;
                speed = 4;
                radius = 6;
                break;
            case "SMALL":
                damage = 1;
                speed = 6;
                radius = 3;
                break;
        }
    }

    public String getSize() {
        return size;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isMybullet() {
        return mybullet;
    }

    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public int getXdirection() {
        return xdirection;
    }

    public int getYdirection() {
        return ydirection;
    }

    public void setXdirection(int xdirection) {
        this.xdirection = xdirection;
    }

    public void setYdirection(int ydirection) {
        this.ydirection = ydirection;
    }

    // MODIFIES: this
    // EFFECTS: bullet is moved by direction * speed in x and y direction
    public void move() {
        ypos = ypos + ydirection * speed;
        xpos = xpos + xdirection * speed;
    }

    // EFFECTS: returns true if is player bullet and collided with enemy, false otherwise
    public boolean collidedWith(Enemy e) {
        if (mybullet) {
            Rectangle enemyHitbox = new Rectangle(e.getX() - e.getXsize() / 2, e.getY() - e.getYsize() / 2,
                    e.getXsize(), e.getYsize());
            Rectangle bulletHitbox = new Rectangle(getX() - radius, getY() - radius,
                    radius * 2, radius * 2);
            return enemyHitbox.intersects(bulletHitbox);
        }
        return false;
    }

    // EFFECTS: returns true if is enemy bullet and collided with player, false otherwise
    public boolean collidedWith(Player p) {
        if (!mybullet) {
            Rectangle playerHitbox = new Rectangle(p.getX() - Player.SIZE / 2, p.getY() - Player.SIZE / 2,
                    Player.SIZE, Player.SIZE);
            Rectangle bulletHitbox = new Rectangle(getX() - radius, getY() - radius,
                    radius * 2, radius * 2);
            return playerHitbox.intersects(bulletHitbox);
        }
        return false;
    }


    @Override
    // EFFECTS: returns Bullet as Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("mybullet", mybullet);
        json.put("xpos", xpos);
        json.put("ypos", ypos);
        json.put("xdir", xdirection);
        json.put("ydir", ydirection);
        json.put("size", size);
        return json;
    }
}
