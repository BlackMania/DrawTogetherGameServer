package com.gamelogic.logic;

import java.sql.Timestamp;

public class ChatMessage {
    private Timestamp messageTime;
    private String content;
    private Player player;

    public ChatMessage(Timestamp messageTime, String content, Player player) {
        this.messageTime = messageTime;
        this.content = content;
        this.player = player;
    }

    //region Getters and setters
    public Timestamp getMessageTime() {
        return messageTime;
    }

    public String getContent() {
        return content;
    }

    public String getPlayerNickname() {
        return player.getNickname();
    }
    //endregion
}
