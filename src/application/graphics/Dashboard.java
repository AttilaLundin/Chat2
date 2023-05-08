package application.graphics;

import application.Client;
import sharedresources.ChatRoom;
import sharedresources.SessionUser;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame{
    private JPanel rootPanel;
    private JPanel sidePanel;
    private JButton dashboardButton;
    private JButton githubButton;
    private JButton contactsButton;
    private JButton messageButton;
    private JPanel mainPanel;
    private JScrollPane chatRoomScrollPane;
    private JPanel chatRoomPanel;
    private JLabel displayNameLabel;
    private SessionUser sessionUser;
    private Client client;

    public Dashboard(Client client){

        this.client = client;
        sessionUser = client.getSessionUser();
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeMainPanel();
        setDisplayName();
        chatRoomPanel.setLayout(new BorderLayout());
        initChatRoomDisplay();
        setVisible(true);

    }

    public void initChatRoomDisplay(){

        List<ChatRoom> chatRooms = client.getChatRooms();
        System.out.println(chatRooms.size());
        if(chatRooms.isEmpty()) return;
        String[] items = new String[chatRooms.size()];
        for(int i = 0; i < chatRooms.size(); i++){
            List<SessionUser> usersInRoom = chatRooms.get(i).getUsersInChatRoom();
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = 0; j < usersInRoom.size(); j++){
                stringBuilder.append(usersInRoom.get(j).getDisplayName());
                if(j < usersInRoom.size() - 1)stringBuilder.append(", ");
            }
            items[i] = stringBuilder.toString();

        }

        JList<String> list = new JList<>(items);

        chatRoomScrollPane = new JScrollPane(list);
        chatRoomScrollPane.setBackground(new Color(26,26,36));
        chatRoomPanel.add(chatRoomScrollPane, BorderLayout.CENTER);

    }

    private void setDisplayName(){
        displayNameLabel.setText("Welcome " + sessionUser.getDisplayName() + ", have a capybara day!");
    }


    private void initializeMainPanel(){
        mainPanel.setDropTarget(new DropTarget(mainPanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dtde.rejectDrop();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    if(!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) throw new Exception();
                    List <File> images = new ArrayList<>();
                    Object object = transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    if(object instanceof List<?> list){
                        for(Object o : list){
                            if(o instanceof File image) images.add(image);

                        }
                    }

                    ArrayList<String> filePaths = new ArrayList<>();
                    for (File i : images) {
                        filePaths.add(i.getAbsolutePath());
                        System.out.println("File path: " + i.getAbsolutePath());
                    }
                    client.sendMessage(filePaths.get(0));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

/*
    public class ChatRoomListModel extends AbstractListModel<String> {
        private final List<ChatRoom> chatRooms;

        public ChatRoomListModel(List<ChatRoom> chatRooms) {
            this.chatRooms = chatRooms;
        }

        @Override
        public int getSize() {
            return chatRooms.size();
        }

        @Override
        public String getElementAt(int index) {
            ChatRoom chatRoom = chatRooms.get(index);
            List<SessionUser> users = chatRoom.getUsersInChatRoom();
            StringBuilder userDisplayNames = new StringBuilder();
            for (SessionUser user : users) {
                userDisplayNames.append(user.getDisplayname()).append(", ");
            }
            return "ChatRoom " + (index + 1) + ": " + userDisplayNames.toString();
        }
    }

    public class ChatRoomListCellRenderer extends JLabel implements ListCellRenderer<String> {
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);

            return this;
        }
    }

    public class ChatRoomList extends JList<String> implements MouseListener{
        public ChatRoomList(List<ChatRoom> chatRooms) {
            super(new ChatRoomListModel(chatRooms));
            setCellRenderer(new ChatRoomListCellRenderer());
            addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int selectedIndex = getSelectedIndex();
                        if (selectedIndex != -1) {
                            // Handle the chat room selection, e.g., display the chat room view
                            System.out.println("Chat room " + (selectedIndex + 1) + " selected");
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // Double-click
                int selectedIndex = getSelectedIndex();
                if (selectedIndex != -1) {
                    // Handle the chat room selection, e.g., display the chat room view
                    System.out.println("Chat room " + (selectedIndex + 1) + " selected");
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

 */

}
