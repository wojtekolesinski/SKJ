package tiia.pjatk.skj.server.receiver;

import tiia.pjatk.skj.server.handler.RequestHandler;
import tiia.pjatk.skj.server.handler.TCPRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPRequestReceiver implements RequestReceiver {

    private ServerSocket socket;
    private int port;

    public TCPRequestReceiver(int port){
        this.port = port;
    }

    public void startListening() throws IOException {
        socket = new ServerSocket(port);
        RequestHandler handler = acceptConnection();
        while (true) handler.handleRequest();
        // TODO 1 zaimplementuj metodę w taki sposób, aby nie zmieniać nieoznaczonych fragmentów kodu
    }

    public RequestHandler acceptConnection() throws IOException{
        return new TCPRequestHandler(socket.accept()/* TODO 2 użyj odpowiedniej metody aby zaakceptować połączenie od klienta*/);
    }

    public int getPort() {
        return port;
    }
}
