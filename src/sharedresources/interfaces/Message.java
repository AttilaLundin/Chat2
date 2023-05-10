package sharedresources.interfaces;

import sharedresources.User;

import java.util.UUID;

public interface Message{

    String getTimeSent();
    User getSender();
}
