package javache;

import javache.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class RequestHandler {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public RequestHandler() {}

    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        if (this.httpRequest.getMethod().equals("GET")) {
            return this.processGetRequest();
        }

        return this.ok(new byte[0]);
    }

    private byte[] ok(byte[] content) {
        this.httpResponse.setStatus(HttpStatus.Ok);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] badRequest(byte[] content) {
        this.httpResponse.setStatus(HttpStatus.BadRequest);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] notFound(byte[] content) {
        this.httpResponse.setStatus(HttpStatus.NotFound);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] redirect(byte[] content) {
        this.httpResponse.setStatus(HttpStatus.SeeOther);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] internalServerError(byte[] content) {
        this.httpResponse.setStatus(HttpStatus.InternalServerError);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] processResourceRequest() {
        String assetPath = WebConstants.ASSETS_FOLDER_PATH + this.httpRequest.getRequestUrl();

        File file = new File(assetPath);
        if (!file.exists() || file.isDirectory()) {
            return this.notFound("Asset not found!".getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(assetPath));
        } catch (IOException e) {
            this.internalServerError("Something went wrong!".getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));
        this.httpResponse.addHeader("Content-Length", String.valueOf(result.length));
        this.httpResponse.addHeader("Content-Disposition", "inline");

        return this.ok(result);
    }

    private byte[] processGetRequest() {
//        if (this.httpRequest.getRequestUrl().equals("/index")) {
//            return new byte[0];
//        }

        if (this.httpRequest.isResource()) {
            return this.processResourceRequest();
        }

        return this.ok(new byte[0]);
    }

    private String getMimeType(File file) {
        return null;
    }
}