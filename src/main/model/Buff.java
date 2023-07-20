package model;

import java.util.List;

// represents a buff with a name, target attributes, and amounts to increase those attributes by
public abstract class Buff {
    protected String name;
    protected List<String> targets;
    protected List<Integer> amounts;

    // EFFECTS: Initializes a buff with name, target attributes, and amounts to increase by
    public Buff(String name, List<String> targets, List<Integer> amounts) {
        this.name = name;
        this.targets = targets;
        this.amounts = amounts;
    }

    public String getName() {
        return name;
    }

    public List<String> getTargets() {
        return targets;
    }

    public List<Integer> getAmounts() {
        return amounts;
    }
}
