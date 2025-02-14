package com.demo.groupChat.controller;

import com.demo.groupChat.model.ChatMessage;
import com.demo.groupChat.model.ChatRoom;
import com.demo.groupChat.model.User;
import com.demo.groupChat.repository.ChatMessageRepository;
import com.demo.groupChat.repository.ChatRoomRepository;
import com.demo.groupChat.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // 注册接口（可选）
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // 1. 查重
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }
        // 2. 保存用户 (实际应对 user.getPassword() 进行加密)
        userRepo.save(user);
        return "User registered successfully";
    }

    // 登录接口
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        // 1. 从数据库中查找用户
        User user = userRepo.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return "Username not found";
        }
        // 2. 比对密码（若加密则用 BCryptPasswordEncoder.matches(...)）
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return "Invalid password";
        }
        // 3. 登录成功，返回一个成功消息或令牌
        return "Login success";
        // 或者返回 JWT token, e.g. { "token": "xxx.yyy.zzz" }
    }
}