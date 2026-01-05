package com.webone.quiosq.config;

import com.webone.quiosq.ws.PixStatusBroadcaster;
import com.webone.quiosq.ws.PixStatusHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final PixStatusBroadcaster broadcaster;

    public WebSocketConfig(PixStatusBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new PixStatusHandler(broadcaster), "/ws/pix")
            .setAllowedOrigins("*");
    }

}

