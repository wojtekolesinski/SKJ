package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ClientThread implements Callable<Integer> {
    private int flag;
    private int port;
    private String address;
    private CountDownLatch latch;

    public ClientThread(int flag, int port, String address, CountDownLatch latch) {
        this.flag = flag;
        this.port = port;
        this.address = address;
        this.latch = latch;
    }

    @Override
    public Integer call() throws Exception {
        String response = null;
        try (
                Socket clientSocket = new Socket(address, port);
                PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            if (clientSocket.isConnected())
                clientSocket.setSoTimeout(1000);
            outToServer.println(flag);
            response = inFromServer.readLine();
            WorkingPortsCounter.workingPorts.getAndIncrement();

        } catch (ConnectException e) {
            System.out.println("Timeout " + latch.getCount());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            return response == null ? null : Integer.parseInt(response);
        }
    }
}
