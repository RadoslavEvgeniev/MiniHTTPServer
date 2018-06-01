package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

/*
Server class - connection controller, it oversees the process with the socket connections.
 */
public class Server {

    private static final String LISTENING_MESSAGE = "Listening on port: ";
    private static final String TIMEOUT_DETECTION_MESSAGE = "Timeout detected";
    private static final Integer SOCKET_TIMEOUT_MILLISECONDS = 5000;

    private ServerSocket server;
    private Integer port;
    private Integer timeouts;

    public Server(int port) {
        this.port = port;
        this.timeouts = 0;
    }

    public void run() throws IOException {
        this.server = new ServerSocket(this.port);
        System.out.println(LISTENING_MESSAGE + this.port);

        this.server.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

        while (true) {
            try (Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException ste) {
                System.out.println(TIMEOUT_DETECTION_MESSAGE);
                this.timeouts++;
            }
        }
    }
}
