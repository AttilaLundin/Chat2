package ui;

import application.Client;
import common.ChatRoom;
import common.RegisteredUser;
import common.requests.CreateNewChatRoom;
import common.requests.FetchAllUser;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * A JFrame class that provides a dashboard interface for users to interact with chatrooms and other users.
 * It allows users to select from a list of existing chatrooms, or create a new chatroom with a selected group of users.
 * The interface includes features such as a refresh button to fetch the latest chatrooms and users,
 * and a create button to create a new chatroom. The dashboard interface also includes a link to a YouTube video.
 */
public class Dashboard extends JFrame{
    private static final String GITHUB_URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    private JPanel rootPanel;
    private JPanel sidePanel;
    private JButton gitHubButton;
    private JButton homeScreenButton;
    private JPanel mainPanel;
    private JScrollPane chatRoomScrollPane;
    private JPanel chatRoomPanel;
    private JLabel displayNameLabel;
    private JList<RegisteredUser> userList;
    private DefaultListModel<RegisteredUser> userListModel;
    private List<RegisteredUser> selectedUsernames = new ArrayList<>();
    private ChatRoom selectedChatroom;
    private JButton createCapyHerdButton;
    private JPanel displayBarPanel;
    private JList chatRoomList;
    private DefaultListModel<ChatRoom> chatRoomListModel;
    private JScrollPane userListScrollPane;
    private JButton enterButton;
    private JButton refreshButton;
    private JTextField chatRoomNameTextField;
    private JPanel userPanel;
    private RegisteredUser user;
    private Client client;

    /**
     * Constructs a new Dashboard instance with a given client.
     * This also sets up the dashboard interface and fetches the latest chatrooms and users.
     *
     * @param client the client that is used to fetch chatrooms and users
     */
    public Dashboard(Client client){
        this.client = client;
        user = client.getUser();

        setupWindow();
        initLists();
        initTextField();
        setupEventListeners();
        updateUserList();
        updateChatRoomList();
        setVisible(true);
    }

    /**
     * Sets up the window and functionality for the dashboard interface.
     */
    private void setupWindow(){
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setWelcomeText();
    }

    /**
     * Initializes the lists that display the users and chatrooms.
     */
    public void initLists(){
        userListModel = new DefaultListModel<>();
        userList.setModel(userListModel);
        userList.setCellRenderer(new UsernameListCellRenderer());
        userList.setSelectionModel(new UsernameListSelectionModel());
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        chatRoomListModel = new DefaultListModel<>();
        chatRoomList.setModel(chatRoomListModel);
        chatRoomList.setCellRenderer(new ChatRoomListCellRenderer());
    }

    /**
     * Updates the list of users displayed in the interface by fetching the latest users from the client.
     */
    public void updateUserList(){
        userListModel.clear();
        List<RegisteredUser> usersList = client.getUsersList(new FetchAllUser());
        if(usersList == null) return;
        for(RegisteredUser u : usersList){
            if(u.getUsername().equals(user.getUsername()))continue;
            userListModel.addElement(u);
        }
    }

    /**
     * Updates the list of chatrooms displayed in the interface by fetching the latest chatrooms from the client.
     */
    public void updateChatRoomList(){
        chatRoomListModel.clear();
        List<ChatRoom> chatRoomsList = client.getAllChatRooms();
        for(ChatRoom c : chatRoomsList){
            chatRoomListModel.addElement(c);
        }
    }

    /**
     * Initializes the text field where the user can enter the name of a new chatroom.
     */
    public void initTextField(){
        chatRoomNameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(chatRoomNameTextField.getText().equals("Enter Chatroom Name")) chatRoomNameTextField.setText("");
            }
        });
    }

    /**
     * Sets the welcome text displayed in the dashboard interface.
     */
    private void setWelcomeText(){
        displayNameLabel.setText("Welcome " + user.getUsername() + ", have a capybara day!");
    }

    /**
     * Sets up the event listeners for the dashboard interface, including the listeners for the lists and buttons.
     */
    private void setupEventListeners() {

        //
        userList.addListSelectionListener(this::onUserListValueChanged);

        chatRoomList.addListSelectionListener(this::onChatRoomListValueChanged);

        gitHubButton.addActionListener(this::onGitHubButtonClicked);

        createCapyHerdButton.addActionListener(this::onCreateCapyHerdButtonClicked);

        enterButton.addActionListener(this::onEnterButtonClicked);

        refreshButton.addActionListener(this::onRefreshButtonClicked);
    }


    private void onUserListValueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            selectedUsernames.clear();
            for (int index : userList.getSelectedIndices()) {
                selectedUsernames.add(userListModel.getElementAt(index));
            }
        }
    }

    private void onChatRoomListValueChanged(ListSelectionEvent e){
        if (!e.getValueIsAdjusting()) {
            selectedChatroom = (ChatRoom) chatRoomList.getSelectedValue();
        }
    }

    private void onGitHubButtonClicked(ActionEvent e) {
        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try{
                desktop.browse(new URI(url));
            }catch (IOException | URISyntaxException i){
                i.printStackTrace();
            }
        }
    }

    private void onCreateCapyHerdButtonClicked(ActionEvent e) {
        List<RegisteredUser> selectedUsers = userList.getSelectedValuesList();
        if(selectedUsers == null || selectedUsers.isEmpty()) return;
        selectedUsers.add(user);
        String chatRoomName = chatRoomNameTextField.getText();
        if(chatRoomName.equals("") || chatRoomName.equals("Enter Chatroom Name")){
            JOptionPane.showMessageDialog(null, "Please set chat room name\nTry again!", "Please set a valid chat room name", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ChatRoom chatRoom = client.addChatRoom(new CreateNewChatRoom(chatRoomName, selectedUsers));
        if(chatRoom != null){
            Chat chat = new Chat(client, chatRoom, user);
            dispose();
        }
    }

    private void onEnterButtonClicked(ActionEvent e) {
        if(selectedChatroom == null) return;
        Chat chat = new Chat(client,selectedChatroom, user);
        dispose();
    }

    private void onRefreshButtonClicked(ActionEvent e){
        updateUserList();
        updateChatRoomList();
    }
}
