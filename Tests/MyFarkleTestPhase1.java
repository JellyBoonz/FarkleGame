package com.company;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class MyFarkleTestPhase1.
 *
 * @author  Christian Trefftz
 * @version 1.0.0 - october 2021
 */
public class MyFarkleTestPhase1
{
    /**
     * Default constructor for test class MyFarkleTestPhase1
     */
    public MyFarkleTestPhase1()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testConstructor()
    {
        Player player1 = new Player("Louie");
        assertEquals("Louie", player1.getName());
        assertEquals(0, player1.getScore());
        assertEquals(0, player1.getSubtotal());
        assertEquals(0, player1.getTurns());
    }

    @Test
    public void testSetters()
    {
        Player player1 = new Player("Louie");
        player1.setName("TheLaker");
        assertEquals("TheLaker", player1.getName());
        player1.setScore(1000);
        assertEquals(1000, player1.getScore());
        player1.setSubtotal(200);
        assertEquals(200, player1.getSubtotal());
        player1.setTurns(7);
        assertEquals(7, player1.getTurns());
    }

    @Test
    public void testAddToSubtotal()
    {
        Player player1 = new Player("Louie");
        player1.addToSubtotal(250);
        assertEquals(250, player1.getSubtotal());
        player1.addToSubtotal(150);
        assertEquals(400, player1.getSubtotal());
    }
    @Test
    public void testUpdateScore()
    {
        Player player1 = new Player("Louie");
        player1.addToSubtotal(400);
        player1.updateScore();
        assertEquals(0, player1.getSubtotal());
        assertEquals(400, player1.getScore());
        assertEquals(1, player1.getTurns());
    }
    @Test
    public void testNewGame() {
        Player player1 = new Player("Louie");
        player1.addToSubtotal(400);
        player1.updateScore();
        player1.newGame();
        assertEquals(0, player1.getSubtotal());
        assertEquals(0, player1.getScore());
        assertEquals(0, player1.getTurns());
    }
}
