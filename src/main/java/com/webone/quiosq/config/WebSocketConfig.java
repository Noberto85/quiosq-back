package com.webone.quiosq.config;

import com.webone.quiosq.ws.PedidoBroadcaster;
import com.webone.quiosq.ws.PedidoHandler;
import com.webone.quiosq.ws.PixStatusBroadcaster;
import com.webone.quiosq.ws.PixStatusHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final PixStatusBroadcaster pixBroadcaster;
    private final PedidoBroadcaster pedidoBroadcaster;




    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new PixStatusHandler(pixBroadcaster), "/ws/pix")
            .setAllowedOrigins("*");
        registry.addHandler(new PedidoHandler(pedidoBroadcaster), "/ws/pedido")
            .setAllowedOrigins("*");

    }

}

