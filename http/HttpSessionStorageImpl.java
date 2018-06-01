package javache.http;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpSessionStorageImpl implements HttpSessionStorage {

    private Map<String, HttpSession> allSessions;

    public HttpSessionStorageImpl() {
        this.allSessions = new HashMap<>();
    }

    public void addSession(HttpSession session) {
        this.allSessions.putIfAbsent(session.getId(), session);
    }

    public void refreshSessions() {
        List<String> idsToRemove = new LinkedList<>();

        for (HttpSession session : allSessions.values()) {
            if (!session.isValid()) {
                idsToRemove.add(session.getId());
            }
        }

        for (String id : idsToRemove) {
            this.allSessions.remove(id);
        }
    }

    @Override
    public HttpSession getSessionById(String id) {
        if (!this.allSessions.containsKey(id)) {
            return null;
        }
        return this.allSessions.get(id);
    }
}
