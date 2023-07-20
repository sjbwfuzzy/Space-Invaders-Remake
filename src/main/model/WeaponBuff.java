package model;

import java.util.List;

// a buff that increases weapon stats.
public class WeaponBuff extends Buff {
    public WeaponBuff(String name, List<String> targets, List<Integer> amounts) {
        super(name, targets, amounts);
    }
}
