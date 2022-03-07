package com.company;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.Random;
/*******************************************
 * To test the Farkle game  - phase 3
 *
 * @author - Ana Posada
 * @version  1.0.0 October 2021
 ******************************************/
public class MyFarkleTestPhase3 {
    /** object of the PokerDice class */
    private Farkle game;

    /** the game dice */
    private GVdie [] dice;

    /** Constants  */
    private final static int WINNING_SCORE = 10000;
    private final static int NUM_DICE = 6;
    private final static int NUM_PLAYERS = 3;

    /** constants for scoring categories */
    private final static int STRAIGHT = 3000;
    private final static int THREE_PAIRS = 1500;
    private final static int ONES = 100;
    private final static int THREE_ONES = 1000;
    private final static int FIVES = 50;
    private final static int FOUR_OF_A_KIND = 1300;
    private final static int FIVE_OF_A_KIND = 2000;
    private final static int SIX_OF_A_KIND = 3000;

    /******************************************************
     * Instantiate - PokerGame
     * Called before every test case method.
     *****************************************************/
    @Before
    public void setUp()
    {
        game = new Farkle();
        dice = game.getDice();
    }

    /******************************************************
     * Test initial values of the constructor
     *****************************************************/
    @Test
    public void testConstructor()
    {
        assertEquals("Constructor: there should be 6 dice",
                6, dice.length);

        assertEquals("Constructor: player score should be zero",
                0, game.getActivePlayer().getScore());

        assertEquals("Constructor: player subtotal should be zero",
                0, game.getActivePlayer().getSubtotal());

        assertEquals("Constructor: player turns should be zero",
                0, game.getActivePlayer().getTurns());

        for (GVdie d : dice) {
            assertFalse("resetGame(): the dice should not be selected", d.isSelected());
            assertFalse("resetGame(): the dice should not be scored", d.isScored());
        }
    }

    /******************************************************
     * Test resetGame
     *****************************************************/
    @Test
    public void testResetGame()
    {
        game.getActivePlayer().setSubtotal(200);
        game.getActivePlayer().setScore(500);
        game.getActivePlayer().setTurns(200);
        game.selectDie(3);
        game.resetGame();
        assertEquals("Active player subtotal should be 0", 0, game.getActivePlayer().getSubtotal());
        assertEquals("Active player score should be 0",0, game.getActivePlayer().getScore());
        assertEquals("Active player number turns should be 0",0, game.getActivePlayer().getTurns());

        for (GVdie d : dice) {
            assertFalse("resetGame(): the dice should not be held", d.isSelected());
            assertFalse("resetGame(): the dice should not be scored", d.isScored());
        }
    }

    /******************************************************
     * Test game over or not over
     *****************************************************/
    @Test
    public void testGameOver()
    {
        game.getActivePlayer().setScore(WINNING_SCORE);

        assertTrue("gameOver(): the game should be over when score is: " + WINNING_SCORE
                ,  game.gameOver());
    }

    /******************************************************
     * Test setAllDice
     *****************************************************/
    @Test
    public void testSetAllDice()
    {
        int [] values = generateRandomVals();
        game.setAllDice(values);
        dice = game.getDice();

        for (int i = 0 ; i < NUM_DICE ; i++)
            assertTrue("dice not set correctly: values entered: " + valuesToString (values) +
                    "game dice values:" + game.diceToString(),  values[i] == dice[i].getValue());
    }

    /******************************************************
     * Test setSelectDie
     *****************************************************/
    @Test
    public void testSelectDie()
    {
        for (int i = 0 ; i < NUM_DICE ; i++) {
            game.selectDie (i + 1);

            assertTrue("die not selected: " + (i + 1) ,
                    dice[i].isSelected());
        }
    }

    /******************************************************
     * Test passDice
     *****************************************************/
    @Test
    public void testPassDice()
    {
        Player lastPlayer = game.getActivePlayer();
        lastPlayer.setSubtotal (1000);
        game.passDice();
        assertEquals("Last player number turns should be 1",1, lastPlayer.getTurns());
        assertEquals("Last player subtotal should be 0",0, lastPlayer.getSubtotal());
        assertEquals("Last player score should have increased",1000, lastPlayer.getScore());

        for (GVdie d : dice) {
            assertFalse("resetGame(): the dice should not be held", d.isSelected());
            assertFalse("resetGame(): the dice should not be scored", d.isScored());
        }
    }

