package Server;

import Client.WorkingPortsCounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        IntStream.rangeClosed(WorkingPortsCounter.getFirstPort(), WorkingPortsCounter.getLastPort())
                .forEach(port -> executorService.submit(new Server(port, WorkingPortsCounter.getAddress())));


    }
}
