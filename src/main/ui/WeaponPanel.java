//package ui;
//
//import model.Weapon;
//import model.Game;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.List;
//
//public class WeaponPanel extends JPanel {
//    private Game game;
//    private List<JButton> buttons;
//
//    // EFFECTS: sets game to g
//    public WeaponPanel(Game g) {
//        game = g;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: creates a JButton for every weapon in the inventory, and sets an action listener to remove the weapon
//    //          from the inventory when pressed
//    public void update() {
//        List<Weapon> weapons = game.getInventory().getWeapons();
//        buttons = new ArrayList<>();
//
//        for (int counter = 0; counter < weapons.size(); counter++) {
//            JButton jb = new JButton(weapons.get(counter).getName());
//            buttons.add(jb);
//            add(jb);
//            jb.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    int index = buttons.indexOf(jb);
//                    weapons.remove(index);
//                    buttons.remove(jb);
//                    remove(jb);
//                }
//            });
//        }
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: changes visibility of this panel when w key is pressed
//    public void handleKey(int keyCode) {
//        if (keyCode == KeyEvent.VK_W) {
//            setVisible(!isVisible());
//        }
//    }
//}
