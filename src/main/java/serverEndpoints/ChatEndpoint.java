package serverEndpoints;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{userId}")
public class ChatEndpoint {
    // This map will store user IDs and their corresponding WebSocket sessions.
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessions.put(userId, session);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        sessions.remove(userId);
    }

	public static Map<String, Session> getSessions() {
		return sessions;
	}
}

