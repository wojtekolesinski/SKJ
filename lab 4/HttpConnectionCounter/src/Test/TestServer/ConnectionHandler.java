package Test.TestServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

public class ConnectionHandler  implements Runnable {

    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line;
            while ((line = inFromClient.readLine()) != null) {
                outToClient.println(line.toUpperCase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
