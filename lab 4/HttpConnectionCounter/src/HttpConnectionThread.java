import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class HttpConnectionThread implements Runnable {

    private String serverName;
    private CountDownLatch latch;

    public HttpConnectionThread(String serverName, CountDownLatch latch) {
        this.serverName = serverName;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(serverName, 80);

            HttpConnectionCounter.increaseResponsiveServerCounter();

            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.println("GET / HTTP/1.1");
            outToServer.println("Host: " + serverName);
            outToServer.println();

            String line;
            while (!(line = inFromServer.readLine()).isEmpty()) {
                System.out.println(line);
            }

            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
