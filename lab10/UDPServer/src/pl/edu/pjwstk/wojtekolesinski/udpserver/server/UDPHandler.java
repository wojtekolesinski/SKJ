package pl.edu.pjwstk.wojtekolesinski.udpserver.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

public class UDPHandler implements Runnable{
    private DatagramPacket receivedPacket;
    private DatagramSocket serverSocket;
    private Map<String, List<String>> clientsMap;
    private InetAddress clientAddress;
    private int clientPort;

    public UDPHandler(DatagramPacket receivedPacket, DatagramSocket serverSocket, Map<String, List<String>> clientsMap){
        this.receivedPacket = receivedPacket;
        this.serverSocket = serverSocket;
        this.clientsMap = clientsMap;
        this.clientAddress = receivedPacket.getAddress();
        this.clientPort = receivedPacket.getPort();
    }

    private String getAddressAndPort() {
        /*
        * returns client's address and port in a address:port format
        * used for lookups in the map provided by the server
        * */
        return String.format("%s:%d", clientAddress.getHostAddress(), clientPort);
    }

    @Override
    public void run(){
        int random = (int)(Math.random()*10);

        String temp1 = "123   -" + random;

        byte[] toClient = temp1.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(toClient, toClient.length, clientAddress, clientPort);
        try {
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(clientsMap.get(clientAddress + ":" + clientPort).get(1).equals("-1")){

        }

        temp1 = "345-" + random;

        toClient = String.valueOf(temp1).getBytes();
        sendPacket = new DatagramPacket(toClient, toClient.length, clientAddress, clientPort);
        try {
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
