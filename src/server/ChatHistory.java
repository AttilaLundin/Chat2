package server;

import sharedresources.Message;
import sharedresources.ChatRoom;
import sharedresources.SessionUser;

import java.util.*;


public class ChatHistory {

    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<SessionUser, List<UUID>> usersChatroom;

    public ChatHistory(){
        chatRoomHistory = Collections.synchronizedMap(new HashMap<>());
        usersChatroom = Collections.synchronizedMap(new HashMap<>());
        ChatRoom chatRoom = new ChatRoom();
        chatRoomHistory.put(chatRoom.getChatRoomID(), chatRoom);
        List<UUID> l = new ArrayList<>();
        l.add(chatRoom.getChatRoomID());
        usersChatroom.put(chatRoom.getUsersInChatRoom().get(0), l);
    }

    public void addChatRoom(ChatRoom chatRoom, UUID chatRoomId, List<SessionUser> users){
        if(chatRoomHistory.containsKey(chatRoomId)) return;

        chatRoomHistory.put(chatRoomId, chatRoom);
        for(SessionUser user : users){
            if(usersChatroom.containsKey(user)) usersChatroom.get(user).add(chatRoomId);
            else {
                ArrayList<UUID> chatrooms = new ArrayList<>();
                chatrooms.add(chatRoomId);
                usersChatroom.put(user, chatrooms);
            }
        }
    }

    public void addMessage(UUID chatRoomId, Message message){
        chatRoomHistory.get(chatRoomId).addMessage(message);
    }

    public ArrayList<ChatRoom> getChatRooms(SessionUser user){
        ArrayList<ChatRoom> listOfChatRooms = new ArrayList<>();
        List<UUID> listOfIDs = usersChatroom.get(user);


        if(listOfIDs == null) return listOfChatRooms;

        for(UUID ID : listOfIDs){
            listOfChatRooms.add(chatRoomHistory.get(ID));
        }

        return listOfChatRooms;
    }

}

