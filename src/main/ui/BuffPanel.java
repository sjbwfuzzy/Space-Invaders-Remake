//package ui;
//
//import model.Buff;
//import model.Game;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.List;
//
//// A panel of Buffs, can remove by clicking on them.
//public class BuffPanel extends JPanel {
//    private Game game;
//    private List<JButton> buttons;
//
//    // EFFECTS: sets game to g
//    public BuffPanel(Game g) {
//        game = g;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: creates a JButton for every buff in the inventory, and sets an action listener to remove the buff
//    //          from the inventory, update player stats, and remove the button from this panel when pressed.
//    public void update() {
//        List<Buff> buffs = game.getInventory().getBuffs();
//        buttons = new ArrayList<>();
//        for (int counter = 0; counter < buffs.size(); counter++) {
//            JButton jb = new JButton(buffs.get(counter).getName());
//            buttons.add(jb);
//            add(jb);
//            jb.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    int index = buttons.indexOf(jb);
//                    Buff removedBuff = buffs.remove(index);
//                    game.getPlayer().removeStats(removedBuff);
//                    buttons.remove(jb);
//                    remove(jb);
//                }
//            });
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: changes visibility of this panel when b key is pressed
//    public void handleKey(int keyCode) {
//        if (keyCode == KeyEvent.VK_B) {
//            setVisible(!isVisible());
//        }
//    }
//}
