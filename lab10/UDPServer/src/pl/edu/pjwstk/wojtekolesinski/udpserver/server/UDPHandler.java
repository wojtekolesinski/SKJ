package pl.edu.pjwstk.wojtekolesinski.udpserver.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UDPHandler implements Runnable{
    private DatagramPacket receivedPacket;
    private final DatagramSocket serverSocket;
    private final Map<String, List<String>> clientsMap;
    private final InetAddress clientAddress;
    private final int clientPort;

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

    private String getDataAtIndex(int index) {
        return clientsMap.get(getAddressAndPort()).get(index);
    }

    private List<String> getDataAtIndexRange(int start, int end) {
        List<String> data = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            data.add(getDataAtIndex(i));
        }

        return data;
    }

    private void sendData(String data) {
        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), clientAddress, clientPort);
        try {
            serverSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForData(int index) {
        while (clientsMap.get(getAddressAndPort()).size() <= index) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int gcdFromList(List<Integer> list) {
        return list.stream().reduce(0, (total, element) -> gcd(total, element));
    }

    public int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;

        while (a != b) {
            if (a > b)
                a -= b;
            else
                b -= a;
        }
        return a;
    }

    public List<Integer> getFirstInputs() {
        return clientsMap.values()
                        .stream()
                        .map(list -> Integer.parseInt(list.get(0)))
                        .collect(Collectors.toList());
    }

    @Override
    public void run() {
        // wczytanie jednej linii tekstu i odeslanie jej
        int index = 0;
        waitForData(index);
        String data = getDataAtIndex(index);
        sendData(data);
        index++;

        // wczytanie 4  kolejnych liczb i odeslanie ich nwd
        waitForData(index + 3);
        List<Integer> numbers = getDataAtIndexRange(index, index+3).stream().map(Integer::parseInt).collect(Collectors.toList());
        data = String.valueOf(gcdFromList(numbers));
        sendData(data);
        index += 4;

        // wyślij sumę liczb otrzymanych od wszystkich klientów
        sendData(String.valueOf(getFirstInputs().stream().reduce(0, Integer::sum)));

        // wyślij nwd tych liczb
        sendData(String.valueOf(gcdFromList(getFirstInputs())));

        // odbierz napis, usun wszystkie wystapienia 3 i odeslij wynik
        waitForData(index);
        data = getDataAtIndex(index);
        System.out.println(data);
        data = data.replace("3", "");
        sendData(data);
        index++;

        // wyślij numer portu z którego się komunikujesz
        sendData(String.valueOf(Server.SERVER_SOCKET_PORT));

        // odbierz finalną flagę
        waitForData(index);
        data = getDataAtIndex(index);
        System.err.println(data);

    }
}