    /******************************************************
     * Select the dice
     * @param vals - dice to be selected (held)
     *****************************************************/
    private void  selectAllDice (int [] vals) {
        for (int i = 0 ; i < NUM_DICE; i++)
            game.selectDie(i + 1);
    }

    /******************************************************
     * Select the dice that have a particular value
     * @param vals - dice to be selected (held)
     * @param value - only dice with this value will be
     * selected (held)
     *****************************************************/
    private void  selectDice (int [] vals, int value) {
        for (int i = 0 ; i < NUM_DICE; i++)
            if (vals[i] == value)
                game.selectDie(i + 1);
    }

    /******************************************************
     * generate an array of NUM-DICE random numbers
     * between 1 - 6 (inclusive)
     * @return int [] - array of NUM_DICE random numbers
     *****************************************************/
    private int[] generateRandomVals() {
        Random rand = new Random ();
        int [] values = new int [NUM_DICE];
        for (int i = 0; i < values.length; i++)
            values[i] = rand.nextInt(6) + 1;
        return values;
    }

    /******************************************************
     * generate an array of NUM-DICE random numbers
     * between 1 - 6 (inclusive)
     * @param ofAKind - total number of equal values to generate
     * @param val - value to generate
     * @return int [] - array contains ofAKind values
     *****************************************************/
    private int []  genValuesOfAKind(int ofAKind, int val) {
        int [] values = new int [NUM_DICE];
        Random rand = new Random ();
        int count = 0;
        int index = 0;
        int num;

        for (int i = 0 ; i < NUM_DICE; i++)
            values [i] = 0;

        if (ofAKind >= 1 && ofAKind <= 6 && val >= 1 && val <=6){
            // populating array of equal numbers ofAKind at random indexes

            while (count < ofAKind) {
                index = rand.nextInt (NUM_DICE);
                if (values [index] == 0) {
                    values [index] = val;
                    count++;
                }
            }

            // populating the rest values at random
            for (int i = 0 ; i < NUM_DICE; i++)
                if (values [i] == 0) {
                    num = rand.nextInt(6) + 1;
                    while (num == val)
                        num = rand.nextInt(6) + 1;
                    values [i] = num;
                }
        }
        return values;

    }

    /******************************************************
     * generate an array of NUM-DICE random numbers
     * between 1 - 6 (inclusive)
     * @return int[] - generates 3 pairs
     *****************************************************/
    private int []  genValuesForThreePairs() {
        int [] values = new int [NUM_DICE];
        Random rand = new Random ();
        int count = 0;
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;

        int index = 0;
        for (int i = 0 ; i < NUM_DICE; i++)
            values [i] = 0;

        // populating first pair
        num1 = rand.nextInt(6) + 1;
        while (count < 2) {
            index = rand.nextInt (NUM_DICE);
            if (values [index] == 0) {
                values [index] = num1;
                count++;
            }
        }

        // populating second pair
        count = 0;
        num2 = rand.nextInt(6) + 1;
        while (num2 == num1)
            num2 = rand.nextInt(6) + 1;

        while (count < 2) {
            index = rand.nextInt (NUM_DICE);
            if (values [index] == 0) {
                values [index] = num2;
                count++;
            }
        }

        // populating third pair
        count = 0;
        num3 = rand.nextInt(6) + 1;
        while (num3 == num1 || num3 == num2)
            num3 = rand.nextInt(6) + 1;

        while (count < 2) {
            index = rand.nextInt (NUM_DICE);
            if (values [index] == 0) {
                values [index] = num3;
                count++;
            }
        }
        return values;
    }

