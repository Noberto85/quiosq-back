package com.webone.quiosq.ws;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class PixStatusBroadcaster {

    private final Map<String, List<WebSocketSession>> sessionsByPayment = new ConcurrentHashMap<>();


    public void associateSessionWithPayment(WebSocketSession session, String idPagamento) {
        sessionsByPayment
            .computeIfAbsent(idPagamento, k -> new ArrayList<>())
            .add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessionsByPayment.values().forEach(list -> list.remove(session));
    }

    public void broadcastToPayment(String idPagamento, String message) {
        List<WebSocketSession> sessions = sessionsByPayment.get(idPagamento);
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

