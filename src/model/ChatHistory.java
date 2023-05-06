package model;

import interfaces.Message;

import java.util.*;


public class ChatHistory {

    //kan vi lägger chattrumsID i separat klass, vi kör en hashmap på skiten, med
    // enkla versionen, add message synchronized, problem med alien message
    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<SessionUser, List<UUID>> usersChatroom;

    public ChatHistory(){
        chatRoomHistory = Collections.synchronizedMap(new HashMap<>());
        usersChatroom = Collections.synchronizedMap(new HashMap<>());
    }


    //    lagrar alla chatrum här som är kopplade till user
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
        List<UUID> listOfIDs = usersChatRoom.get(user);

        for(UUID ID : listOfIDs){
            listOfChatRooms.add(chatRoomHistory.get(ID));
        }

        return listOfChatRooms;
    }

}