    /******************************************************
     * generate an array of NUM-DICE random numbers
     * between 1 - 6 (inclusive)
     * @return int[] - generates 1 pair and four_of_a_kind
     *****************************************************/
    private int []  genValuesForOnePairAndFourOfAKind() {
        int [] values = new int [NUM_DICE];
        Random rand = new Random ();
        int count = 0;
        int num1 = 0;
        int num2 = 0;
        int index = 0;
        for (int i = 0 ; i < NUM_DICE; i++)
            values [i] = 0;

        // populating first pair
        num1 = rand.nextInt(6) + 1;
        while (count < 2) {
            index = rand.nextInt (NUM_DICE);
            if (values [index] == 0) {
                values [index] = num1;
                count++;
            }
        }

        count = 0;
        num2 = rand.nextInt(6) + 1;
        while (num1 == num2)
            num2 = rand.nextInt(6) + 1;

        // populating  four of a kind
        while (count < 4) {
            index = rand.nextInt (NUM_DICE);
            if (values [index] == 0) {
                values [index] = num2;
                count++;
            }
        }
        return values;
    }

    /******************************************************
     * generate an array of NUM-DICE all values 1-6
     * @return int []- straight
     *****************************************************/
    private int []  genValuesStraight() {
        int [] values = new int [NUM_DICE];
        Random rand = new Random ();
        int count = 0;
        int num = 1;
        int index = 0;
        for (int i = 0 ; i < NUM_DICE; i++)
            values [i] = 0;

        // populating straight - random index position
        while (count < NUM_DICE) {
            index = rand.nextInt (NUM_DICE);
            if (values [index] == 0) {
                values [index] = num;
                num++;
                count++;
            }
        }
        return values;
    }

    /******************************************************
     * tests THREE_OF_A_KIND for numbers 1 - 6
     *****************************************************/
    @Test
    public void testThreeOfAKind()
    {
        int [] values;
        int scoreBefore;

        // three of a kind of any number
        for (int i = 1 ; i <= 6 ; i++) {
            values = genValuesOfAKind(3, i);
            scoreBefore = game.getActivePlayer().getSubtotal();
            game.setAllDice (values);
            selectDice (values, i);
            game.scoreSelectedDice();
            if (i == 1)
                assertEquals (valuesToString (values) + " Three ONES should increment subtotal by " +
                        THREE_ONES ,  scoreBefore + THREE_ONES , game.getActivePlayer().getSubtotal());
            else
                assertEquals (valuesToString (values) + " Three_of_a_Kind should increment subtotal by " +
                        "die value * 100" ,  scoreBefore + (i * 100) , game.getActivePlayer().getSubtotal());
        }
    }

