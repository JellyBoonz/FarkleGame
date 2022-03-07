package com.company;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Farkle {
    private static final int WINNING_SCORE = 10000;
    private static final int STRAIGHT = 3000;
    private static final int THREE_PAIRS = 1500;
    private static final int FOUR_OF_A_KIND = 1300;
    private static final int FIVE_OF_A_KIND = 2000;
    private static final int SIX_OF_A_KIND = 3000;
    private static final int THREE_ONES = 1000;
    private static final int THREE_TWOS = 200;
    private static final int THREE_THREES = 300;
    private static final int THREE_FOURS = 400;
    private static final int THREE_FIVES = 500;
    private static final int THREE_SIXES = 600;
    private static final int ONE_ONE = 100;
    private static final int ONE_FIVE = 50;

    private boolean canRoll = true;
    private boolean canPass = false;
    private boolean isFirstRoll = false;

    Player player;
    Player[] players;
    private int[] tally;
    private GVdie[] dice;

    public Farkle() {
        dice = new GVdie[6];
        players = new Player[3];
        String [] names = {"Jaiden Ortiz", "Lex Fridman", "Elon Musk"};

        for(int i = 0; i < players.length; i++){
            players[i] = new Player(names[i]);
        }

        for (int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }
        tally = new int[7];
        player = new Player("Jaiden");
    }

    public void setDice(GVdie[] dice) {
        this.dice = dice;
    }

    public void setTally(int[] tally){
        this.tally = tally;
    }

    public boolean getIsFirstRoll(){
        return isFirstRoll;
    }

    public Player getActivePlayer() {
        return player;
    }

    /*****************************************************************
     * method returns true if player score is greater than or equal to
     * 10000 else false
     ****************************************************************/
    public boolean gameOver() {
        if (player.getScore() >= WINNING_SCORE) {
            return true;
        } else {
            return false;
        }
    }

    /*****************************************************************
     * method returns array of GVdie objects
     ****************************************************************/
    public GVdie[] getDice() {
        //return array of gvdie objects
        return dice;
    }

    /*****************************************************************
     * method sets a tally in first loop to 0 and tallies occurrences
     * of selected die in the second
     ****************************************************************/
     public void tallySelectedDice() {
        for (int i = 1; i < tally.length; i++) {
            tally[i] = 0;
        }

        for (int i = 0; i < dice.length; i++) {
            if (dice[i].isSelected()) {
                tally[dice[i].getValue()]++;
            }
        }
    }


    /*****************************************************************
     * method sets a tally in first loop to 0 and tallies occurrences
     * of unscored die in the second.
     ****************************************************************/
    private void tallyUnscoredDice() {
        for (int i = 1; i < tally.length; i++) {
            tally[i] = 0;
        }
        for (int i = 0; i < dice.length; i++) {
            if (!(dice[i].isScored())) {
                tally[dice[i].getValue()]++;
            }
        }
    }

    /*****************************************************************
     * method returns true if at least one die was selected
     * else false
     ****************************************************************/
    public boolean wereDiceSelected() {
        for (int i = 0; i < dice.length; i++) {
            if (dice[i].isSelected()) {
                return true;
            }
        }
        return false;
    }

    /*****************************************************************
     * method returns true if player rolled a straight
     * else false
     ****************************************************************/
    private boolean hasStraight() {
        for (int i = 1; i < tally.length; i++) {
            //tally for each die has to be 1
            if (tally[i] == 1) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /*****************************************************************
     * method returns true if player rolled three pairs or
     * four of a kind and a pair
     * else false
     ****************************************************************/
    private boolean hasThreePairs() {
        int pairCount = 0, fourCount = 0;

        for (int i = 1; i < tally.length; i++) {
            /*if three pairs or four of a kind
            tally values must be either
            4 or 2 or 0*/
            if (tally[i] == 2) {
                pairCount++;
            } else if (tally[i] == 4) {
                fourCount++;
            }
        }

        if (pairCount == 3) {
            return true;
        } else if (fourCount == 1 && pairCount == 1) {
            return true;
        }
        return false;
    }

    /*****************************************************************
     * method returns true if any die was rolled numberTimes
     * else false
     ****************************************************************/
    private boolean hasMultiples(int numberTimes) {
        for (int i = 1; i < tally.length; i++) {
            if (tally[i] == numberTimes) {
                return true;
            }
        }
        return false;
    }

    /*****************************************************************
     * method sets selected dice to scored
     ****************************************************************/
    public void diceSelectedToScored() {
        for (int i = 0; i < dice.length; i++) {
            if (dice[i].isSelected()) {
                dice[i].setScored(true);
            }
        }
    }

    /*****************************************************************
     * method prepares for next turn by setting scored and selected
     * to false and setting each die blank
     ****************************************************************/
    private void nextTurn() {
        isFirstRoll = true;
        canRoll = true;
        canPass = false;

        for (int i = 0; i < dice.length; i++) {
            dice[i].setSelected(false);
            dice[i].setScored(false);
            dice[i].setBlank();
        }
    }

    /*****************************************************************
     * method prepares for new game by resetting player object and
     * dice object
     ****************************************************************/
    public void resetGame() {
        for(int i = 0; i < players.length; i++){
            players[i].newGame();
        }
        setActivePlayer(1);
        nextTurn();
    }

    /*****************************************************************
     * method tallies selected dice and checks each against scoring
     * category while updating player score, then sets selected dice
     * to scored
     ****************************************************************/
    public void scoreSelectedDice() {
        int flag1 = 0;
        tallySelectedDice();
        for (int i = 0; i < tally.length; i++) {
            System.out.print(tally[i] + " ");
        }
        System.out.print("\n");
        if (hasStraight()) {
            player.setSubtotal(STRAIGHT);
            flag1++;
            System.out.println(player.getSubtotal() + " 1");
        }
        if (hasMultiples(6)) {
            player.setSubtotal(SIX_OF_A_KIND);
            System.out.println(player.getSubtotal() + " 2");
        }
        if (hasMultiples(5)) {
            player.setSubtotal(FIVE_OF_A_KIND);
            System.out.println(player.getSubtotal() + " 3");
        }
        if (hasThreePairs()) {
            player.setSubtotal(THREE_PAIRS);
            flag1++;
            System.out.println(player.getSubtotal() + " 4");
        }
        if (hasMultiples(4) && flag1 == 0) {
            player.setSubtotal(FOUR_OF_A_KIND);
            System.out.println(player.getSubtotal() + " 5");
        }
        if (tally[1] == 3) {
            player.setSubtotal(THREE_ONES);
            System.out.println(player.getSubtotal() + " 6");
        }
        if (tally[6] == 3) {
            player.setSubtotal(THREE_SIXES);
            System.out.println(player.getSubtotal() + " 7");
        }
        if (tally[5] == 3) {
            player.setSubtotal(THREE_FIVES);
            System.out.println(player.getSubtotal() + " 8");
        }
        if (tally[4] == 3) {
            player.setSubtotal(THREE_FOURS);
            System.out.println(player.getSubtotal() + " 9");
        }
        if (tally[3] == 3) {
            player.setSubtotal(THREE_THREES);
            System.out.println(player.getSubtotal() + " 10");
        }
        if (tally[2] == 3) {
            player.setSubtotal(THREE_TWOS);
            System.out.println(player.getSubtotal() + " 11");
        }
        if ((tally[1] == 1 || tally[1] == 2) && flag1 == 0) {
            if (tally[1] == 2) {
                player.setSubtotal(2 * ONE_ONE);
            } else {
                player.setSubtotal(ONE_ONE);
            }

            System.out.println(player.getSubtotal() + " 13");
        }
        if ((tally[5] == 2 || tally[5] == 1) && flag1 == 0) {
            if (tally[5] == 1) {
                player.setSubtotal(ONE_FIVE);
            } else {
                player.setSubtotal(2 * ONE_FIVE);
            }
            System.out.println(player.getSubtotal() + " 14");
        }
        diceSelectedToScored();

    }

    /*****************************************************************
     * method scores selected dice, if all dice are scored, starts
     * next turn using nextTurn, else rolls all un-scored dice
     ****************************************************************/
    public void rollDice() {
        int flag = 0;

        if(isFirstRoll || wereDiceSelected()) {
            scoreSelectedDice();
            for (int i = 0; i < dice.length; i++) {
                if (!dice[i].isScored()) {
                    dice[i].roll();
                }
                else{
                    flag++;
                }
            }
        }

        if (flag == 6) {
            nextTurn();
        }

        isFirstRoll = false;
        canPass = true;
        if(playerFarkled()){
            player.setSubtotal((player.getSubtotal()) * -1);
            canRoll = false;
        }
    }

    /*****************************************************************
     * method scores selected dice, sets players score, and
     * prepares next turn
     ****************************************************************/
    public void passDice() {
        scoreSelectedDice();
        player.updateScore();
        nextTurn();
    }

    /*****************************************************************
     * method assists in testing by manually setting all dice values
     * to entered values in second loop and sets each value of 0 to
     * 1 in first loop
     ****************************************************************/
    public void setAllDice(int[] values) {
        for (int i = 0; i < dice.length; i++) {
            if (values[i] == 0) {
                values[i] = 1;
            }
            while (dice[i].getValue() != values[i]) {
                dice[i].roll();
            }
        }
    }

    /*****************************************************************
     * method takes input id and sets the value at index id - 1 in dice
     * array to selected
     ****************************************************************/
    public void selectDie(int id) {
        dice[id - 1].setSelected(true);
    }

    /*****************************************************************
     * method takes input id and sets the value at index id - 1 in dice
     * array to selected
     ****************************************************************/
    public String diceToString() {
        String diceStr = "";
        for (int i = 0; i < dice.length; i++) {
            diceStr += dice[i].getValue();
            if (i == dice.length - 1) {
                break;
            }
            diceStr += ", ";
        }
        return diceStr;
    }

    /*****************************************************************
     * method checks if any unscored dice CAN be scored
     * else returns true
     ****************************************************************/
    public boolean playerFarkled() {
        int flag1 = 0;
        tallyUnscoredDice();

        if (hasStraight()) {
            flag1++;
            return false;
        }
        if (hasMultiples(6)) {
            return false;
        }
        if (hasMultiples(5)) {
            return false;
        }
        if (hasThreePairs()) {
            flag1++;
            return false;
        }
        if (hasMultiples(4) && flag1 == 0) {
            return false;
        }
        if (tally[1] == 3) {
            return false;
        }
        if (tally[6] == 3) {
            return false;
        }
        if (tally[5] == 3) {
            return false;
        }
        if (tally[4] == 3) {
            return false;
        }
        if (tally[3] == 3) {
            return false;
        }
        if (tally[2] == 3) {
            return false;
        }
        if ((tally[1] == 1 || tally[1] == 2) && flag1 == 0) {
            return false;
        }
        if (tally[5] == 2 || tally[5] == 1 && flag1 == 0) {
            return false;
        }
        return true;
    }

    /*****************************************************************
     * method returns true if player can roll
     *  else false
     ****************************************************************/
    public boolean okToRoll(){
        if(canRoll){
            return true;
        }
        return false;
    }

    /*****************************************************************
     * method returns true if player can pass
     *  else false
     ****************************************************************/
    public boolean okToPass(){
        if(canPass){
            return true;
        }
        return false;
    }

    /*****************************************************************
     * method sets current active player to player with entered playerNum
     * value. Else, displays an Error and call method again
     ****************************************************************/
    public void setActivePlayer(int playerNum){
        if(playerNum >= 1 && playerNum <= 3){
            player = players[playerNum - 1];
        }
        else{
            Scanner scnr = new Scanner(System.in);
            System.out.println("Error: playerNum must be between 1 and 3 (inclusive)");
            setActivePlayer(scnr.nextInt());
        }
    }





}


