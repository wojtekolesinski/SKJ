package clientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {
    public static void main(String[]args) throws Exception{
        String sentence;
        String modifiedSentence;

        //wczytanie zdania, które użytkownik chce przesłać na serwer
        BufferedReader inFromUser = new BufferedReader(new
                InputStreamReader(System.in));
        sentence = inFromUser.readLine();

        //przygotowanie strumieni do wysłania i odebrania danych
        Socket clientSocket = new Socket("127.0.0.1", 6789);
        DataOutputStream outToServer = new
                DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new
                InputStreamReader(clientSocket.getInputStream()));

        //wysłanie danych na serwer
        outToServer.writeBytes(sentence+"\n");

        //wczytanie odpowiedzi
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }
}