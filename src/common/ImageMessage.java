package common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ImageMessage implements Message, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final byte[] imageBytes;
    private final RegisteredUser sender;

    private ImageMessage(ImageMessageBuilder imageMessageBuilder){
        this.imageBytes = Objects.requireNonNull(imageMessageBuilder.imageBytes);
        this.sender= Objects.requireNonNull(imageMessageBuilder.sender);
    }

    public static class ImageMessageBuilder implements Serializable{
        @Serial
        private static final long serialVersionUID = 1L;
        private byte[] imageBytes;
        private RegisteredUser sender;

        public ImageMessageBuilder image (byte[] imageBytes){
            this.imageBytes = imageBytes;
            return this;
        }

        public ImageMessageBuilder sender(RegisteredUser sender) {
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
    public RegisteredUser getSender() {
        return sender;
    }

}
