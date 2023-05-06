package model.messages;

import model.user.SessionUser;

import java.util.UUID;

public interface Message {

    String getTimeSent();
    SessionUser getSender();
    UUID getChatRoomID();
    void messageHandler();

}
