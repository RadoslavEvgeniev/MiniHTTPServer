package javache.http;

import javache.WebConstants;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {

    private Map<String, String> headers;
    private HttpStatus status;
    private byte[] content;

    public HttpResponseImpl() {
        this.setContent(new byte[0]);
        this.headers = new HashMap<>();
    }

    private byte[] getHeadersBytes() {
        StringBuilder result = new StringBuilder();
        result.append(WebConstants.SERVER_HTTP_VERSION).append(" ")
                .append(this.getStatus().getStatusPhrase()).append(System.lineSeparator());

        for (Map.Entry<String, String> header : this.getHeaders().entrySet()) {
            result.append(header.getKey()).append(": ").append(header.getValue()).append(System.lineSeparator());
        }

        result.append(System.lineSeparator());

        return result.toString().getBytes();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public byte[] getBytes() {
        byte[] headerBytes = this.getHeadersBytes();
        byte[] bodyBytes = this.getContent();

        byte[] fullResponse = new byte[headerBytes.length + bodyBytes.length];
        int index = 0;
        for (byte headerByte : headerBytes) {
            fullResponse[index] = headerByte;
            index++;
        }
        for (byte bodyByte : bodyBytes) {
            fullResponse[index] = bodyByte;
            index++;
        }
        return fullResponse;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }
}
