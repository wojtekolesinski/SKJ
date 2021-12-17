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
        int index = 0;
        waitForData(index);
        String data = getDataAtIndex(index);
        sendData(data);
        index++;

        // 2. Wyślij największy wspólny dizelnik liczb otrzymanych przez Twoj serwer od wszystkich
        // klientów w ich pierwszych komunikatach (tj., w pkt. 1 zadania).
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendData(String.valueOf(gcdFromList(getFirstInputs())));

        // 3. Odbierz napis. Usuń z niego wszystkie wystąpienia 6 i odeślij wynik.
        waitForData(index);
        data = getDataAtIndex(index);
//        System.out.println(data);
        data = data.replace("6", "");
        sendData(data);
        index++;

        // 4. W 5 kolejnych liniach odbierz 5 liczb(y) naturalnych(e). Policz sumę tych liczb i odeślij.
        waitForData(index + 4);
//        List<Integer> numbers =
        int sum = getDataAtIndexRange(index, index+4).stream().map(Integer::parseInt).reduce(0, Integer::sum);
        data = String.valueOf(sum);
        sendData(data);
        index += 5;

        // 5. Wyślij numer portu z którego się komunikujesz.
        sendData(String.valueOf(Server.SERVER_SOCKET_PORT));
        // 6. Odbierz liczbę naturalną x. Oblicz największą liczbę naturalną k, taką, że k podniesione do potęgi 2 jest nie większe niż wartość x. Odeślij wartość k.
        waitForData(index);
        data = getDataAtIndex(index);
        int k = maxPower(Integer.parseInt(data));
        sendData(String.valueOf(k));
        index++;

        // odbierz finalną flagę
        waitForData(index);
        data = getDataAtIndex(index);
        System.err.println(data);

    // 534773679
    // 534773679
    // 534773679

    }
}
