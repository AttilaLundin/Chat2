package server;

import application.ChatRoom;
import application.Message;
import application.User;

import java.util.*;


public class ChatHistory {

    //kan vi lägger chattrumsID i separat klass, vi kör en hashmap på skiten, med

    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<User, List<UUID>> chatroomsUsersAreIn;

    public ChatHistory(){
        chatRoomHistory = Collections.synchronizedMap(new HashMap<>());
        chatroomsUsersAreIn = Collections.synchronizedMap(new HashMap<>());
    }


//    lagrar alla chatrum här som är kopplade till user
    public void addChatRoom(ChatRoom chatRoom, UUID chatRoomId, List<User> users){
        if(!chatRoomHistory.containsKey(chatRoomId)){

            for(User user : users){
                chatroomsUsersAreIn.get(user).add(chatRoomId);
            }
        }
    }

    //    lagrar alla chatrum i user som är kopplade till user
    public void addChatRoom1(ChatRoom chatRoom, UUID chatRoomId, List<User> users){
        if(chatRoomHistory.putIfAbsent(chatRoomId, chatRoom) == null){
            for(User user : users){
                user.addChatRoom(chatRoomId);
            }
        }
    }

    public void addMessage(UUID chatRoomId, Message message){
        chatRoomHistory.get(chatRoomId).addMessage(message);
    }

    public ChatRoom getChatroom(int chatRoomId){
        return chatRoomHistory.get(chatRoomId);

    }

}

