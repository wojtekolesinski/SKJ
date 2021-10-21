package clientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[]args) throws Exception{
        String clientSentence;
        String capitalizedSentence;

        //Serwer nasłuchuje na porcie 6789
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while(true){
            //zaakceptowanie połączenia przez serwer
            Socket connectionSocket = welcomeSocket.accept();
            //odczytanie danych od klienta
            BufferedReader inFromClient = new BufferedReader(new
                InputStreamReader(connectionSocket.getInputStream()));
            clientSentence = inFromClient.readLine();

            //Przygotowanie odpowiedzi
            DataOutputStream outToClient = new
                DataOutputStream(connectionSocket.getOutputStream());
            capitalizedSentence = clientSentence.toUpperCase()+"\n";

            //wysłanie odpowiedzi
            outToClient.writeBytes(capitalizedSentence);
        }
    }
}

