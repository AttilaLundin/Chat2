package application.graphics;

import application.Client;
import sharedresources.ChatRoom;
import sharedresources.ImageMessage;
import sharedresources.TextMessage;
import sharedresources.User;
import sharedresources.interfaces.Message;
import sharedresources.requests.FetchMessages;
import sharedresources.requests.SendMessage;
import sharedresources.requests.FetchChatRoom;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Chat extends JFrame {
    private ScheduledExecutorService scheduler;
    private JPanel rootPanel;
    private JButton gitHubButton;
    private JButton homeScreenButton;
    private JTextField textMessageField;
    private JButton sendButton;
    private JList messagesList;
    private JButton refreshButton;
    private DefaultListModel<Message> messageListModel;
    private ChatRoom displayedChatroom;
    private final Client client;
    private final User user;

    public Chat(Client client, ChatRoom displayedChatroom, User user){
        this.client = client;
        this.displayedChatroom = displayedChatroom;
        this.user = user;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeDragAndDrop();
        initializeButtons();
        initializeMessagesList();
        updateTimer();
        setVisible(true);
    }


    public void initializeMessagesList(){
        messageListModel = new DefaultListModel<>();
        messagesList.setModel(messageListModel);
        messagesList.setCellRenderer(new MessageListCellRenderer());
    }

    public void updateMessageList(){
        messageListModel.clear();
        List<Message> messages = displayedChatroom.getMessages();
        for(Message m : messages){
            messageListModel.addElement(m);
        }
    }

    public void updateTimer(){
        scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Runnable(){
                @Override
                public void run(){
                    List<Message> messages = client.getMessages(new FetchMessages(displayedChatroom.getChatRoomID()));
                    displayedChatroom.addMessagList(messages);
                    updateMessageList();
                }
            }, 2, 3, TimeUnit.SECONDS);
    }


    public void initializeButtons(){

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Message> messages = client.getMessages(new FetchMessages(displayedChatroom.getChatRoomID()));
                displayedChatroom.addMessagList(messages);
                updateMessageList();
            }
        });

        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(new SendMessage(displayedChatroom.getChatRoomID(), new TextMessage.TextMessageBuilder().text(textMessageField.getText()).sender(user).build()));
            }
        });

        homeScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Dashboard dashboard = new Dashboard(client);
                scheduler.close();
            }
        });

        gitHubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "https://www.youtube.com/watch?v=eBxTEoseZak";
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
                            if(o instanceof File imageFile){
                                BufferedImage bufferedImage = ImageIO.read(imageFile);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                Avkommentera detta om vi vill ha support för mer än jpg, typ gif osv.
                                ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
                                String format = ImageIO.getImageReaders(imageInputStream).next().getFormatName();

                                ImageIO.write(bufferedImage, format, byteArrayOutputStream);
                                System.out.println(displayedChatroom.getChatRoomID());
                                client.sendMessage(new SendMessage(displayedChatroom.getChatRoomID(), new ImageMessage.ImageMessageBuilder().image(byteArrayOutputStream.toByteArray()).sender(user).build()));
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
