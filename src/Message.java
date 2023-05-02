import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

public class Message {
    private String text;
    private String time;
    private BufferedImage image;
    private User sender;

    public Message(String text, BufferedImage image, User sender){
        this.text= text;
        this.image = image;
        this.sender= sender;
        time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }


}
