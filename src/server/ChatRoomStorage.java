package server;

import sharedresources.TextMessage;
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

    private  Map<UUID, ChatRoom> chatRoomCentralStorage;
    private  Map<User, List<UUID>> chatRoomsThisUsersIsIn;

    public ChatRoomStorage() {
        chatRoomCentralStorage = Collections.synchronizedMap(new HashMap<>());
        chatRoomsThisUsersIsIn = Collections.synchronizedMap(new HashMap<>());
    }

    public List<ChatRoom> getAllChatRooms(User user){
        List<UUID> chatRoomIDs = chatRoomsThisUsersIsIn.get(user);
        List<ChatRoom> chatRooms = new ArrayList<>();

        if(chatRoomIDs == null) return null;
        for(UUID id : chatRoomIDs){
            chatRooms.add(chatRoomCentralStorage.get(id));
        }

        return chatRooms;

    }

    public ChatRoom getChatRoom(UUID chatRoomID){
        List<Message> list = chatRoomCentralStorage.get(chatRoomID).getMessages();
        return chatRoomCentralStorage.get(chatRoomID);
    }

    public List<Message> getMessages(UUID chatRoomID){
        return chatRoomCentralStorage.get(chatRoomID).getMessages();
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
        ChatRoom chatRoom = chatRoomCentralStorage.get(chatRoomID);
        chatRoom.addMessage(message);
        if(chatRoom == null){
            return;
        }
        ChatRoom chatRoom1 = chatRoomCentralStorage.get(chatRoomID);
    }
}

