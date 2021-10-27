import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPHandler implements Runnable {

    private Socket socket;

    public TCPHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while (!(line = inFromClient.readLine()).isEmpty()) { // Pusta linia na koniec nagłówków
                System.out.println(line);
            }

            // Headers
            outToClient.println("HTTP/1.1 200 OK");
            outToClient.println("Content-Length: 17");
            outToClient.println();
            //Content
            outToClient.println("<html>test</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
