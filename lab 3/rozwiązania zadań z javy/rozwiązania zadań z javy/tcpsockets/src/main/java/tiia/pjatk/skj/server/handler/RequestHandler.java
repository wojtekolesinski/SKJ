package tiia.pjatk.skj.server.handler;

public interface RequestHandler extends Runnable{

    default void run() {
        handleRequest();
    }

    void handleRequest();

}
