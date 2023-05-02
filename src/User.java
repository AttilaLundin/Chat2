import java.io.Serializable;

public class User implements Serializable {
    private String UserName;
    private String displayName;
    private String PassWord;

    public User(String Username, String displayName,String PassWord ){
        this.UserName= Username;
        this.displayName = displayName;
        this.PassWord = PassWord;

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
