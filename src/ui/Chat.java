package ui;

import application.Client;
import common.ChatRoom;
import common.ImageMessage;
import common.TextMessage;
import common.RegisteredUser;
import common.Message;
import common.requests.FetchMessages;
import common.requests.SendMessage;

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
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A JFrame class that provides a chat interface for users to send and receive messages.
 * It allows users to send text messages, and drag-and-drop files (currently only images) into the chat.
 * The interface includes features such as a refresh button to fetch latest messages, and a send button to send messages.
 * The chat interface also includes a link to a YouTube video and a button to go back to the home screen.
 */
public class Chat extends JFrame {
    private ScheduledExecutorService scheduler;
    private JPanel rootPanel;
    private JButton gitHubButton;
    private JButton homeScreenButton;
    private JTextField textMessageField;
    private JButton sendButton;
    private JList messagesList;
    private JButton refreshButton;
    private JLabel chatNameLabel;
    private DefaultListModel<Message> messageListModel;
    private ChatRoom displayedChatroom;
    private final Client client;
    private final RegisteredUser user;

    /**
     * Constructs a new Chat instance with a given client, chat room, and user.
     * This also sets up the chat interface and starts the message update timer.
     *
     * @param client the client that is used to send and fetch messages
     * @param displayedChatroom the chat room that this chat instance is associated with
     * @param user the user who is currently using this chat interface
     */
    public Chat(Client client, ChatRoom displayedChatroom, RegisteredUser user){
        this.client = client;
        this.displayedChatroom = displayedChatroom;
        this.chatNameLabel.setText("Chatting in Room: " + displayedChatroom.getChatRoomName());
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
        getRootPane().setDefaultButton(sendButton);
        setVisible(true);
    }

    /**
     * Initializes the JList that displays the messages.
     */
    public void initializeMessagesList(){
        messageListModel = new DefaultListModel<>();
        messagesList.setModel(messageListModel);
        messagesList.setCellRenderer(new MessageListCellRenderer());
    }

    /**
     * Updates the list of messages displayed in the interface by fetching the latest messages from the chat room.
     */
    public void updateMessageList(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                messageListModel.clear();
                List<Message> messages = displayedChatroom.getMessages();
                for(Message m : messages){
                    messageListModel.addElement(m);
                }
            }
        });
    }


    /**
     * Starts a timer that updates the list of messages displayed in the interface every two seconds.
     */
    public void updateTimer(){
        scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Runnable(){
                @Override
                public void run(){
                    List<Message> messages = client.getMessages(new FetchMessages(displayedChatroom.getChatRoomID()));
                    displayedChatroom.addMessageList(messages);
                    updateMessageList();
                }
            }, 1, 3, TimeUnit.SECONDS);
    }

    /**
     * Initializes the buttons in the chat interface, including their action listeners.
     */
    public void initializeButtons(){

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Message> messages = client.getMessages(new FetchMessages(displayedChatroom.getChatRoomID()));
                displayedChatroom.addMessageList(messages);
                updateMessageList();
            }
        });

        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(new SendMessage(displayedChatroom.getChatRoomID(), new TextMessage.TextMessageBuilder().text(textMessageField.getText()).sender(user).build()));
                textMessageField.setText("");
            }
        });

        homeScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard(client);
                scheduler.close();
                dispose();
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

    /**
     * Initializes the drag-and-drop feature for the chat interface.
     * This allows users to drag and drop files (only images) into the chat to send them as messages.
     */
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
                                ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
                                String format = ImageIO.getImageReaders(imageInputStream).next().getFormatName();

                                ImageIO.write(bufferedImage, format, byteArrayOutputStream);
                                client.sendMessage(new SendMessage(displayedChatroom.getChatRoomID(), new ImageMessage.ImageMessageBuilder().image(byteArrayOutputStream.toByteArray()).sender(user).build()));
                            }
                        }
                    }
                } catch (UnsupportedFlavorException | IOException | NoSuchElementException e){
                    JOptionPane.showMessageDialog(null, "Come on bro, dont break my program", "That's not an image you flippin twit", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }));
    }
}
