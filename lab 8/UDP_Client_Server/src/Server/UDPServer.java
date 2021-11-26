package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPServer {

    public static final String IP = "172.23.129.141";
    public static final int PORT = 9876;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivedPacket);
                executorService.submit(new UDPServerHandler(receivedPacket, serverSocket));
            }




        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}