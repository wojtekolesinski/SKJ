package skj.adam.main;

import java.io.*;
import java.net.Socket;


/*Zadanie polega na komunikacji z użyciem protokołu TCP/UDP. Protokół komunikacji jest tekstowy. W przypadku protokołu
TCP odebranie pojedyńczej odpowiedzi polega na wczytaniu jedej linii tekstu; analogicznie, wysłanie pojedyńczej
odpowiedzi polega na wysłaniu jednej linii tekstu. Natomiast w przybadku protokołu UDP odebranie pojedyńczej odpowiedzi
polega na wczytaniu pojedyńczego datagramu z bajtami reprezentującymi jedną linię tekstu; analogicznie, wysłanie
pojedyńczej odpowiedzi polega na wysłaniu jednego datagramu z bajtami reprezentującymi jedną linię tekstu.
W szczególności wszystkie liczby powinny najpierw zamieniane na tekstową reprezentację. Twoją początkową flagą
jest liczba 103590. Flagę tę należy wysłać do serwera TCP aby zainicjować komunikację.


        Serwer TCP działa na adresie 172.21.48.48 i porcie 14168.


        1. Utwórz gniazdo UDP. Wyślij do serwera TCP jedną linię w formacie adres:port, gdzie adres jest adresem IP
        Twojego gniazda UDP, a port jego numerem portu. Wykonaj poniższe polecenia używając protokołu UDP i
        gniazda, które właśnie utworzyłeś.

        2. Odbierz jedną linijkę tekstu. Skonkatenuj tekst 3 razy z samym sobą i odeślij wynik.

        3. Odbierz napis. Usuń z niego wszystkie wystąpienia 9 i odeślij wynik.

        4. W 5 kolejnych liniach odbierz 5 liczb(y) naturalnych(e). Policz sumę tych liczb i odeślij.


        Odbierz finalną flagę i wpisz ją poniżej.*/

public class Main {
    public static final String IP = "172.23.129.180";
    public static final int PORT = 9873;
    public static final String SERVER_IP = "172.21.48.48";
    public static final int SERVER_PORT = 14168;
    public static final int FLAG = 103590;

    public static void main(String[] args) throws IOException {
        //przygotowanie strumieni do wysłania i odebrania danych
        Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
        DataOutputStream outToServer = new
                DataOutputStream(clientSocket.getOutputStream());

        //wysłanie danych na serwer
        outToServer.writeBytes(FLAG+"\n");
        outToServer.writeBytes(IP+":"+PORT+"\n");

        clientSocket.close();
    }
}
