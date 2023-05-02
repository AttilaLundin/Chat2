import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Message implements Serializable {
    private String text;
    private transient BufferedImage image;
    private String time;
    private User sender;

    public Message(String text, BufferedImage image, User sender){
        this.text= text;
        this.image = image;
        time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= sender;
    }

    public String getText(){
        return text;
    }


}
