package pl.edu.pjwstk.wojtekolesinski.udpserver.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int SERVER_SOCKET_PORT = 9876;


    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Map<String, List<String>> clients = new LinkedHashMap<>();
        String temp;

        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_SOCKET_PORT)) {

            while (true){
                byte[] receivedData = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                serverSocket.receive(receivedPacket);
                temp = receivedPacket.getAddress() + ":" + receivedPacket.getPort();
                System.out.println(temp);

                if (clients.containsKey(temp)) {
                    String fromServer = new String(receivedPacket.getData(), 0, receivedPacket.getLength()-1);
                    clients.get(temp).set(1,fromServer);
                } else if (!clients.containsKey(temp)) {
                    clients.put(temp, new ArrayList<>(Arrays.asList("-1","-1","-1","-1")));
                    String fromServer = new String(receivedPacket.getData(), 0, receivedPacket.getLength()-1);
                    clients.get(temp).set(0,fromServer);
                    threadPool.submit(new UDPHandler(receivedPacket, serverSocket, clients));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
