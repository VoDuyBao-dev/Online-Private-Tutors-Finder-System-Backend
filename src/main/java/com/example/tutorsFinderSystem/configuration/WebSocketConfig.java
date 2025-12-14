package com.example.tutorsFinderSystem.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketJwtChannelInterceptor webSocketJwtChannelInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // server -> client
        registry.enableSimpleBroker("/topic", "/queue");

        // client -> server
        registry.setApplicationDestinationPrefixes("/app");

        // /user/queue/** routing by Principal.getName()
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // 1) WebSocket thuần (để test bằng wscat, hoặc frontend dùng brokerURL ws://)
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*");

        // 2) SockJS (để frontend dùng sockjs-client)
        registry.addEndpoint("/ws/chat-sockjs")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketJwtChannelInterceptor);
    }
}
