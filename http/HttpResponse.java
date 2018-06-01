package javache.http;

import java.util.Map;

public interface HttpResponse {

    Map<String, String> getHeaders();
    HttpStatus getStatus();
    void setStatus(HttpStatus statusCode);
    byte[] getContent();
    void setContent(byte[] content);
    byte[] getBytes();
    void addHeader(String header, String value);
}
