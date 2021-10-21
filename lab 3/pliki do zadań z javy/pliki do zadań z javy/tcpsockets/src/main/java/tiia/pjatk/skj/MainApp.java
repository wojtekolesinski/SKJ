package tiia.pjatk.skj;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import tiia.pjatk.skj.client.handler.ResponseHandler;
import tiia.pjatk.skj.client.sender.RequestSender;
import tiia.pjatk.skj.client.sender.TCPRequestSender;
import tiia.pjatk.skj.server.receiver.RequestReceiver;
import tiia.pjatk.skj.server.receiver.TCPRequestReceiver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MainApp {

    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    public static void main(String args[]) {

        CommandLineOptions options = new CommandLineOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);

            if(options.getMode() == CommandLineOptions.Mode.SERVER) {
                logger.info("Starting in server mode");
                RequestReceiver receiver = new TCPRequestReceiver(options.getServerPort());
                try {
                    receiver.startListening();

                    ExecutorService executor = Executors.newFixedThreadPool(3);
                    while (true) {
                        executor.submit(receiver.acceptConnection());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(options.getMode() == CommandLineOptions.Mode.CLIENT && options.getServerIP() != null){
                logger.info("Starting in client mode");
                RequestSender sender = new TCPRequestSender(options.getServerIP(), options.getServerPort());
                try {
                    if(options.getCommand() != null) {
                        logger.info("Trying to send command \"" + options.getCommand() +"\" to "+options.getServerIP()+":"+options.getServerPort());
                        ResponseHandler responseHandler = sender.sendRequest(options.getCommand());
                        if(responseHandler != null) {
                            responseHandler.handleResponse();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }

}
