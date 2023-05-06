package interfaces;

import model.SessionUser;

import java.util.Objects;
import java.util.UUID;

public interface Message {

    String getTimeSent();
    SessionUser getSender();
    UUID getChatRoomID();
    void messageHandler(Object object);

}
