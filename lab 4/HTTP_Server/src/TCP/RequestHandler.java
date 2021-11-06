package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {

    private Socket clientSocket;



    /* Zadanie polega na komunikacji z użyciem protokołu TCP. Protokół komunikacji jest tekstowy: odebranie pojedyńczej
    odpowiedzi polega na wczytaniu jedej linii tekstu; analogicznie, wysłanie pojedyńczej odpowiedzi polega na wysłaniu
    jednej linii tekstu. W szczególności wszystkie liczby powinny być przesyłane w tekstowej reprezentacji.
    Twoją początkową flagą jest liczba 177938. Flagę tę należy wysłać do serwera aby zainicjować komunikację.

    Serwer TCP działa na adresie 172.21.48.141 i porcie 23155. Użyj protokołu TCP do komunikacji.

    1. Wyślij do serwera jedną linię w formacie adres:port, gdzie adres jest adresem IP Twojego serwera, a port jego
    numerem portu. Twój serwer powinien móc obsługiwać wielu kientów jednocześnie. Komunikacja z każdym z klientów
    zaczyna się od wczytania od klienta jednej lini tekstu i odesłania jej do klienta, a następnie wykonania poniższych
    zadań dla każdego klienta. Uwaga! nie wszyscy klienci muszą wykonać cały protokół, ale należy założyć,
    że przynajmniej jeden z nich go wykona oraz wszyscy, którzy go wykonają zwrócą tę samą finalną flagę.

    2. W 5 kolejnych liniach odbierz 5 liczb(y) naturalne(ych). Policz ich największy wspólny dzielnik i wynik odeślij.

    3. Wyślij numer portu z którego się komunikujesz.

    4. Wyślij największy wspólny dizelnik liczb otrzymanych przez Twoj serwer od wszystkich klientów w ich pierwszych komunikatach (tj., w pkt. 1 zadania).

    5. Odbierz napis. Usuń z niego wszystkie wystąpienia 4 i odeślij wynik.

    6. Wyślij sumę liczb otrzymanych przez Twoj serwer od wszystkich klientów w ich pierwszych komunikatach (tj., w pkt. 1 zadania).

    Odbierz finalną flagę i wpisz ją poniżej.*/

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
//        System.out.println("sajdgf");
        try {
            clientSocket.setSoTimeout(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public long gcd(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }


    @Override
    public void run() {
        try (
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String line = inFromClient.readLine();
            outToClient.println(line);
            System.out.println(line);
            Server.addClientInput(Long.parseLong(line));

            List<Long> numbers = new ArrayList<>();

            // 2. W 5 kolejnych liniach odbierz 5 liczb(y) naturalne(ych). Policz ich największy wspólny dzielnik i wynik odeślij.
            long l1 = Long.parseLong(inFromClient.readLine());
            long l2 = Long.parseLong(inFromClient.readLine());
            long l3 = Long.parseLong(inFromClient.readLine());
            long l4 = Long.parseLong(inFromClient.readLine());
            long l5 = Long.parseLong(inFromClient.readLine());
            System.out.println();
            System.out.println(l1);
            System.out.println(l2);
            System.out.println(l3);
            System.out.println(l4);
            System.out.println(l5);
            System.out.println();


            long gcdResult = gcd(gcd(gcd(l1, l2), gcd(l3, l4)), l5);
            outToClient.println(gcdResult);
            System.out.println(inFromClient.readLine());


            // 3. Wyślij numer portu z którego się komunikujesz.
            outToClient.println(Server.port);

            // 4. Wyślij największy wspólny dizelnik liczb otrzymanych przez Twoj serwer od wszystkich klientów w ich pierwszych komunikatach (tj., w pkt. 1 zadania).
            numbers = Server.getClientInputs();
            gcdResult = numbers.get(0);
            for (int i = 1; i < numbers.size(); i++) {
                gcdResult = gcd(gcdResult, numbers.get(i));
            }
//            System.out.println(inFromClient.readLine());

            outToClient.println(gcdResult);

            // 5. Odbierz napis. Usuń z niego wszystkie wystąpienia 4 i odeślij wynik.
            line = inFromClient.readLine();
            line = line.replace("4", "");
            outToClient.println(line);

            // 6. Wyślij sumę liczb otrzymanych przez Twoj serwer od wszystkich klientów w ich pierwszych komunikatach (tj., w pkt. 1 zadania).
            long sum = numbers.stream().mapToLong(x -> x).sum();
            outToClient.println(sum);

            String finalFlag = inFromClient.readLine();
            System.out.println("final:" + finalFlag);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
