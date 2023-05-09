package sharedresources.requests;

import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GetUsersRequest implements DataHandler, Serializable {
    @Override
    public void dataHandler(Object userStorage, Object chatroomStorage, ObjectOutputStream outputStream) {

    }
}
