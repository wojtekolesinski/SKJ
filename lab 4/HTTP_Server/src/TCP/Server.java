package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static TCP.Util.*;

public class Server {
    static int port = 54678;
//    static String address = "10.13.58.2";
    static String address = "172.23.129.29";
    static List<Long> clientInputs;

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

    static {
        clientInputs = new ArrayList<>();
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            while (true) {
<<<<<<< HEAD
                System.out.println("im here");
=======
>>>>>>> 0b32880dcc4bd5f89d0cf0408486f7f130c909ae
                executorService.submit(new RequestHandler(serverSocket.accept()));
                System.out.println("me too");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addClientInput(long input) {
        clientInputs.add(input);
    }

    public static void printCI() {
        System.out.println(clientInputs);
    }

    public static long getGcd() {
        return gcdFromList(clientInputs);
    }

    public static long getClientInputsSum() {
        return clientInputs.stream().mapToLong(x->x).sum();
    }
}
