package common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents an image message in a chat application. Each image message is associated with
 * a sender (RegisteredUser) and the image is stored in byte array format.
 *
 * @author Shazhaib Kazmi
 */
public class ImageMessage implements Message, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final byte[] imageBytes;

    private final RegisteredUser sender;

    /**
     * Constructs an ImageMessage with the specified builder.
     *
     * @param imageMessageBuilder the builder to construct an image message
     */
    private ImageMessage(ImageMessageBuilder imageMessageBuilder){
        this.imageBytes = Objects.requireNonNull(imageMessageBuilder.imageBytes);
        this.sender= Objects.requireNonNull(imageMessageBuilder.sender);
    }

    /**
     * This class provides a way to build an ImageMessage.
     */
    public static class ImageMessageBuilder implements Serializable{
        @Serial
        private static final long serialVersionUID = 1L;
        private byte[] imageBytes;
        private RegisteredUser sender;

        /**
         * Sets the image bytes for the ImageMessage to be built.
         *
         * @param imageBytes the bytes of the image
         * @return this builder
         */
        public ImageMessageBuilder image (byte[] imageBytes){
            this.imageBytes = imageBytes;
            return this;
        }

        /**
         * Sets the sender for the ImageMessage to be built.
         *
         * @param sender the sender of the image message
         * @return this builder
         */
        public ImageMessageBuilder sender(RegisteredUser sender) {
            this.sender = sender;
            return this;
        }

        /**
         * Returns a new ImageMessage built from this builder.
         *
         * @return a new ImageMessage
         */
        public ImageMessage build(){
            return new ImageMessage(this);
        }
    }

    /**
     * Returns the image contained in the ImageMessage as a BufferedImage.
     *
     * @return the image as a BufferedImage
     */
    public BufferedImage getImage(){
        BufferedImage bufferedImage = null;
        try{
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        }catch (IOException e){
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * Returns the sender of the ImageMessage.
     *
     * @return the sender
     */
    @Override
    public RegisteredUser getSender() {
        return sender;
    }

}
