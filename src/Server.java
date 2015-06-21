/**
 * Server.java
 *
 * A simple server program to handle chat clients.
 */

import java.net.*;
import java.io.*;

public class Server {
    private Socket sock = null;
    private ServerSocket server = null;
    private DataInputStream stream = null;

    private static final int NUM_ARGS = 1;

    /**
     *
     * @param port integer value.
     */
    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started...");
            System.out.println("Server waiting...");
            sock = server.accept();
            System.out.println("connected!");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Validate arguments and start server.
     */
    public static void main(String args[]) {
        Server server = null;

        // TODO: Validate command line arguments with flags
        // Check length
        if (args.length != NUM_ARGS) {
            System.out.println("Usage: java Server [portNum]");
            System.exit(0);
        }
        server = new Server(Integer.parseInt(args[0]));
    }
}
