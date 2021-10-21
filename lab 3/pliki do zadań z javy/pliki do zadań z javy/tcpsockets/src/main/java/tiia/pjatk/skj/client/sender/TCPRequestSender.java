package tiia.pjatk.skj.client.sender;

import tiia.pjatk.skj.client.handler.ResponseHandler;
import tiia.pjatk.skj.client.handler.TCPResponseHandler;
import tiia.pjatk.skj.shared.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class TCPRequestSender implements RequestSender {

    private static final Logger logger = Logger.getLogger(TCPRequestSender.class.getName());

    String server;
    int port;

    public TCPRequestSender(String server, int port){
        this.server = server;
        this.port = port;
    }

    @Override
    public ResponseHandler sendRequest(String command) throws IOException {
        StringTokenizer commandParser = new StringTokenizer(command);

        Request.Command commandType = Request.Command.getCommandByName(commandParser.nextToken());
        Socket clientSocket;
        DataOutputStream outToServer;
        ResponseHandler responseHandler = null;

        if(commandType != null){
            switch(commandType){
                case GET:
                    if(commandParser.hasMoreTokens()) {
                        clientSocket = connect();
                        outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        outToServer.writeByte(commandType.getCode());
                        outToServer.writeUTF(commandParser.nextToken());
                        responseHandler = new TCPResponseHandler(clientSocket);
                    }
					else{
						logger.info("File not specified - command not sent :(");
					}
                    break;
            }
        }
        else{
            logger.info("Something went wrong - command not sent :(");
        }

        return responseHandler;
    }

    private Socket connect() throws IOException {
        return new Socket(server, port);
        //TODO 4 zaimplementuj metodę w taki sposób, aby zwracała odpowiednią wartość
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}
