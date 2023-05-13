package server;

import common.Message;
import common.ChatRoom;
import common.RegisteredUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that handles the storage of chat rooms in the chat application. This class is responsible for
 * adding and retrieving chat rooms and messages, and tracking which users are in which chat rooms.
 */
public class ChatRoomStorage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Map<UUID, ChatRoom> chatRoomCentralStorage;
    private final Map<RegisteredUser, List<UUID>> chatRoomsThisUsersIsIn;

    /**
     * Constructor that initializes the chat room and user storage.
     */
    public ChatRoomStorage() {
        chatRoomCentralStorage = new ConcurrentHashMap<>();
        chatRoomsThisUsersIsIn = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves all chat rooms that a specific user is in.
     *
     * @param user the user for which to get the chat rooms
     * @return a list of chat rooms
     */
    public List<ChatRoom> getAllChatRooms(RegisteredUser user){
        List<UUID> chatRoomIDs = chatRoomsThisUsersIsIn.get(user);
        List<ChatRoom> chatRooms = new ArrayList<>();

        if(chatRoomIDs == null) return null;
        for(UUID id : chatRoomIDs){
            chatRooms.add(chatRoomCentralStorage.get(id));
        }

        return chatRooms;

    }

    /**
     * Retrieves a specific chat room by its ID.
     *
     * @param chatRoomID the ID of the chat room
     * @return the chat room
     */
    public ChatRoom getChatRoom(UUID chatRoomID){
        return chatRoomCentralStorage.get(chatRoomID);
    }

    /**
     * Retrieves all messages from a specific chat room.
     *
     * @param chatRoomID the ID of the chat room
     * @return a list of messages
     */
    public List<Message> getMessages(UUID chatRoomID){
        return chatRoomCentralStorage.get(chatRoomID).getMessages();
    }

    /**
     * Adds a new chat room with the provided name and members.
     *
     * @param chatRoomName the name of the chat room
     * @param membersInChatRoom the members of the chat room
     * @return the created chat room
     */
    public ChatRoom addChatRoom(String chatRoomName, List<RegisteredUser> membersInChatRoom){

        ChatRoom chatRoom = new ChatRoom(chatRoomName, membersInChatRoom, new ArrayList<>());

        chatRoomCentralStorage.put(chatRoom.getChatRoomID(), chatRoom);
        for(RegisteredUser user : membersInChatRoom){
            if(chatRoomsThisUsersIsIn.containsKey(user)) chatRoomsThisUsersIsIn.get(user).add(chatRoom.getChatRoomID());
            else {
                ArrayList<UUID> chatRoomId = new ArrayList<>();
                chatRoomId.add(chatRoom.getChatRoomID());
                chatRoomsThisUsersIsIn.put(user, chatRoomId);
            }
        }
        return chatRoom;
    }

    /**
     * Adds a message to a specific chat room.
     *
     * @param chatRoomID the ID of the chat room
     * @param message the message to add
     */
    public void addMessageToChatRoom(UUID chatRoomID, Message message){
        ChatRoom chatRoom = chatRoomCentralStorage.get(chatRoomID);
        if(chatRoom == null) return;
        chatRoom.addMessage(message);
    }
}

