package server;

import sharedresources.Message;
import sharedresources.ChatRoom;
import sharedresources.SessionUser;

import java.util.*;


public class ChatHistory {

    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<SessionUser, List<UUID>> usersChatRooms;

    public ChatHistory() {
        chatRoomHistory = Collections.synchronizedMap(new HashMap<>());
        usersChatRooms = Collections.synchronizedMap(new HashMap<>());
    }

    public List<ChatRoom> getChatRooms(SessionUser user){
        List<UUID> chatRoomIDs = usersChatRooms.get(user);
        return null;
    }

    public void addChatRoom(ChatRoom chatRoom, UUID chatRoomId, List<SessionUser> users){
        if(chatRoomHistory.containsKey(chatRoomId)) return;

        chatRoomHistory.put(chatRoomId, chatRoom);
        for(SessionUser user : users){
            if(usersChatRooms.containsKey(user)) usersChatRooms.get(user).add(chatRoomId);
            else {
                ArrayList<UUID> chatrooms = new ArrayList<>();
                chatrooms.add(chatRoomId);
                usersChatRooms.put(user, chatrooms);
            }
        }
    }

    public void addMessage(UUID chatRoomId, Message message){
        chatRoomHistory.get(chatRoomId).addMessage(message);
    }



}

