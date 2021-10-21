package tiia.pjatk.skj;

import org.kohsuke.args4j.Option;

public class CommandLineOptions {

    public enum Mode {SERVER, CLIENT};
    public enum Protocol {TCP};

    @Option(name = "-m", required = true, usage = "mode")
    private Mode mode;

    @Option(name = "-p", required = true, usage = "transport layer protocol")
    private Protocol streamType;

    @Option(name = "-c", usage = "command to send to the server")
    private String command;

    @Option(name = "-server-ip", usage = "server address")
    private String serverIP;

    @Option(name = "-server-port", usage = "server port")
    private int serverPort;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Protocol getStreamType() {
        return streamType;
    }

    public void setStreamType(Protocol streamType) {
        this.streamType = streamType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
