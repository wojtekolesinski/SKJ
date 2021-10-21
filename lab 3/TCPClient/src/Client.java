import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {

        try {
            String address = "users.pja.edu.pl";
            Socket clientSocket = new Socket(address, 80);

            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.println("GET /~tiia/skj_welcome.html HTTP/1.1");
            outToServer.println("Host: " + address);
            outToServer.println();

            String line;
            while ((line = inFromServer.readLine()) != null) {
                System.out.println(line);
            }

            clientSocket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
