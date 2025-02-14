package com.demo.groupChat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")  // 对应数据库表 chat_messages
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 消息内容
    @Column(nullable = false)
    private String content;

    // 消息发送时间
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * 外键 sender_id -> users.id
     * 用 ManyToOne 指明多对一关系
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_sender"))
    private User sender;

    /**
     * 外键 room_id -> chat_rooms.id
     * 用 ManyToOne 指明多对一关系
     */
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_room"))
    private ChatRoom room;

    // 默认构造函数
    public ChatMessage() {}

    // 带参构造函数
    public ChatMessage(String content, User sender, ChatRoom room) {
        this.content = content;
        this.sender = sender;
        this.room = room;
    }

    /**
     * 在插入记录之前，自动设置消息发送时间
     */
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }
}