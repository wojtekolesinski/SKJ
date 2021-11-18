package Client;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class WorkingPortsCounter {
    private static int firstPort = 31249;
    private static int lastPort = 31754;
    private static String address = "172.21.48.191";
    private static int flag = 509166;
    static AtomicInteger workingPorts;

    static {
        workingPorts = new AtomicInteger(0);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threads = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(lastPort - firstPort + 1);

        List<Callable<Integer>> clients =  IntStream.rangeClosed(firstPort, lastPort)
                .mapToObj(x -> new ClientThread(flag, x, address, latch))
                .collect(Collectors.toList());

        List<Future<Integer>> results = threads.invokeAll(clients);

//        boolean terminated = threads.awaitTermination(15, TimeUnit.SECONDS);
//
//        if (!terminated) {
//            System.out.println("Przerwanie przed czasem");
//
//        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        results.stream().filter(f -> !f.isCancelled()).mapToInt(integerFuture -> {
            try {
                return integerFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return -1;
        }).filter(x -> x != -1)
//                .forEach(System.out::println);
                .forEach(System.out::println);

        System.out.println(workingPorts.get());
//        System.out.println(X);
    }
}
