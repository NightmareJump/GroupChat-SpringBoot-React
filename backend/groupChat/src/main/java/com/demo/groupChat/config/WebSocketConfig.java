package com.demo.groupChat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * WebSocketConfig: 启用 STOMP 消息处理
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册 STOMP 端点 (SockJS)
     * 前端将通过此端点连接WebSocket: 例如 /ws-chat
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 允许前端通过 SockJS 连接到 /ws-chat 端点
        registry.addEndpoint("/ws-chat")
                // 视需要设置允许跨域
                .setAllowedOrigins("http://localhost:5173", "http://localhost:3000")
                .withSockJS();
    }

    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端向服务器发送消息的前缀 (即 @MessageMapping 默认前缀)
        registry.setApplicationDestinationPrefixes("/app");

        // 启用简单的内存消息代理, 客户端订阅 /topic/xxx
        registry.enableSimpleBroker("/topic");
        // 如需点对点，可配置: registry.enableSimpleBroker("/topic", "/queue");
        // 并在客户端订阅 /user/queue/xxx
    }

    /*
     * 其他可选方法:
     *
     * @Override
     * public void configureClientInboundChannel(ChannelRegistration registration) {
     *     // inbound 拦截器, 可做安全检查
     * }
     *
     * @Override
     * public void configureClientOutboundChannel(ChannelRegistration registration) {
     *     // outbound 拦截器
     * }
     *
     * @Override
     * public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
     *     // 自定义消息转换器
     *     return true;
     * }
     */
}

