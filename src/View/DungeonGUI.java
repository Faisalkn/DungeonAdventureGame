package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class DungeonGUI extends JFrame {
    /*** This is instance field of start menu item. */
    private JMenuItem saveGame;
    /*** This is instance field of exit menu item. */
    private JMenuItem exit;
    /*** This is instance field of reset menu item. */
    private JMenuItem reset;
    /*** This is instance field of about menu item. */
    private JMenuItem about;
    /*** This is instance field of rules menu item. */
    private JMenuItem rules;
    /*** This is instance field of shortcuts menu item. */
    private JMenuItem shortcuts;

    private DungeonPanel myDungeonPanel;

    public DungeonGUI(){
        setTitle("DungeonAdventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        myDungeonPanel = new DungeonPanel();
        add(myDungeonPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setupGUI();
    }
    private void setupGUI() {
        JMenuBar menuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        game.setMnemonic(KeyEvent.VK_G);        // on Mac using ctrl + option + keyboard
        saveGame = new JMenuItem("Save Game");
        //reset = new JMenuItem("Reset");
        exit = new JMenuItem("Exit");
        // start.setEnabled(false);

        /*** This is the message when the user click Exit on the menu. */
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(DungeonGUI.this,
                        "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        saveGame.setAccelerator(KeyStroke.getKeyStroke("control S"));
        //reset.setAccelerator(KeyStroke.getKeyStroke("control X"));
        exit.setAccelerator(KeyStroke.getKeyStroke("control E"));
        game.add(saveGame);
        //game.add(reset);
        game.add(exit);

        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        about = new JMenuItem("About");
        rules = new JMenuItem("Rules");
        shortcuts = new JMenuItem("Shortcuts");

        /** This is the message include my name and the version of Java that I used. */
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DungeonGUI.this,
                        "Pham Nguyen, Ali Mohamed, Faisal Nur\nVersion 1.0\nJava 8 ");
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DungeonGUI.this,
                        "The rules of the Dungeon Adventure game: " +
                                "\nThis is an adventure game where a hero is randomly placed within a dungeon, which is randomly generated." +
                                "\nThe adventurer needs to find the four Pillars of OO (Abstraction, Encapsulation, Inheritance, and Polymorphism)" +
                                " and take them to the exit to win the game." +
                                "\nSome features of the dungeon will prove a hindrance to the adventurer's task (pits), " +
                                " while some will prove helpful (healing and vision potions)\n"
                );
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myDungeonPanel.getMySaveLoad().save();
                JOptionPane.showMessageDialog(DungeonGUI.this, "Saved the game ");
            }

        });

        /*** This is the message summary about keyboard shortcuts used in this game. */
        shortcuts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DungeonGUI.this, """
                        Keyboard Shortcuts:\s
                        Movement: Arrow Keys (Up, Down, Left, Right)
                        Open Inventory: Enter
                        Use Vision Potion: V
                        Use Health Potion: H
                        Close Inventory/Battle Window: Mouse Click
                        Start Game: Mouse Click on 'NEW GAME'
                        Load Game: Mouse Click on 'LOAD GAME'
                        Quit Game: Mouse Click on 'QUIT GAME'
                        Select Character: Mouse Click on Checkbox
                        Select Difficulty: Mouse Click on Checkbox
                        Enter Username: Type in the Username Box
                        Confirm Selection: Mouse Click on 'DONE'""");
            }
        });
        about.setAccelerator(KeyStroke.getKeyStroke("control A"));
        rules.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        shortcuts.setAccelerator(KeyStroke.getKeyStroke("control F"));
        help.add(about);
        help.add(rules);
        help.add(shortcuts);

        // menuBar
        menuBar.add(game);
        menuBar.add(help);
        setJMenuBar(menuBar);
    }

}