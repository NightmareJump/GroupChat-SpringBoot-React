package com.demo.groupChat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 房间名称
    @Column(nullable = false)
    private String name;

    // 房间描述
    private String description;

    // 创建时间
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 更新时间
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 默认构造函数
    public ChatRoom() {}

    // 带参构造函数
    public ChatRoom(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 保存之前初始化时间戳
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 更新之前刷新更新时间戳
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

