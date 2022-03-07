package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
/***********************************
 * GUI for the Farkle Game
 *
 * @author Ana Posada
 * @version 1.0.0 - October 2021
 **************************************/
public class FarkleGUI extends JFrame implements ActionListener{
    /** game */
    private Farkle game;

    /** current Player */
    private Player currentPlayer;

    /** buttons and labels - Array of 3 each*/
    private JLabel [] playerTurns;
    private JLabel [] playerSubtotals;
    private JLabel [] playerScores;

    /** JButtons */
    private JButton rollButton, passButton;
    private ButtonGroup group;

    /** Radio Buttons */
    private JRadioButton[] playerNames;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenuItem quitItem;
    private JMenuItem newGameItem;

    /****************************************************************
     * Main method start the game
     ****************************************************************/
    public static void main(String args[]){
        FarkleGUI gui = new FarkleGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Ana's Farkle Game");
        gui.pack();
        gui.setVisible(true);
    }

    /****************************************************************
     * Create all elements and display within the GUI
     ****************************************************************/
    public FarkleGUI(){
        // create the game object
        game = new Farkle();

        setupGUI();
        setupMenu();
        newGame ();
    }

    /****************************************************************
     * sets up the GUI elements
     ****************************************************************/
    private void setupGUI () {
        // create the layout
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        setBackground(Color.lightGray);

        // position the dice
        GVdie[] dice = game.getDice();

        position.gridx = 0;
        position.gridy = 0;
        for (int i = 0 ; i < dice.length; i++) {
            add(dice[i], position);
            position.gridx++;
        }

        // instantiate and position the buttons
        rollButton = new JButton ("Roll");
        passButton = new JButton ("Pass");

        rollButton.addActionListener(this);
        passButton.addActionListener(this);

        position.gridx = 1;
        position.gridy = 2;
        add(rollButton, position);

        position.gridx = 2;
        add(passButton, position);

        // instantiate the group, the JRadioButtons and JLabels
        group = new ButtonGroup();

        playerNames = new JRadioButton [3];
        playerScores = new JLabel [3];
        playerTurns = new JLabel [3];
        playerSubtotals = new JLabel [3];

        position.gridx = 3;

        for (int i = 0; i < playerNames.length ; i++ ) {
            position.gridy = 1;
            playerNames[i] = new JRadioButton();
            group.add (playerNames[i]);

            playerNames [i].setEnabled(false);

            playerScores [i] = new JLabel ();
            playerTurns [i] = new JLabel ();
            playerSubtotals [i] = new JLabel ();

            add(playerNames [i], position);
            position.gridy++;

            add(playerTurns [i], position);
            position.gridy++;

            add(playerSubtotals [i], position);
            position.gridy++;

            add(playerScores [i], position);
            position.gridx++;
        }
    }

    /****************************************************************
     * Start a new game
     ****************************************************************/
    private void newGame(){
        game.resetGame();

        for (int i = 0; i < playerNames.length ; i++ ) {
            game.setActivePlayer(i + 1);
            currentPlayer = game.getActivePlayer();
            playerNames[i].setText(currentPlayer.getName());
            playerScores [i].setText("Score: " + currentPlayer.getScore());
            playerSubtotals [i].setText("Sub Total: " + currentPlayer.getSubtotal());
            playerTurns[i].setText("Turns: " + currentPlayer.getTurns());
        }
        playerNames [0].setSelected(true);
        rollButton.setEnabled(true);
        passButton.setEnabled(false);
    }

    /****************************************************************
     *This method called when the rol, pass buttons or a menu item
     *is selected
     *
     *@param event - the JComponent just selected
     ****************************************************************/
    public void actionPerformed(ActionEvent event){

        // player selects menu to quit the game
        if (event.getSource() == quitItem)
            System.exit(1);

        // player selects menu item to start a new game
        if (event.getSource() == newGameItem)
            newGame();

        // player selects roll
        if (event.getSource()  == rollButton){
            game.rollDice();
            refreshData();
        }

        // player selects pass
        if (event.getSource()  == passButton){
            game.passDice();
            refreshData();
            selectNextPlayer();
        }

        //desabling or enabling buttons
        rollButton.setEnabled(game.okToRoll());
        passButton.setEnabled(game.okToPass());

        if (game.gameOver())
            gameOver();
    }

    /****************************************
     * process the game over
     ***************************************/
    private void gameOver () {
        // disable the roll and pass buttons
        rollButton.setEnabled (false);
        passButton.setEnabled (false);

        // display message
        JOptionPane.showMessageDialog(this, currentPlayer.getName() + " wins!");
    }

    /******************************************************
     * refreshes the score, subtotal and turns for the
     * active player
     *******************************************************/
    private void refreshData() {
        // updating the JLabels turns, subtotal and score
        for (int i = 0; i < playerNames.length ; i++ ) {
            if (playerNames [i].isSelected()){
                game.setActivePlayer(i + 1);
                currentPlayer = game.getActivePlayer();
                playerScores [i].setText("Score: " + currentPlayer.getScore());
                playerSubtotals [i].setText("Sub Total: " + currentPlayer.getSubtotal());
                playerTurns[i].setText("Turns: " + currentPlayer.getTurns());
            }
        }
    }

    /**********************************************
     * sets up the JBotton for the next player
     ***********************************************/
    private void selectNextPlayer () {
        boolean foundSelected = false;
        int nextPlayer;
        for (int i = 0; i < playerNames.length && !foundSelected ; i++)
            if (playerNames[i].isSelected()){
                foundSelected = true;
                nextPlayer = i + 1;
                playerNames [i].setSelected(false);
                playerNames [nextPlayer % 3].setSelected(true);
            }
    }

    /**********************************************
     * sets up the menu
     ***********************************************/
    private void setupMenu() {
        fileMenu = new JMenu("File");

        newGameItem = new JMenuItem("New Game");
        quitItem = new JMenuItem("Quit");
        fileMenu.add(newGameItem);

        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        //register the three menu items with this action listener
        quitItem.addActionListener(this);
        newGameItem.addActionListener(this);
    }
}
