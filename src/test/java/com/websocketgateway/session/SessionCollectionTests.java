package com.websocketgateway.session;


import org.junit.Assert;
import org.junit.Test;

import javax.websocket.Session;

public class SessionCollectionTests {

    @Test
    public void stage1_testAddingClientSessionToSessionCollection() {
        SessionCollection collection = SessionCollection.getInstance();
        String clientid = "550e8400-e29b-41d4-a716-446655440000";
        Session session = null;

        collection.addSession(session, clientid);

        Assert.assertEquals(1, collection.getCount());
    }

    @Test
    public void stage2_removeClientSessionByClientID()
    {
        SessionCollection collection = SessionCollection.getInstance();

        collection.removeSessionByClientId("550e8400-e29b-41d4-a716-446655440000");

        Assert.assertEquals(0, collection.getCount());
    }

    @Test
    public void stage3_removeSessionBySession()
    {
        SessionCollection collection = SessionCollection.getInstance();
        String clientid = "550e8400-e29b-41d4-a716-446655440000";
        Session session = null;

        collection.addSession(session, clientid);

        Assert.assertEquals(1, collection.getCount());

        collection.removeSessionBySesion(session);

        Assert.assertEquals(0, collection.getCount());
    }

    @Test
    public void stage4_getSessionByClientID()
    {
        SessionCollection collection = SessionCollection.getInstance();
        String clientid = "550e8400-e29b-41d4-a716-446655440000";
        Session session = null;

        collection.addSession(session, clientid);

        Session result = collection.getSessionByClientId(clientid);

        Assert.assertEquals(null, result);
        collection.removeSessionBySesion(null);
    }

    @Test
    public void stage5_getSessionByClientId()
    {
        SessionCollection collection = SessionCollection.getInstance();
        String clientid = "550e8400-e29b-41d4-a716-446655440000";
        Session session = null;

        collection.addSession(session, clientid);

        Session result = collection.getSessionByClientId(clientid);

        Assert.assertEquals(null, result);
        collection.removeSessionBySesion(null);
    }

    @Test
    public void stage6_getClientIDBySession()
    {
        SessionCollection collection = SessionCollection.getInstance();
        String clientid = "550e8400-e29b-41d4-a716-446655440000";
        Session session = null;

        collection.addSession(session, clientid);

        String result = collection.getClientIdBySession(session);

        Assert.assertEquals("550e8400-e29b-41d4-a716-446655440000", result);
        collection.removeSessionBySesion(null);
    }
}
