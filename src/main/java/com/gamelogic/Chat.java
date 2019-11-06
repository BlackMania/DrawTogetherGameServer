package com.gamelogic;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<ChatMessage> chatMessages;

    public Chat() {
        this.chatMessages = new ArrayList<ChatMessage>();
    }

    public void addChatMessage(ChatMessage message)
    {
        chatMessages.add(message);
    }
}
