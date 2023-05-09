package server;

import sharedresources.interfaces.Message;
import sharedresources.ChatRoom;
import sharedresources.User;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;


public class ChatRoomStorage {

    private final Map<UUID, ChatRoom> chatRoomCentralStorage;
    private final Map<User, List<UUID>> chatRoomsThisUsersIsIn;

    public ChatRoomStorage() {
        chatRoomCentralStorage = Collections.synchronizedMap(new HashMap<>());
        chatRoomsThisUsersIsIn = Collections.synchronizedMap(new HashMap<>());
    }

    public List<ChatRoom> getChatRooms(User user){
        List<UUID> chatRoomIDs = chatRoomsThisUsersIsIn.get(user);
        List<ChatRoom> chatRooms = new ArrayList<>();

        if(chatRoomIDs == null) return null;
        for(UUID id : chatRoomIDs){
            chatRooms.add(chatRoomCentralStorage.get(id));
        }

        return chatRooms;

    }

    public ChatRoom addChatRoom(String chatRoomName, List<User> membersInChatRoom){

        ChatRoom chatRoom = new ChatRoom(chatRoomName, membersInChatRoom, new ArrayList<>());

        chatRoomCentralStorage.put(chatRoom.getChatRoomID(), chatRoom);
        for(User user : membersInChatRoom){
            if(chatRoomsThisUsersIsIn.containsKey(user)) chatRoomsThisUsersIsIn.get(user).add(chatRoom.getChatRoomID());
            else {
                ArrayList<UUID> chatRoomId = new ArrayList<>();
                chatRoomId.add(chatRoom.getChatRoomID());
                chatRoomsThisUsersIsIn.put(user, chatRoomId);
            }
        }
        return chatRoom;
    }

    public void addMessageToChatRoom(UUID chatRoomID, Message message){
        ChatRoom theChatRoom = chatRoomCentralStorage.get(chatRoomID);
        if(theChatRoom == null) return;
        theChatRoom.addMessage(message);
    }
}

