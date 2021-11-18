package Server;

import Client.WorkingPortsCounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {
    static int flag = 29384;
    Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            if (Integer.parseInt(inFromClient.readLine()) == (WorkingPortsCounter.getFlag())) {
                outToClient.println(flag);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
