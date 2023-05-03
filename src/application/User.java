import java.io.Serializable;
import java.util.*;
public class User implements Serializable {
    private String UserName;
    private String displayName;
    private String PassWord;

    public User(String Username, String displayName, String PassWord, List<UUID> chatRoomIDs){
        this.UserName= Username;
        this.displayName = displayName;
        this.PassWord = PassWord;
        this.chatRoomIDs = chatRoomIDs;
    }

    public User(){
        this.UserName= "Username";
        this.displayName = "displayName";
        this.PassWord = "PassWord";

    }

    public String getUserName(){return UserName;}

    public String getDisplayName() {
        return displayName;
    }
}
