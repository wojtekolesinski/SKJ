package tiia.pjatk.skj.server.receiver;

import tiia.pjatk.skj.server.handler.RequestHandler;

import java.io.IOException;

public interface RequestReceiver {

    void startListening() throws IOException;

    RequestHandler acceptConnection() throws IOException;

}
