package com.gamelogic.logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LobbyCollectionTests {

    private LobbyCollection collection = LobbyCollection.getInstance();
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;
    private Player player7;

    // Tests needs to be runned all, cause LobbyCollection is Singleton

    @Before
    public void setup() {
        collection.createLobby("550e8400-e29b-41d4-a716-446655440000", "First Lobby");
        player1 = new Player("550e8400-e29b-41d4-a716-446655440000", "testuser");
        player2 = new Player("550e8400-e29b-41d4-a716-44665544222", "testuser2");
        player3 = new Player("550e8400-e29b-41d4-a716-4444", "testuser3");
        player4 = new Player("550e8400-e29b-41d4-a716-6345", "testuser4");
        player5 = new Player("550e8400-e29b-41d4-a716-45645", "testuser5");
        player6 = new Player("550e8400-e29b-41d4-a716-457334", "testuser6");
        player7 = new Player("550e8400-e29b-41d4-a716-2312", "testuser7");
    }

    @Test
    public void stage1_joinLobbyTest() {

        Assert.assertTrue(collection.joinLobby(player1, collection.getLobbies().get(0).getLobbyId()));
    }

    @Test
    public void stage2_joinLobbyWhenAlreadyInALobby()
    {

        Assert.assertFalse(collection.joinLobby(player1, collection.getLobbies().get(0).getLobbyId()));
    }

    @Test
    public void stage3_createLobbyWhenAlreadyInLobby()
    {
        Assert.assertFalse(collection.createLobby(player1.getClientid(), collection.getLobbies().get(0).getLobbyId()));
    }

    @Test
    public void stage4_joinLobbyWhenFull(){
        collection.joinLobby(player1, collection.getLobbies().get(0).getLobbyId());
        collection.joinLobby(player2, collection.getLobbies().get(0).getLobbyId());
        collection.joinLobby(player3, collection.getLobbies().get(0).getLobbyId());
        collection.joinLobby(player4, collection.getLobbies().get(0).getLobbyId());
        collection.joinLobby(player5, collection.getLobbies().get(0).getLobbyId());
        collection.joinLobby(player6, collection.getLobbies().get(0).getLobbyId());

        Assert.assertFalse(collection.joinLobby(player6, collection.getLobbies().get(0).getLobbyId()));
    }

    @Test
    public void stage5_leaveLobby() {
        Assert.assertTrue(collection.leaveLobby(player6.getClientid()));
    }

    @Test
    public void stage6_leaveLobbyWhenNotInLobby()
    {
        Assert.assertFalse(collection.leaveLobby(player6.getClientid()));
    }

    @Test
    public void stage7_lobbyGetsRemovedWhenNobodyIsInThereAnymore() {
        collection.leaveLobby(player1.getClientid());
        collection.leaveLobby(player2.getClientid());
        collection.leaveLobby(player3.getClientid());
        collection.leaveLobby(player4.getClientid());
        collection.leaveLobby(player5.getClientid());
        collection.leaveLobby(player6.getClientid());

        Assert.assertEquals(0, collection.getLobbies().size());
    }

    @Test
    public void stage8_leaveNonExistingLobby() {
        Assert.assertFalse( collection.leaveLobby(player1.getClientid()));
    }
}
