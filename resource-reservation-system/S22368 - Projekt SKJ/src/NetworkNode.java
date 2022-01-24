import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkNode extends Node {
    private boolean isRoot;
    private int root; // port węzła u korzenia
    private Map<String, Integer> resources;
    private List<Node> network;

    public NetworkNode(int id, String ip, int port, int parentPort, Map<String, Integer> resources) {
        super(id, ip, port, parentPort);
        this.resources = resources;

        if (getParentPort() == -1) {
            isRoot = true;
            root = getPort();
            network = new ArrayList<>();
        }
        else
            sayHello();

        listen();
    }

    public synchronized void addNode(Node node) {
        network.add(node);
        System.out.println(network);
    }

    private void sayHello() {
        try (
            Socket socket = new Socket(getIp(), getParentPort());
            PrintWriter outToParent = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromParent = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            outToParent.println(String.format("HELLO %d:%s:%d", getId(), getIp(), getPort()));
            root = Integer.parseInt(inFromParent.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != getParentPort()) {
            try (
                    Socket socket = new Socket(getIp(), root);
                    PrintWriter outToRoot = new PrintWriter(socket.getOutputStream(), true);
            ) {
                outToRoot.println(String.format("HELLO %d:%s:%d", getId(), getIp(), getPort()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRoot() {
        return isRoot;
    }

    public int getRoot() {
        return root;
    }

    public void listen() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try (ServerSocket serverSocket = new ServerSocket(getPort())) {
            while (true) {
                threadPool.submit(new TCPRequestHandler(serverSocket.accept(), this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
        System.out.println("Node " + this + " terminated");
    }

    public static void main(String[] args) {
        // parameter storage
        String gateway = null;
        int gatewayPort = -1;
        int port = 0;
        String identifier = null;
        Map<String, Integer> resources = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-ident":
                    identifier = args[++i];
                    break;
                case "-gateway":
                    String[] gatewayArray = args[++i].split(":");
                    gateway = gatewayArray[0];
                    gatewayPort = Integer.parseInt(gatewayArray[1]);
                    break;
                case "-tcpport":
                    port = Integer.parseInt(args[++i]);
                    break;
                default:
                    String[] data = args[i].split(":");
                    if (data.length != 2) {
                        System.err.println("Bad argument");
                        System.exit(1);
                    }
                    resources.put(data[0], Integer.parseInt(data[1]));
            }
        }
        NetworkNode node = new NetworkNode(
            Integer.parseInt(identifier),
            gateway == null ? "localhost" : gateway,
            port,
            gatewayPort,
            resources);
    }

    public List<Node> getChildrenFromRoot() {
        List<Node> children = new ArrayList<>();
        try (
            Socket socket = new Socket(getIp(), root);
            PrintWriter outToRoot = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromRoot = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            outToRoot.println("LIST");
            String line;
            while ((line = inFromRoot.readLine()) != null) {
                String[] data = line.split(":");
                children.add(new Node(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return children;
    }

    public List<Node> getNetwork() {
        return network;
    }

    public void terminateAllChildren() {
        for (Node child: network) {
            try (
                Socket socket = new Socket(child.getIp(), child.getPort());
                PrintWriter outToChild = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader inFromChild = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                outToChild.println("TERMINATED");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Node " + this + " terminated");
        System.exit(0);
    }

    public String allocate(String line) {
        String[] data = line.split(" ");
        StringBuilder result = new StringBuilder("ALLOCATED\n");
        for (int i = 1; i < data.length; i++) {
            String resource = data[i].split(":")[0];
            int quantity = Integer.parseInt(data[i].split(":")[1]);
            if (!resources.containsKey(resource)) {
                return "FAILED";
            } else if (resources.get(resource) < quantity) {
                return "FAILED";
            } else {
                result.append(data[i]+":"+getIp()+":"+getPort()+"\n");
            }
        }
        return result.toString();
    }
}

