package Prog1PL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    static String address = "172.21.48.177";
    static int port = 19799;
    static int flag = 101381;

    /*Zadanie polega na komunikacji z serwerem TCP. Protokół komunikacji jest tekstowy: odebranie pojedyńczej odpowiedzi od serwera polega na wczytaniu od serwera jedej linii tekstu; analogicznie, wysłanie pojedyńczej odpowiedzi do serwera polega na wysłaniu jednej linii tekstu. W szczególności wszystkie liczby powinny być przesyłane w tekstowej reprezentacji. Twoją początkową flagą jest liczba 101381. Flagę tę należy wysłać do serwera aby zainicjować komunikację.


Serwer TCP działa na adresie 172.21.48.177 i porcie 19799. Użyj protokołu TCP do komunikacji.


1. Wyślij do serwera numer portu z którego nawiązałeś komunikację.

2. Odbierz liczbę naturalną x od serwera. Oblicz największą liczbę naturalną k, taką, że k podniesione do potęgi 2 jest nie większe niż wartość x. Odeślij do serwera wartość k.

3. W 4 kolejnych liniach odbierz 4 liczb(y) naturalne(ych). Policz ich największy wspólny dzielnik i wynik odeślij do serwera.*/

//    1. Wyślij do serwera numer portu z którego nawiązałeś komunikację.
//
//2. Odbierz liczbę naturalną x od serwera. Oblicz największą liczbę naturalną k, taką, że k podniesione do potęgi
// 2 jest nie większe niż wartość x. Odeślij do serwera wartość k.
//
//3. W 4 kolejnych liniach odbierz 4 liczb(y) naturalne(ych). Policz ich największy wspólny dzielnik i
// wynik odeślij do serwera.

    public static int findK(int x) {
        int k = 0;
        while (Math.pow(k, 2) < x) k++;
        return k - 1;
    }

    public static int gcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(address, port)) {
            try (
                    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {

                outToServer.println(flag);
                outToServer.println(clientSocket.getLocalPort());

                int x = Integer.parseInt(inFromServer.readLine());
                System.out.println("Otrzymana liczba: " + x);

                outToServer.println(findK(x));

                int a, b, c, d;
                a = Integer.parseInt(inFromServer.readLine());
                b = Integer.parseInt(inFromServer.readLine());
                c = Integer.parseInt(inFromServer.readLine());
                d = Integer.parseInt(inFromServer.readLine());

                int myInt = gcd(gcd(a, b), gcd(c, d));

                System.out.printf("Dla liczb %d, %d, %d, %d największy wspólny dzielnik to: %d\n", a, b, c, d, myInt);
                outToServer.println(myInt);

                System.out.println(inFromServer.readLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
