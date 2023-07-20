package model;

// a weapon, having name, fire rate, bullet type
public class Weapon {
    String name;
    int firerate;
    Bullet bullettype;

    // EFFECTS: creates a weapon with name, firerate, and a bullettype
    public Weapon(String name, int firerate, Bullet bullettype) {
        this.name = name;
        this.firerate = firerate;
        this.bullettype = bullettype;
    }

    public String getName() {
        return name;
    }

    public int getFirerate() {
        return firerate;
    }

    public void setFirerate(int firerate) {
        this.firerate = firerate;
    }

    public Bullet getBullettype() {
        return bullettype;
    }
}
