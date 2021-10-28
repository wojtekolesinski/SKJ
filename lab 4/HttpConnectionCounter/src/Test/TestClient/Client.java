package Test.TestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static String address = "127.0.0.1";
    private static int port = 80;

    public static void main(String[] args) {
        try (Socket socket = new Socket(address, port)) {
            try (
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

            ) {
                Scanner scanner = new Scanner(System.in);

                String line;
                outToServer.println(scanner.nextLine());

                line = inFromServer.readLine();
                System.out.println("SERVER RESPONSE: " + line);


            }



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
