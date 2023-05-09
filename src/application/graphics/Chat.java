package application.graphics;

import application.Client;
import sharedresources.ChatRoom;
import sharedresources.ImageMessage;
import sharedresources.TextMessage;
import sharedresources.User;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Chat extends JFrame {

    private JPanel rootPanel;
    private JLabel ChatRoomNameLabel;
    private JButton gitHubButton;
    private JButton messageButton;
    private JTextField textMessageField;
    private JButton sendButton;
    private JList messagesList;

    private Message msgToSend;
    private sharedresources.ChatRoom displayedChatroom;
    private Client client;
    private ChatRoom chatRoom;
    private User user;

    public Chat(Client client, ChatRoom chatRoom){
        this.client = client;
        this.chatRoom = chatRoom;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeDragAndDrop();
        initializeDragAndDrop();
        setVisible(true);
        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = textMessageField.getText();
                TextMessage msgToSend = new TextMessage.TextMessageBuilder().text(msg).sender(user).build();
                client.sendMessage(new SendMessageRequest(displayedChatroom, msgToSend));


            }
        });

        gitHubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "https://www.youtube.com/watch?v=kz_lzEhyryY";
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try{
                        desktop.browse(new URI(url));
                    }catch (IOException | URISyntaxException i){
                        i.printStackTrace();
                    }
                }
            }
        });

    }

//    public void setDisplayName(){
//        .setText("Welcome to the ChatRoom" + user);
//    }
    private void initializeDragAndDrop(){
        rootPanel.setDropTarget(new DropTarget(rootPanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
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
