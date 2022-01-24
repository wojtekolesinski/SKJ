public class Node {
    private int id;
    private String ip;
    private int port;
    private int parentPort;

    public Node(int id, String ip, int port, int parentPort) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.parentPort = parentPort;
    }

    public Node(int id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getParentPort() {
        return parentPort;
    }

    @Override
    public String toString() {
        return String.format("%d:%s:%d", id, ip, port);
    }
}
