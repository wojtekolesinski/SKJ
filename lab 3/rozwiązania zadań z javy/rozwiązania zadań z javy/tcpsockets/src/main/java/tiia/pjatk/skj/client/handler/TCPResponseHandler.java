package tiia.pjatk.skj.client.handler;

import tiia.pjatk.skj.shared.Response;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class TCPResponseHandler implements ResponseHandler {

    private static final Logger logger = Logger.getLogger(TCPResponseHandler.class.getName());

    private Socket socket;

    public TCPResponseHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void handleResponse() throws IOException {
        logger.info("Handling response...");
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        int statusCode = inputStream.readInt();
        logger.info("Status code: "+statusCode);
        int dataLength = inputStream.readInt();
        logger.info("Data length: "+dataLength);

        if (statusCode == Response.FOUND_STATUS && dataLength > 0) {
            int read;
            StringBuilder msg = new StringBuilder();
            while ((read = inputStream.read()) != -1) {
                msg.append((char) read);
            }
            logger.info("Received data: " + msg);
        }
        socket.close();
    }

}
