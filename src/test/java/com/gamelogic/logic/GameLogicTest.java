package com.gamelogic.logic;

import org.junit.Assert;
import org.junit.Test;

public class GameLogicTest {

    private Lobby lobby;

    public void setup()
    {
        lobby = new Lobby("Test Lobby");
        lobby.addPlayer(new Player("550e8400-e29b-41d4-a716-446655440000", "testuser"));
        lobby.addPlayer(new Player("550e8400-e29b-41d4-a716-446111111111", "testuser2"));
    }

    @Test
    public void startGame()
    {
        setup();
        lobby.getGame().startGame();

        Assert.assertTrue(lobby.getGame().isStarted());
        Assert.assertNotNull(lobby.getGame().getDrawingPlayer());
    }

    @Test
    public void startGameAndRoundAndCheckIfRoundIsRunning()
    {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");


        Assert.assertTrue(lobby.getGame().isStarted());
        Assert.assertNotNull(lobby.getGame().getDrawingPlayer());
        Assert.assertTrue(lobby.getGame().isRoundRunning());
    }

    @Test
    public void startGameAndRoundAndStartRoundAndCheckWrongWordGuess()
    {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");

        Assert.assertFalse(lobby.getGame().checkWordGuess("birdo"));
    }

    @Test
    public void startGameAndRoundAndStartRoundAndCheckCorrectWordGuess()
    {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");

        Assert.assertTrue(lobby.getGame().checkWordGuess("bird"));
    }

    @Test
    public void startGameAndRoundAndStartRoundAndCheckNullWordGuess()
    {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound(null);

        Assert.assertFalse(lobby.getGame().checkWordGuess("bird"));
    }

    @Test
    public void startGameAndRoundAndStartRoundAndCheckIfPlayerIsDrawer()
    {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");

        Player drawer = lobby.getGame().getDrawingPlayer();

        Assert.assertTrue(lobby.getGame().checkIfClientIsDrawer(drawer.getClientid()));
    }

    @Test
    public void startGameAndRoundAndStartRoundAndCheckIfPlayerIsDrawerWhenHeIsNot()
    {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");

        Player player = null;

        for(Player p : lobby.getPlayers())
        {
            if(!p.isDrawer())
            {
                player = p;
            }
        }

        Assert.assertFalse(lobby.getGame().checkIfClientIsDrawer(player.getClientid()));
    }

    @Test
    public void startGameStartRoundAndEndRound() {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");
        lobby.getGame().roundEnd();

        Assert.assertEquals(2, lobby.getGame().getRounds());
        Assert.assertFalse(lobby.getGame().isRoundRunning());
        Assert.assertNull(lobby.getGame().getDrawing());

        for(Player p : lobby.getPlayers())
        {
            Assert.assertFalse(p.getGuessedWord());
        }
    }

    @Test
    public void startGameAndRoundAndCheckIfPlayersGuessedWord() {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");

        Player player = null;

        for(Player p : lobby.getPlayers())
        {
            if(!p.isDrawer())
            {
                player = p;
            }
        }
        Assert.assertFalse(lobby.getGame().checkAllPlayersGuessedWord());
    }

    @Test
    public void startGameAndRoundAndGuessCorrectWord() {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");

        Player player = null;

        for(Player p : lobby.getPlayers())
        {
            if(!p.isDrawer())
            {
                player = p;
            }
        }
        player.setGuessedWord(true);
        Assert.assertTrue(lobby.getGame().checkAllPlayersGuessedWord());
    }

    @Test
    public void testEndOfGame() {
        setup();
        lobby.getGame().startGame();
        lobby.getGame().startRound("bird");
        lobby.getGame().roundEnd();
        lobby.getGame().endGame();


        Assert.assertFalse(lobby.getGame().isStarted());
        Assert.assertFalse(lobby.getGame().isRoundRunning());
        Assert.assertEquals(3, lobby.getGame().getRounds());
    }

    @Test
    public void startGameAndRoundAndGuessCorrectWordWhenNoDrawingIsSet() {
        setup();
        lobby.getGame().startGame();


        Assert.assertFalse(lobby.getGame().checkWordGuess(null));
    }
}
