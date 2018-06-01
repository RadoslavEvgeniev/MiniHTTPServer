package javache;

import javache.io.Reader;
import javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
ConnectionHandler class - process the client's connection.
 */
public class ConnectionHandler extends Thread {

    private RequestHandler requestHandler;
    private Socket clientSocket;
    private InputStream clientSocketInputStream;
    private OutputStream clientSocketOutputStream;

    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.initializeConnection(clientSocket);
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            String requestContent = Reader.readAllLines(this.clientSocketInputStream);
            byte[] responseContent = this.requestHandler.handleRequest(requestContent);
            Writer.writeBytes(responseContent, this.clientSocketOutputStream);
            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientSocketInputStream = this.clientSocket.getInputStream();
            this.clientSocketOutputStream = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
