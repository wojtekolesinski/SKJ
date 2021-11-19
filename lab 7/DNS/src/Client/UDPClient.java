package Client;

import Server.UDPServer;

import java.io.IOException;
import java.net.*;

public class UDPClient {


    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                try (DatagramSocket clientSocket = new DatagramSocket()) {

                    int toServer = 5;
                    byte[] sendData = String.valueOf(toServer).getBytes();
                    InetAddress serverAddress = InetAddress.getByName("127.0.0.1");

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, UDPServer.PORT);

                    clientSocket.send(sendPacket);

                    byte[] receivedData = new byte[1024];
                    DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                    clientSocket.receive(receivedPacket);

                    System.out.println(
                            "FROM SERVER: " + new String(receivedPacket.getData(), 0, receivedPacket.getLength())
                    );


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
