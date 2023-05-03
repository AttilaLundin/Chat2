package server;

import application.ChatRoom;
import application.User;

import java.util.*;


public class ChatHistory {

    //kan vi lägger chattrumsID i separat klass, vi kör en hashmap på skiten, med

    private Map<UUID, ChatRoom> chatRoomHistory;
    private Map<User, List<UUID>> väljettnamn;
    private UUID chatRoomId;


    public ChatHistory(){
        chatRoomHistory = Collections.synchronizedMap(chatRoomHistory);
    }


//    lagrar alla chatrum här som är kopplade till user
    public void addChatRoom(ChatRoom chatRoom, UUID chatRoomId, List<User> users){
        if(chatRoomHistory.putIfAbsent(chatRoomId, chatRoom) == null){
            for(User user : users){
                väljettnamn.get(user).add(chatRoomId);
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




    public ChatRoom getChatroom(int chatRoomId){
        return chatRoomHistory.get(chatRoomId);

    }

}

