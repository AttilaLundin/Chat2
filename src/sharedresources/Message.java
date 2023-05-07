package sharedresources;

import sharedresources.SessionUser;

import java.util.UUID;

public interface Message {

    String getTimeSent();
    SessionUser getSender();
    UUID getChatRoomID();
    void messageHandler(Object object);

}
