package pl.edu.pjwstk.wojtekolesinski.udpserver;

import pl.edu.pjwstk.wojtekolesinski.udpserver.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public final static int FLAG = 188082;
    public final static int PORT = 39799;
    public final static String IP = "172.21.48.118";

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(IP, PORT);
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            outToServer.println(FLAG);
            outToServer.println(Server.SERVER_SOCKET_IP+":"+Server.SERVER_SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
// 459008252
// 459008252