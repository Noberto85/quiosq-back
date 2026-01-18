package com.webone.quiosq.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PedidoHandler extends TextWebSocketHandler {
    private final PedidoBroadcaster broadcaster;

    public PedidoHandler(PedidoBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // quando o cliente envia o idPagamento
        String payload = message.getPayload();
        // supondo que seja JSON {"id":"123456"}
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(payload);
        String id = node.get("quiosque").asText();

        broadcaster.associateSessionQuiosque(session, id);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcaster.removeSession(session);
    }
}