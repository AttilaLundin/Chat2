package server;

import sharedresources.interfaces.Message;
import sharedresources.ChatRoom;
import sharedresources.SessionUser;

import java.util.*;


public class chatRoomStorage {

    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<SessionUser, List<UUID>> usersChatRooms;

    public chatRoomStorage() {
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

