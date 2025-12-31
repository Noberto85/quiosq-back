package com.webone.quiosq.ws;

import lombok.AllArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@AllArgsConstructor
public class PixStatusHandler extends TextWebSocketHandler {

    private final PixStatusBroadcaster broadcaster;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        broadcaster.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcaster.removeSession(session);
    }

}
