package server;

import sharedresources.Message;
import sharedresources.ChatRoom;
import sharedresources.SessionUser;

import java.util.*;


public class chatroomStorage {

    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<SessionUser, List<UUID>> usersChatRooms;

    public chatroomStorage() {
        chatRoomHistory = Collections.synchronizedMap(new HashMap<>());
        usersChatRooms = Collections.synchronizedMap(new HashMap<>());
    }

    public List<ChatRoom> getChatRooms(SessionUser user){
        List<UUID> chatRoomIDs = usersChatRooms.get(user);
        return null;
    }

    public void addChatRoom(List<SessionUser> membersInChatRoom, String chatRoomName){

        ChatRoom chatRoom = new ChatRoom(membersInChatRoom, new ArrayList<>());

        chatRoomHistory.put(chatRoom.getChatRoomID(), chatRoom);
        for(SessionUser user : membersInChatRoom){
            if(usersChatRooms.containsKey(user)) usersChatRooms.get(user).add(chatRoom.getChatRoomID());
            else {
                ArrayList<UUID> chatRoomId = new ArrayList<>();
                chatRoomId.add(chatRoom.getChatRoomID());
                usersChatRooms.put(user, chatRoomId);
            }
        }
    }

    public void addMessage(UUID chatRoomId, Message message){
        chatRoomHistory.get(chatRoomId).addMessage(message);
    }
}

