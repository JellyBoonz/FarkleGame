package com.company;

import org.junit.Test;
import static org.junit.Assert.*;

public class FarkleTest {
    /**
     * Testing nextTurn() Method
     * */
    @Test
    public void testNextTurn(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();

        //Testing nextTurn method
        for(int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
            dice[i].roll();
            dice[i].setSelected(true);
            dice[i].setScored(true);
        }

        //game.nextTurn();

        for(int i = 0; i < dice.length; i++){

            assertFalse("Fail: setSelected method error", dice[i].isSelected());

            assertFalse("Fail: setScored method error", dice[i].isSelected());
        }

        assertTrue("Fail: ok to roll should be set to true", game.okToRoll());
        assertFalse("Fail: ok to pass should be set to false", game.okToPass());
    }

    /**
     * Testing dice to string method
     * by printing string and comparing
     * to diceVals
     */
    @Test
    public void testDiceToString(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();
        int [] diceVals = {1, 2, 3, 4, 5, 6};

        for(int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }

        game.setAllDice(diceVals);

        System.out.println(game.diceToString());
    }

    /**
     * Testing rollDice() method by creating new dice
     * and game objects and comparing boolean values of getIsFirstRoll()
     * and wereDiceSelected()
     */
    @Test
    public void testRollDice(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();

        for(int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);
        game.rollDice();

        for(int i = 0; i < dice.length; i++){
            dice[i].setSelected(true);
            System.out.println(dice[i].isSelected());
        }

        System.out.println(dice);
        assertFalse("Fail: isFirstRoll should be set to false", game.getIsFirstRoll());

        assertTrue("Fail: dice must be selected in order to roll", game.wereDiceSelected());
    }

    /**
     * Testing ok to roll method by calling nextTurn()
     * and comparing boolean value for okToRoll()
     */
    @Test
    public void testOkToRoll(){
        Farkle game = new Farkle();

        //game.nextTurn();

        assertTrue("Fail: ok to roll should be set to true", game.okToRoll());
    }

    /**
     * Testing ok to roll method by calling rollDice()
     * and comparing boolean value for okToPass()
     */
    @Test
    public void testOkToPass(){
        Farkle game = new Farkle();

        game.rollDice();
        assertTrue("Fail: ok to pass should be set to true", game.okToPass());
    }

    /**
     * Testing getDice method to return
     * dice address
     * @return dice
     */
    @Test
    public void testGetDice(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();

        for(int i = 0; i < dice.length; i++){
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);

        assertEquals("Fail: getDice should return address of dice", dice, game.getDice());

    }

    /**
     * Testing setDice method by calling getDice to return
     * dice address
     * @return dice
     */
    @Test
    public void testSetDice(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();

        for(int i = 0; i < dice.length; i++){
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);

        assertEquals("Fail: getDice should return address of dice", dice, game.getDice());
    }

    /**
     * Testing getDice method to return
     * dice address
     * @return dice
     */
    @Test
    public void testTallySelectedDice(){
        Farkle game = new Farkle();
        GVdie [] dice = new GVdie[6];
        int [] tally = new int[7];
        int [] diceVals = {1, 2, 3, 4, 5, 6};

        game.setTally(tally);

        for(int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);
        game.setAllDice(diceVals);

        for(int i = 0; i < dice.length; i++){
            dice[i].setSelected(true);
        }

        game.tallySelectedDice();

        for (int i = 1; i < tally.length; i++) {
            assertEquals("Fail, should tally every dice value once",1, tally[i]);
        }
    }

    /**
     * Testing wereDiceSelected output
     * @returns boolean
     */
    @Test
    public void testWereDiceSelected() {
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();
        int[] diceVals = {1, 2, 3, 4, 5, 6};
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);
        game.setAllDice(diceVals);

        for (int i = 0; i < dice.length; i++) {
            dice[i].setSelected(true);
        }

        assertTrue("Fail: wereDiceSelected() should return true", game.wereDiceSelected());

        for (int i = 0; i < dice.length; i++) {
            dice[i].setSelected(false);
        }

        assertFalse("Fail: wereDiceSelected() should return false", game.wereDiceSelected());
    }

    /**
     * Testing if diceSelectedToScored() sets dice to scored
     * if selected
     * @returns boolean
     */
    @Test
    public void testDiceSelectedToScored(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();
        int[] diceVals = {1, 2, 3, 4, 5, 6};
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);
        game.setAllDice(diceVals);

        for (int i = 0; i < dice.length; i++) {
            dice[i].setSelected(true);
        }

        game.diceSelectedToScored();

        for(int i = 0; i < dice.length; i++) {
            assertTrue("Fail: isScored() should return true", dice[i].isScored());
        }
    }

    /**
     * Testing if selectDie() sets selected to true for given
     * die
     * @returns boolean
     */
    @Test
    public void testSelectDie(){
        GVdie[] dice = new GVdie[6];
        Farkle game = new Farkle();

        for(int i = 0; i < dice.length; i++) {
            dice[i] = new GVdie(100);
        }
        game.setDice(dice);
        game.selectDie(1);

        assertTrue("Fail: selected die should be set to selected", dice[0].isSelected());


    }
}