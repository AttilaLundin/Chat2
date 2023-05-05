package model;

import java.util.*;


public class ChatHistory {

    //kan vi lägger chattrumsID i separat klass, vi kör en hashmap på skiten, med
    // enkla versionen, add message synchronized, problem med alien message
    private final Map<UUID, ChatRoom> chatRoomHistory;
    private final Map<User, List<UUID>> chatroomsUsersAreIn;

    public ChatHistory(){
        chatRoomHistory = Collections.synchronizedMap(new HashMap<>());
        chatroomsUsersAreIn = Collections.synchronizedMap(new HashMap<>());
    }


    //    lagrar alla chatrum här som är kopplade till user
    public void addChatRoom(ChatRoom chatRoom, UUID chatRoomId, List<User> users){
        if(chatRoomHistory.containsKey(chatRoomId)) return;

        chatRoomHistory.put(chatRoomId, chatRoom);
        for(User user : users){
            if(chatroomsUsersAreIn.containsKey(user)) chatroomsUsersAreIn.get(user).add(chatRoomId);
            else {
                ArrayList<UUID> chatrooms = new ArrayList<>();
                chatrooms.add(chatRoomId);
                chatroomsUsersAreIn.put(user, chatrooms);
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

    public ChatRoom getChatroom(UUID chatRoomId){
        return chatRoomHistory.get(chatRoomId);

    }

}

