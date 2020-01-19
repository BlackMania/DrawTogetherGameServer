package com.gamelogic.logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LobbyTests {
    private Lobby lobby;

    @Before
    public void setup()
    {
        lobby = new Lobby("Test Lobby");
        lobby.addPlayer(new Player("550e8400-e29b-41d4-a716-446655440000", "testuser"));
    }

    @Test
    public void addPlayerToLobby()
    {
        lobby.addPlayer(new Player("446655440000", "test"));
        Assert.assertEquals(2, lobby.getPlayers().size());
        lobby.removePlayer("446655440000");
        Assert.assertEquals(1, lobby.getPlayers().size());
    }

    @Test
    public void checkLobbyNotFull()
    {
        Assert.assertTrue(lobby.isNotFull());
    }

    @Test
    public void checkIfPlayerIsInLobby(){
        Assert.assertTrue(lobby.checkIfPlayerIsInHere("550e8400-e29b-41d4-a716-446655440000"));
    }

    @Test
    public void checkIfPlayerIsInLobbyWhichIsNot(){
        Assert.assertFalse(lobby.checkIfPlayerIsInHere("qwasq23-234wer234-1234wer345"));
    }

    @Test
    public void getAllClientIDsFromPlayers(){
        Assert.assertEquals(1, lobby.getAllClientIds().length);
    }
}
