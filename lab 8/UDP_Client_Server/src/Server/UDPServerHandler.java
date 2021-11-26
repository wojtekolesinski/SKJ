package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class UDPServerHandler implements Runnable {

    private DatagramSocket serverSocket;
    private DatagramPacket receivedPacket;

    public UDPServerHandler(DatagramPacket receivedPacket, DatagramSocket serverSocket) {
        this.receivedPacket = receivedPacket;
        this.serverSocket = serverSocket;
    }

//    public UDPServerHandler(DatagramSocket serverSocket) {
//        this.serverSocket = serverSocket;
//    }

    public String getDataFromClient() {
//        serverSocket.receive(receivedPacket);
        return "";
    }

    @Override
    public void run() {
        int fromClient = Integer.parseInt(new String(receivedPacket.getData(), 0, receivedPacket.getLength()));

        int modifyFromClient = fromClient + LocalDateTime.now().getNano();

        byte[] sendData = String.valueOf(modifyFromClient).getBytes();
        InetAddress clientAddress = receivedPacket.getAddress();
        int clientPort = receivedPacket.getPort();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);

        try {
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
