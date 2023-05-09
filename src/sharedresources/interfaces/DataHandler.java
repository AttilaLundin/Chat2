package sharedresources.interfaces;

import java.io.ObjectOutputStream;

public interface DataHandler {
    void dataHandler(Object userStorage, Object chatroomStorage, ObjectOutputStream outputStream);
}
