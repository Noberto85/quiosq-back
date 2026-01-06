package com.webone.quiosq.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@AllArgsConstructor
public class PixStatusHandler extends TextWebSocketHandler {

    private final PixStatusBroadcaster broadcaster;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // quando o cliente envia o idPagamento
        String payload = message.getPayload();
        // supondo que seja JSON {"id":"123456"}
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(payload);
        String idPagamento = node.get("id").asText();

        broadcaster.associateSessionWithPayment(session, idPagamento);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcaster.removeSession(session);
    }
}
