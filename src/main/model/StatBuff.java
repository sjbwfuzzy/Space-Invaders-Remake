package model;

import java.util.List;

// a buff that increases player stats, having targets and amounts
public class StatBuff extends Buff {
    public StatBuff(String name, List<String> targets, List<Integer> amounts) {
        super(name, targets, amounts);
    }
}

