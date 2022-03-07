package com.company;

public class Player {

    private String playerName;
    private int score, subtotal, numTurns;

    /*****************************************************************
     * Constructor sets player name to param n and remaining
     *  values to 0
     ****************************************************************/
    public Player(String n){
        playerName = n;
        score = 0;
        subtotal = 0;
        numTurns = 0;
    }

    /*****************************************************************
     * method returns value for player name
     ****************************************************************/
    public String getName() {
        return playerName;
    }

    /*****************************************************************
     * method returns value for player score
     ****************************************************************/
    public int getScore() {
        return score;
    }

    /*****************************************************************
     * method returns value for player subtotal
     ****************************************************************/
    public int getSubtotal() {
        return subtotal;
    }

    /*****************************************************************
     * method returns value for player number of turns
     ****************************************************************/
    public int getTurns() {
        return numTurns;
    }

    /*****************************************************************
     * method sets player name to given value n
     ****************************************************************/
    public void setName(String n) {
        playerName = n;
    }

    /*****************************************************************
     * method sets player score to value of s
     ****************************************************************/
    public void setScore(int s) {
        score = s;
    }

    /*****************************************************************
     * method sets player subtotal to value of s
     ****************************************************************/
    public void setSubtotal(int s) {
        subtotal += s;
    }

    /*****************************************************************
     * method sets player turns to value of t
     ****************************************************************/
    public void setTurns(int t) {
        numTurns = t;
    }

    /*****************************************************************
     * method sets subtotal to given subtotal plus amount
     ****************************************************************/
    public void addToSubtotal(int amt){
        subtotal += amt;
    }

    /*****************************************************************
     * method increases player score by value of subtotal,
     * sets subtotal to 0, increments player turns
     ****************************************************************/
    public void updateScore(){
        setScore(subtotal);
        subtotal = 0;
        numTurns++;
    }

    /*****************************************************************
     * method sets score, subtotal, and turns to 0
     ****************************************************************/
    public void newGame(){
        score = 0;
        subtotal = 0;
        numTurns = 0;
    }

    public static void main(String args[]){
        Player player = new Player("Jaiden");
        //GVdie[] dice;


    }
}
