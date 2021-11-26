import Server.UDPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static final int flag = 179466;
    public static final String TCPServerIP = "172.21.48.55";
    public static final int TCPServerPort = 13155;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(TCPServerIP, TCPServerPort);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.printf("%s:%d\n", UDPServer.IP, UDPServer.PORT);
            outToServer.printf("%s:%d\n", UDPServer.IP, UDPServer.PORT);

            System.out.println(inFromServer.readLine());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
