package com.demo.groupChat.controller;



import com.demo.groupChat.model.ChatMessage;
import com.demo.groupChat.model.ChatRoom;
import com.demo.groupChat.model.User;
import com.demo.groupChat.repository.ChatMessageRepository;
import com.demo.groupChat.repository.ChatRoomRepository;
import com.demo.groupChat.repository.UserRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final UserRepository userRepo;
    private final ChatRoomRepository roomRepo;
    private final ChatMessageRepository messageRepo;

    public ChatController(UserRepository userRepo,
                          ChatRoomRepository roomRepo,
                          ChatMessageRepository messageRepo) {
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
        this.messageRepo = messageRepo;
    }

    // 1. 创建用户
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    // 2. 获取所有用户
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // 3. 创建房间
    @PostMapping("/rooms")
    public ChatRoom createRoom(@RequestBody ChatRoom room) {
        return roomRepo.save(room);
    }

    // 4. 获取所有房间
    @GetMapping("/rooms")
    public List<ChatRoom> getAllRooms() {
        return roomRepo.findAll();
    }

    // 5. 在某个房间发送消息
    //   这里为了简单，RequestParam 里传 senderId、roomId
    //   也可以用 RequestBody 传更复杂 JSON
    @PostMapping("/messages")
    public ChatMessage sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long roomId,
            @RequestParam String content
    ) {
        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new RuntimeException("No user found with id=" + senderId));
        ChatRoom room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("No room found with id=" + roomId));
        ChatMessage message = new ChatMessage(content, sender, room);
        return messageRepo.save(message);
    }

    // 6. 获取所有消息
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return messageRepo.findAll();
    }

    // 7. 获取某个房间的消息
    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessage> getMessagesByRoom(@PathVariable Long roomId) {
        return messageRepo.findByRoomId(roomId);
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public ChatMessage processMessage(@DestinationVariable Long roomId, ChatMessage message) {
        // 这里可以存数据库 + 设置时间戳
        return message; // 返回后会广播到 /topic/messages/{roomId}
    }


}
