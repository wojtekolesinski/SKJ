package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    static int port = 23155;
    static String address = "172.21.48.141";
    static int flag = 177938;



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


    public static void main(String[] args) {
        try (
                Socket socket = new Socket(address, port);
                PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            outToServer.println(flag);
            String message = Server.address + ":" + Server.port;
            outToServer.println(message);



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
