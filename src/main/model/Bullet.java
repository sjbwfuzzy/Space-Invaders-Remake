package model;

import org.json.JSONObject;
import persistence.Writable;

// a bullet, having damage, speed, penetration, radius, mybullet
public class Bullet implements Writable {
    private int damage;
    private int speed;
    private int penetration;
    private int radius;
    private boolean mybullet; //true if bullet is player's bullet, false otherwise

    // initializes a bullet
    public Bullet(int d, int s, int p, int r, boolean mb) {
        damage = d;
        speed = s;
        penetration = p;
        radius = r;
        mybullet = mb;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPenetration() {
        return penetration;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isMybullet() {
        return mybullet;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("damage", damage);
        json.put("speed", speed);
        json.put("penetration", penetration);
        json.put("radius", radius);
        json.put("mybullet", mybullet);
        return json;
    }
}
