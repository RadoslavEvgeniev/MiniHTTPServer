package javache.http;

public interface HttpSessionStorage {

    void addSession(HttpSession session);

    void refreshSessions();

    HttpSession getSessionById(String id);
}
