package sharedresources;

import sharedresources.interfaces.Message;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;

public class ImageMessage implements Message, Serializable {
//    todo:: buffered image skickas inte när vi serialiserar.. måste göra om till byte arrat eller ngt
    private final transient BufferedImage image;
    private final String timeSent;
    private final User sender;

    private ImageMessage(ImageMessageBuilder imageMessageBuilder){
        this.image= Objects.requireNonNull(imageMessageBuilder.image);
        this.timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= Objects.requireNonNull(imageMessageBuilder.sender);
    }

    public static class ImageMessageBuilder implements Serializable{
        private BufferedImage image;
        private User sender;
        private UUID chatRoomID;

        public ImageMessageBuilder image (BufferedImage image){
            this.image = image;
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
        return image;
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
