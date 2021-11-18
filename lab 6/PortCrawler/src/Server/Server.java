package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    public int port;
    public String address;
    private boolean works;



    public Server(int port, String address) {
        this.port = port;
        this.address = address;
        this.works = Math.random() > .5;
    }


    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Starting server at port " + port + " which " + (works ? "works":"doesn't work"));

            ExecutorService executorService = Executors.newSingleThreadExecutor();


            while (true) {
                if (works)
                    executorService.submit(new RequestHandler(server.accept()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
