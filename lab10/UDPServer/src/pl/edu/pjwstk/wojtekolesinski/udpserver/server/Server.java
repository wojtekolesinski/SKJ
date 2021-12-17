package pl.edu.pjwstk.wojtekolesinski.udpserver.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int SERVER_SOCKET_PORT = 9876;
    public static final String SERVER_SOCKET_IP = "172.23.129.44";

    public static String getData(DatagramPacket packet) {
        return new String(packet.getData(), 0, packet.getLength() - 1);
    }



    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Map<String, List<String>> clients = new LinkedHashMap<>();
        String IPAndPort;

        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_SOCKET_PORT)) {

            while (true) {
                byte[] receivedData = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                serverSocket.receive(receivedPacket);

                IPAndPort = receivedPacket.getAddress().getHostAddress() + ":" + receivedPacket.getPort();
                System.out.println(IPAndPort);
                String fromServer = getData(receivedPacket);

                if (!clients.containsKey(IPAndPort)) {
                    clients.put(IPAndPort, new ArrayList<>());
                    threadPool.submit(new UDPHandler(receivedPacket, serverSocket, clients));
                }
                clients.get(IPAndPort).add(fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
