package com.demo.groupChat.repository;

import com.demo.groupChat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 比如想根据房间ID查找消息
    List<ChatMessage> findByRoomId(Long roomId);

    // 根据发送者ID查找
    List<ChatMessage> findBySenderId(Long senderId);
}