package pl.edu.pjwstk.wojtekolesinski.udpserver.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UDPHandler implements Runnable{
    private final DatagramSocket serverSocket;
    private final Map<String, Queue<String>> clientsMap;
    private final InetAddress clientAddress;
    private final int clientPort;

    public UDPHandler(DatagramPacket receivedPacket, DatagramSocket serverSocket, Map<String, Queue<String>> clientsMap){
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

    private String getData() {
        /*
        * returns next String in Queue
        * */
        waitForData(1);
        return clientsMap.get(getAddressAndPort()).poll();
    }

    private List<String> getDataAsList(int howMany) {
        /*
        * returns List of n next Strings
        * */

        List<String> data = new ArrayList<>();
        waitForData(howMany);
        for (int i = 0; i < howMany; i++) {
            data.add(clientsMap.get(getAddressAndPort()).poll());
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

    public void sendData(int data) {
        sendData(String.valueOf(data));
    }

    private void waitForData(int howMany) {
        while (clientsMap.get(getAddressAndPort()).size() < howMany) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int gcdFromList(List<Integer> list) {
        return list.stream().reduce(0, this::gcd);
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
        return Server.FIRST_INPUTS
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
    }

    public int maxPower(int x) {
        int k = 1;
        while (Math.pow(k, 2) < x) {
            k++;
        }
        return k-1;
    }



    @Override
    public void run() {
        // wczytanie jednej linii tekstu i odeslanie jej
        String data = getData();
        sendData(data);

        // 2. Wyślij największy wspólny dizelnik liczb otrzymanych przez Twoj serwer od wszystkich
        // klientów w ich pierwszych komunikatach (tj., w pkt. 1 zadania).
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendData(gcdFromList(getFirstInputs()));

        // 3. Odbierz napis. Usuń z niego wszystkie wystąpienia 6 i odeślij wynik.
        data = getData();
        data = data.replace("6", "");
        sendData(data);

        // 4. W 5 kolejnych liniach odbierz 5 liczb(y) naturalnych(e). Policz sumę tych liczb i odeślij.
        int sum = getDataAsList(5).stream().map(Integer::parseInt).reduce(0, Integer::sum);
        data = String.valueOf(sum);
        sendData(data);

        // 5. Wyślij numer portu z którego się komunikujesz.
        sendData(Server.SERVER_SOCKET_PORT);

        // 6. Odbierz liczbę naturalną x. Oblicz największą liczbę naturalną k, taką, że k podniesione do potęgi 2 jest nie większe niż wartość x. Odeślij wartość k.
        data = getData();
        int k = maxPower(Integer.parseInt(data));
        sendData(k);

        // odbierz finalną flagę
        data = getData();
        System.err.println(data);

    // 534773679
    // 534773679
    // 534773679

    }
}
