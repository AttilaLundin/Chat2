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

    private final Map<UUID, ChatRoom> chatRoomCentralStorage;
    private final Map<User, List<UUID>> chatRoomsThisUsersIsIn;

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
        System.out.println(chatRoomCentralStorage.get(chatRoomID));

        return chatRoomCentralStorage.get(chatRoomID);
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
        System.out.println("Storage adress: "+chatRoom);
        System.out.println(((TextMessage)message).getText());
        if(chatRoom == null){
            System.out.println("chatRoom is null");
            return;
        }
        ChatRoom chatRoom1 = chatRoomCentralStorage.get(chatRoomID);
        System.out.println(((TextMessage)chatRoom1.getMessages().get(0)).getText());
    }
}

