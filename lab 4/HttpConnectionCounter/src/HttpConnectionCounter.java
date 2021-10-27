import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpConnectionCounter {

    private static int responsiveServersCounter;

    public static void main(String[] args) {
        List<String> servers = List.of(
                "www.pja.edu.pl",
                "www.onet.pl",
                "bjklsadj"
        );

        CountDownLatch latch = new CountDownLatch(servers.size());

        ExecutorService threads = Executors.newFixedThreadPool(3);

        servers.forEach(s -> threads.submit(new HttpConnectionThread(s, latch)));

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println();
        System.out.println("Responsive servers number: " + getResponsiveServersCounter());

        threads.shutdown();

    }

    public static int getResponsiveServersCounter() {
        return responsiveServersCounter;
    }

    public static synchronized void increaseResponsiveServerCounter() {
        responsiveServersCounter++;
    }
}
