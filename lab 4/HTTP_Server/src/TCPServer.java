import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            ExecutorService threadService = Executors.newFixedThreadPool(3);

            while (true) {
                threadService.submit(new TCPHandler(serverSocket.accept()));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
