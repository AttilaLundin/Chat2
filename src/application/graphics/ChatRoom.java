package application.graphics;

import application.Client;
import sharedresources.ImageMessage;
import sharedresources.interfaces.Message;
import sharedresources.requests.SendMessageRequest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChatRoom extends JFrame {

    private JPanel chatRoomPanel;
    private JLabel ChatRoomNameLabel;
    private JButton gitHubButton;
    private JButton friendsButton;
    private JButton messageButton;
    private JTextField textMessageField;
    private JButton sendButton;
    private JList messagesList;

    private Message msgToSend;
    private sharedresources.ChatRoom displayedChatroom;
    private Client client;

    public ChatRoom(Client client){
        this.client = client;

        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(chatRoomPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeDragAndDrop();
        setDisplayName();
        chatRoomPanel.setLayout(new BorderLayout());
        initChatRoomDisplay();
        setVisible(true);


    }

    public void setDisplayName(){
        ChatRoomNameLabel.setText("Welcome to the ChatRoom" + user);
    }
    private void initializeDragAndDrop(){
        chatRoomPanel.setDropTarget(new DropTarget(chatRoomPanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            //TODO: ändra mainpanel till chattrumspanelen eller den här Jlist
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dtde.rejectDrop();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    if(!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) throw new UnsupportedFlavorException(DataFlavor.javaFileListFlavor);
                    Object object = transferable.getTransferData(DataFlavor.javaFileListFlavor);

                    if(object instanceof List<?> list){
                        for(Object o : list){
                            if(o instanceof File image){
                                BufferedImage bufferedImage = ImageIO.read(image);
                                client.sendMessage(new SendMessageRequest(displayedChatroom, new ImageMessage.ImageMessageBuilder().image(bufferedImage).sender(user).build()));
                            }
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

}
