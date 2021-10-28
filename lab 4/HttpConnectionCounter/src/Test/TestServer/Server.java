package Test.TestServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static int port = 80;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(port)) {
            ExecutorService executorService = Executors.newFixedThreadPool(3);

            while (true) {
                executorService.submit(new ConnectionHandler(server.accept()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