    /******************************************************
     * tests STRAIGHT (one die of each value 1 - 6)
     *****************************************************/
    @Test
    public void testStraight()
    {
        int [] values = genValuesStraight();
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " Straight should increment subtotal by " +
                STRAIGHT ,  scoreBefore + STRAIGHT , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * tests THREE_PAIRS
     *****************************************************/
    @Test
    public void testThreePairs()
    {
        int [] values = genValuesForThreePairs();
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " Three PAIRS should increment subtotal by " +
                THREE_PAIRS ,  scoreBefore + THREE_PAIRS , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * tests THREE_PAIRS (one pair and  four_of_A_kind)
     *****************************************************/
    @Test
    public void testOnePairAndFourOfAKind()
    {
        int [] values = genValuesForOnePairAndFourOfAKind();
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " One PAIR and four_of_a_kind should increment subtotal by " +
                THREE_PAIRS ,  scoreBefore + THREE_PAIRS , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * tests FOUR_OF_A_KIND
     *****************************************************/
    @Test
    public void testFourOfAKind()
    {
        int [] values;
        int scoreBefore;

        // four of a kind of any number
        for (int i = 1 ; i <= 6 ; i++) {
            values = genValuesOfAKind(4, i);
            scoreBefore = game.getActivePlayer().getSubtotal();
            game.setAllDice (values);
            selectDice (values, i);
            game.scoreSelectedDice();
            assertEquals (valuesToString (values) + " Four_of_a_Kind should increment subtotal by " +
                    FOUR_OF_A_KIND ,  scoreBefore + FOUR_OF_A_KIND , game.getActivePlayer().getSubtotal());
        }
    }

    /******************************************************
     * tests FIVE_OF_A_KIND
     *****************************************************/
    @Test
    public void testFiveOfAKind()
    {
        int [] values;
        int scoreBefore;

        // five of a kind of any number
        for (int i = 1 ; i <= 6 ; i++) {
            values = genValuesOfAKind(5, i);
            scoreBefore = game.getActivePlayer().getSubtotal();
            game.setAllDice (values);
            selectDice (values, i);
            game.scoreSelectedDice();
            assertEquals (valuesToString (values) + " Five_of_a_Kind should increment subtotal by " +
                    FIVE_OF_A_KIND ,  scoreBefore + FIVE_OF_A_KIND , game.getActivePlayer().getSubtotal());
        }
    }

    /******************************************************
     * tests SIX_OF_A_KIND
     *****************************************************/
    @Test
    public void testSixOfAKind()
    {
        int [] values;
        int scoreBefore;

        // six of a kind of any number
        for (int i = 1 ; i <= 6 ; i++) {
            values = genValuesOfAKind(6, i);
            scoreBefore = game.getActivePlayer().getSubtotal();
            game.setAllDice (values);
            selectDice (values, i);
            game.scoreSelectedDice();
            assertEquals (valuesToString (values) + " Six_of_a_Kind should increment subtotal by " +
                    SIX_OF_A_KIND ,  scoreBefore + SIX_OF_A_KIND , game.getActivePlayer().getSubtotal());
        }
    }

    /******************************************************
     * tests ONES (One or two values)
     *****************************************************/
    @Test
    public void testOnes()
    {
        int [] values;
        int scoreBefore;

        // ones (1 or 2)
        for (int i = 1 ; i <= 2 ; i++) {
            values = genValuesOfAKind(i, 1);
            scoreBefore = game.getActivePlayer().getSubtotal();
            game.setAllDice (values);
            selectDice (values, 1);
            game.scoreSelectedDice();
            assertEquals (valuesToString (values) + i + " ONE's should increment subtotal by " +
                    ONES * i ,  scoreBefore + ONES * i , game.getActivePlayer().getSubtotal());
        }
    }

    /******************************************************
     * tests FIVES (One or two values)
     *****************************************************/
    @Test
    public void testFives()
    {
        int [] values;
        int scoreBefore;

        // fives (1 or 2)
        for (int i = 1 ; i <= 2 ; i++) {
            values = genValuesOfAKind(i, 5);
            game.setAllDice (values);
            scoreBefore = game.getActivePlayer().getSubtotal();
            selectDice (values, 5);
            game.scoreSelectedDice();
            assertEquals (valuesToString (values) + i + " FIVE's should increment subtotal by " +
                    FIVES * i ,  scoreBefore + FIVES * i , game.getActivePlayer().getSubtotal());
        }
    }

    /******************************************************
     * tests ones and fives with four_of_a_kind
     *****************************************************/
    @Test
    public void testOnesAndFives1()
    {
        int [] values = {1,3,5,3,3,3};
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " Four_of_A_kind, one 1, one 5 should increment subtotal by ",
                scoreBefore + FOUR_OF_A_KIND + ONES + FIVES , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * tests ones and fives with four_of_a_kind
     *****************************************************/
    @Test
    public void testOnesAndFives2()
    {
        int [] values = {1,3,5,1,3,3};
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " Three_of_A_kind, two 1's, one 5 should increment subtotal by ",
                scoreBefore + 300 + ONES * 2 + FIVES , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * tests ones and fives with four_of_a_kind
     *****************************************************/
    @Test
    public void testOkOneAndFiveOfAKind()
    {
        int [] values = {1,2,2,2,2,2};
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " Five_of_A_kind, one 1 should increment subtotal by ",
                scoreBefore + FIVE_OF_A_KIND + ONES  , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * tests five and five_of_a_kind
     *****************************************************/
    @Test
    public void testFiveAndFiveOfAKind()
    {
        int [] values = {6,5,6,6,6,6};
        int scoreBefore = game.getActivePlayer().getSubtotal();
        game.setAllDice (values);
        selectAllDice (values);
        game.scoreSelectedDice();
        assertEquals (valuesToString (values) + " Five_of_A_kind, one 5 should increment subtotal by ",
                scoreBefore + FIVE_OF_A_KIND + FIVES  , game.getActivePlayer().getSubtotal());
    }

    /******************************************************
     * Converts an array to a String
     * @param values - array of values
     * @return String -
     ***************************************************/
    private String valuesToString (int [] values){
        String s = "[";
        s += values [0];
        for (int i = 1 ; i <= values.length - 1 ; i++)
            s += "," + values [i];
        s += "]";
        return s;
    }

    /******************************************************
     * tests booleans okToRoll, okToPass, initialRoll
     *****************************************************/
    @Test
    public void testGameBooleans()
    {
        assertTrue("FAIL: Game should begin as OK to roll", game.okToRoll());
        assertFalse("FAIL: Game should begin as not OK to pass", game.okToPass());

        game.rollDice();
        assertTrue("FAIL: Should be OK to pass after initial roll", game.okToPass());

        // start second turn
        game.passDice();
        assertTrue("FAIL: Turn should begin as OK to roll", game.okToRoll());
        assertFalse("FAIL: Turn should begin as not OK to pass", game.okToPass());

        // initial roll
        game.rollDice();
        assertTrue("FAIL: Should be OK to pass after initial roll", game.okToPass());

    }

    /******************************************************
     * tests playerFarkled - no possibility of scoring
     *****************************************************/
    @Test
    public void testPlayerFarkled()
    {
        // testing player farkled - no possibility of scoring
        int [] values = {6,4,3,6,2,2};
        game.setAllDice (values);
        assertTrue("FAIL: Player should have farkled", game.playerFarkled());
    }

    /******************************************************
     * tests player did not farkled - >=3 multiples
     *****************************************************/
    @Test
    public void testPlayerNotFarkledOfAKind()
    {
        // testing player did not farkle - multiples >=3
        Random rand = new Random ();
        int [] values;
        for (int i = 3 ; i < NUM_DICE ; i++) {
            int val = rand.nextInt(6) + 1;
            values = genValuesOfAKind(i, val);
            game.setAllDice (values);
            // no dice scored
            assertFalse("FAIL: Player should have not farkled", game.playerFarkled());
        }
    }

    /******************************************************
     * tests player did not farkled - straight
     *****************************************************/
    @Test
    public void testPlayerNotFarkledStraight()
    {
        // testing player did not farkle - genValuesStraight
        int [] values = genValuesStraight();
        game.setAllDice (values);
        // no dice scored
        assertFalse("FAIL: Player should have not farkled", game.playerFarkled());
    }

    /******************************************************
     * tests player did not farkled - three pairs
     *****************************************************/
    @Test
    public void testPlayerNotFarkledThreePairs()
    {
        // testing player did not farkle - genValuesStraight
        int [] values = genValuesForThreePairs();
        game.setAllDice (values);
        // no dice scored
        assertFalse("FAIL: Player should have not farkled", game.playerFarkled());
    }

    /******************************************************
     * tests player did not farkled - three pairs
     * (4_of_a_kind and a pair)
     *****************************************************/
    @Test
    public void testPlayerNotFarkledFourAndAPair()
    {
        // testing player did not farkle - genValuesStraight
        int [] values = genValuesForOnePairAndFourOfAKind();
        game.setAllDice (values);
        // no dice scored
        assertFalse("FAIL: Player should have not farkled", game.playerFarkled());
    }

    /******************************************************
     * tests player did not farkled - ones
     *****************************************************/
    @Test
    public void testPlayerNotFarkledOnes()
    {
        // testing player did not farkle - Ones
        int [] values;
        for (int i = 1 ; i <= 2 ; i++) {
            values = genValuesOfAKind(i, 1);
            game.setAllDice (values);
            // no dice scored
            assertFalse("FAIL: Player should have not farkled", game.playerFarkled());
        }
    }

    /******************************************************
     * tests player did not farkled - fives
     *****************************************************/
    @Test
    public void testPlayerNotFarkledFives()
    {
        // testing player did not farkle - Ones
        int [] values;
        for (int i = 1 ; i <= 2 ; i++) {
            values = genValuesOfAKind(i, 5);
            game.setAllDice (values);
            // no dice scored
            assertFalse("FAIL: Player should have not farkled", game.playerFarkled());
        }
    }

    /******************************************************
     * tests multiples players - setActivePlayer
     *****************************************************/
    @Test
    public void testSetActivePlayer()
    {

        for (int i = 0 ; i < 3 ; i++) {
            game.setActivePlayer(i + 1);
            game.getActivePlayer().setName("P" + (i + 1));
            assertEquals("FAIL: Multiple players ", game.getActivePlayer().getName(), "P" + (i + 1));
        }
    }
}