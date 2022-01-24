package pl.edu.pjwstk.wojtekolesinski.udpserver;

import pl.edu.pjwstk.wojtekolesinski.udpserver.server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public final static int FLAG = 189231; // specified in the task
    public final static int PORT = 13154; // server's port specified in the task
    public final static String IP = "172.21.48.25"; // server's ip, also in the task

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(IP, PORT);
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            outToServer.println(FLAG);
            outToServer.println(Server.SERVER_SOCKET_IP+":"+Server.SERVER_SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
