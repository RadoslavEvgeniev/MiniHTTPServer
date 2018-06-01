package javache.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Reader {

    private Reader() {}

    public static String readAllLines(InputStream clientSocketInputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocketInputStream));
        StringBuilder sb = new StringBuilder();
        while (bufferedReader.ready()) {
            sb.append((char)bufferedReader.read());
        }
        return sb.toString();
    }
}
