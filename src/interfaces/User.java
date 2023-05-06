package interfaces;

import controller.ClientHandler;
import model.RegisteredUsers;

import java.io.ObjectOutputStream;

public interface User {


    String getUsername();

    void userHandler(Object object, ObjectOutputStream outputStream);

}
