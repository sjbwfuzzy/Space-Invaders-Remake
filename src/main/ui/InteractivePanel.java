package ui;

import model.Buff;
import model.Game;
import model.Inventory;
import model.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// A panel of Buffs, can remove by clicking on them.
public class InteractivePanel extends JPanel {
    private Game game;
    private List<JButton> buffButtons;
    private List<JButton> weaponButtons;

    // EFFECTS: sets game to g
    public InteractivePanel(Game g) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 30));
        game = g;
        setupBuffButtons();
        add(Box.createVerticalStrut(40));
        setupWeaponButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets up buff buttons and adds listeners to them
    private void setupBuffButtons() {
        buffButtons = new ArrayList<>();
        add(new JLabel("Buffs: "));
        for (int counter = 0; counter < Inventory.MAX_BUFFS; counter++) {
            JButton jb = new JButton("Empty");
            buffButtons.add(jb);
            add(jb);
            jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!Objects.equals(jb.getText(), "Empty")) {
                        Inventory i = game.getInventory();
                        Buff buffToBeRemoved = i.getBuff(jb.getText());
                        game.getPlayer().removeStats(buffToBeRemoved);
                        jb.setText("Empty");
                    }
                }
            });
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up weapon buttons and adds listeners to them
    private void setupWeaponButtons() {
        weaponButtons = new ArrayList<>();
        add(new JLabel("Weapons: "));
        for (int counter = 0; counter < Inventory.MAX_WEAPONS; counter++) {
            JButton jb = new JButton("Empty");
            weaponButtons.add(jb);
            add(jb);
            jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!Objects.equals(jb.getText(), "Empty")) {
                        Inventory i = game.getInventory();
                        Weapon weaponToBeRemoved = i.getWeapon(jb.getText());
                        i.removeWeapon(weaponToBeRemoved);
                        jb.setText("Empty");
                    }
                }
            });
        }
    }

    // EFFECTS: updates JButtons
    public void update() {
        updateBuffs();
        updateWeapons();
    }

    // MODIFIES: this
    // EFFECTS: updates Weapon JButtons
    private void updateWeapons() {
        ArrayList<Weapon> weapons = game.getInventory().getWeapons();
        int numWeapons = weapons.size();
        if (numWeapons > numUsedWeaponButtons()) {
            for (JButton jb : weaponButtons) {
                if (Objects.equals(jb.getText(), "Empty")) {
                    jb.setText(weapons.get(numWeapons - 1).getName());
                    break;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates Buff JButtons
    private void updateBuffs() {
        ArrayList<Buff> buffs = game.getInventory().getBuffs();
        int numBuffs = buffs.size();
        if (numBuffs > numUsedBuffButtons()) {
            for (JButton jb : buffButtons) {
                if (Objects.equals(jb.getText(), "Empty")) {
                    jb.setText(buffs.get(numBuffs - 1).getName());
                    break;
                }
            }
        }
    }

    // EFFECTS: returns number of used buff JButtons
    private int numUsedBuffButtons() {
        int counter = 0;
        for (JButton jb : buffButtons) {
            if (!Objects.equals(jb.getText(), "Empty")) {
                counter += 1;
            }
        }
        return counter;
    }

    // EFFECTS: returns number of used weapon JButtons
    private int numUsedWeaponButtons() {
        int counter = 0;
        for (JButton jb : weaponButtons) {
            if (!Objects.equals(jb.getText(), "Empty")) {
                counter += 1;
            }
        }
        return counter;
    }

}
