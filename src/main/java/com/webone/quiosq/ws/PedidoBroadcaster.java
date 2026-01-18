package com.webone.quiosq.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class PedidoBroadcaster {
    private final Map<String, List<WebSocketSession>> sessionsByPayment = new ConcurrentHashMap<>();


    public void associateSessionQuiosque(WebSocketSession session, String id) {
        sessionsByPayment
            .computeIfAbsent(id, k -> new ArrayList<>())
            .add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessionsByPayment.values().forEach(list -> list.remove(session));
    }

    public void broadcast(String id, String message) {
        List<WebSocketSession> sessions = sessionsByPayment.get(id);
        if (sessions != null) {
            for (WebSocketSession s : sessions) {
                try {
                    s.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}