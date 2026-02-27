package com.dat.cnpm_btl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowtimeWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    // Map<showtimeId, Set<WebSocketSession>>
    private final Map<String, Set<WebSocketSession>> showtimeSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String showtimeId = extractShowtimeId(session);

        showtimeSessions
                .computeIfAbsent(showtimeId, k -> ConcurrentHashMap.newKeySet())
                .add(session);

        log.info("WS: Client {} connected to showtime: {} (total: {})",
                session.getId(), showtimeId,
                showtimeSessions.get(showtimeId).size());

        // Send CONNECTION_ACK
        Map<String, Object> ack = Map.of(
                "type", "CONNECTION_ACK",
                "data", Map.of(),
                "timestamp", Instant.now().toString()
        );
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(ack)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            Map<?, ?> payload = objectMapper.readValue(message.getPayload(), Map.class);
            String type = (String) payload.get("type");

            if ("PING".equals(type)) {
                Map<String, String> pong = Map.of("type", "PONG");
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(pong)));
                log.debug("WS: PING/PONG with session {}", session.getId());
            } else {
                log.warn("WS: Unknown message type '{}' from session {}", type, session.getId());
            }
        } catch (Exception e) {
            log.error("WS: Error handling message from session {}: {}", session.getId(), e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String showtimeId = extractShowtimeId(session);

        Set<WebSocketSession> sessions = showtimeSessions.get(showtimeId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                showtimeSessions.remove(showtimeId);
            }
        }

        log.info("WS: Client {} disconnected from showtime: {} (status: {})",
                session.getId(), showtimeId, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WS: Transport error for session {}: {}", session.getId(), exception.getMessage());
    }

    /**
     * Broadcast SEATS_UPDATED to all clients watching the given showtime.
     * Called by BookingService after a successful booking.
     */
    public void broadcastSeatUpdate(String showtimeId, List<Integer> seatIds) {
        Set<WebSocketSession> sessions = showtimeSessions.get(showtimeId);
        if (sessions == null || sessions.isEmpty()) {
            log.debug("WS: No active sessions for showtime {}, skipping broadcast", showtimeId);
            return;
        }

        try {
            Map<String, Object> message = Map.of(
                    "type", "SEATS_UPDATED",
                    "data", Map.of("seatIds", seatIds),
                    "timestamp", Instant.now().toString()
            );
            String messageJson = objectMapper.writeValueAsString(message);

            int sent = 0;
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(messageJson));
                        sent++;
                    } catch (Exception e) {
                        log.error("WS: Failed to send to session {}: {}", session.getId(), e.getMessage());
                    }
                }
            }

            log.info("WS: Broadcasted SEATS_UPDATED for showtime {} to {}/{} clients",
                    showtimeId, sent, sessions.size());

        } catch (Exception e) {
            log.error("WS: Error building broadcast message for showtime {}: {}", showtimeId, e.getMessage());
        }
    }

    private String extractShowtimeId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
