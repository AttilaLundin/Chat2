import java.util.HashMap;

import java.util.Map;


public class ChatHistory {

    //Lock
    private Map<Integer, ChatRoom> chatRoomHistory;

    public ChatHistory(){
        chatRoomHistory = new HashMap<>();
    }

    public void addChatRoom(ChatRoom chatRoom){
        chatRoomHistory.putIfAbsent(chatRoom.getChatRoomID(), chatRoom);
    }

    public ChatRoom getChatroom(int chatRoomId){
        return chatRoomHistory.get(chatRoomId);
    }

}