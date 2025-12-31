package com.webone.quiosq.ws;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class PixStatusBroadcaster {
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void broadcast(String payload) {
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(payload));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

