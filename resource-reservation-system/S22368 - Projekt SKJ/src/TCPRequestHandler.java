import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPRequestHandler implements Runnable {
    private Socket socket;
    private NetworkNode node;

    public TCPRequestHandler(Socket socket, NetworkNode node) {
        this.socket = socket;
        this.node = node;
    }

    @Override
    public void run() {
        try (
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String line = inFromClient.readLine();
            String command = line.split(" ")[0];
            switch (command) {
                case "HELLO":
                    if (node.isRoot()) {
                        String[] data = line.split(" ")[1].split(":");
                        node.addNode(new Node(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2])));
                    }
                        outToClient.println(node.getRoot());
                    break;
                case "LIST":
                    if (node.isRoot()) {
                        for (Node child: node.getNetwork()) {
                            outToClient.println(child);
                        }
                        outToClient.println();
                    }

                    break;

                case "TERMINATE":
                    if (node.isRoot()) {
                        node.terminateAllChildren();
                    } else {
                        try (
                            Socket rootSocket = new Socket(node.getIp(), node.getRoot());
                            PrintWriter outToRoot = new PrintWriter(rootSocket.getOutputStream(), true);
                        ) {
                            outToRoot.println("TERMINATE");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "TERMINATED":
                    System.out.println("Node " + node + " terminated");
                    System.exit(0);

                    break;

                default:
                    outToClient.println(node.allocate(line));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
