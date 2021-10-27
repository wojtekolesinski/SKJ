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
        socket = new ServerSocket(getPort());
        }

    public RequestHandler acceptConnection() throws IOException{
        return new TCPRequestHandler(socket.accept());
    }

    public int getPort() {
        return port;
    }
}
