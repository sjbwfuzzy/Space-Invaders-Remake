package model;

// a weapon, having name, fire rate, bullet type
public class Weapon {
    String name;
    int fireRate;
    Bullet bulletType;

    // EFFECTS: creates a weapon with name, fire rate, and a bullet type
    public Weapon(String name, int fr, Bullet bt) {
        this.name = name;
        this.fireRate = fr;
        this.bulletType = bt;
    }

    public String getName() {
        return name;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public Bullet getBulletType() {
        return bulletType;
    }
}
