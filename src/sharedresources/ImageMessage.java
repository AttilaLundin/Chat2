package sharedresources;

import sharedresources.interfaces.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;

public class ImageMessage implements Message, Serializable {
//    todo:: buffered image skickas inte när vi serialiserar.. måste göra om till byte arrat eller ngt
    private final byte[] imageBytes;
    private final String timeSent;
    private final User sender;

    private ImageMessage(ImageMessageBuilder imageMessageBuilder){
        this.imageBytes = Objects.requireNonNull(imageMessageBuilder.imageBytes);
        this.timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= Objects.requireNonNull(imageMessageBuilder.sender);
    }

    public static class ImageMessageBuilder implements Serializable{
        private byte[] imageBytes;
        private User sender;
        private UUID chatRoomID;

        public ImageMessageBuilder image (byte[] imageBytes){
            this.imageBytes = imageBytes;
            return this;
        }

        public ImageMessageBuilder sender(User sender) {
            this.sender = sender;
            return this;
        }

        public ImageMessage build(){
            return new ImageMessage(this);
        }
    }



    public BufferedImage getImage(){
        BufferedImage bufferedImage = null;
        try{
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        }catch (IOException e){
            e.printStackTrace();
        }
        return bufferedImage;
    }

    @Override
    public String getTimeSent() {
        return timeSent;
    }

    @Override
    public User getSender() {
        return sender;
    }




}
