package tiia.pjatk.skj.server.handler;

import tiia.pjatk.skj.shared.Request;
import tiia.pjatk.skj.shared.Response;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class TCPRequestHandler implements RequestHandler {

    private static final Logger logger = Logger.getLogger(TCPRequestHandler.class.getName());

    private Socket socket;

    public TCPRequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void handleRequest() {
        logger.info("Handling request...");

        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            Request.Command command = Request.Command.getCommandByCode(inputStream.readByte());

            if (command != null) {
                switch (command) {
                    case GET:
                        handleGetRequest(inputStream);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetRequest(DataInputStream inputStream){
        logger.info("Processing GET request");
        try {
            String filename = inputStream.readUTF();
            logger.info("Requested file: "+filename);

            File file = new File(filename);

            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeInt(Response.FOUND_STATUS);
            outputStream.writeInt((int)file.length());

            int read;
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(filename));
            while((read = fileInputStream.read()) != -1){
                outputStream.write(read);
            }
            fileInputStream.close();
            logger.info("File sent!");

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
