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
    private static final String DISCONNECT = ".disconnect";

    /**
     *
     * @param port integer value.
     */
    public Server(int port) {
        try {
            server = new ServerSocket(port);    // Bind
            sock = server.accept();             // Accept
            System.out.println("Client connected: " + sock);

            // Handle connection
            stream = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
            boolean active = true;
            while (active) {
                try {
                    String data = stream.readUTF(); // Conversion
                    System.out.println("Got: " + data);

                    // Check for disconnect
                    active = !data.equals(DISCONNECT);
                }
                catch (IOException ioe) {
                    active = false;
                    System.out.println("Client disconnected: " + sock);
                }
            }

            // Close connection
            closeConnection();
        }
        catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Close connections at end of transfer.
     */
    public void closeConnection() {
        try {
            if (sock != null)
                sock.close();
            if (stream != null)
                stream.close();
        }
        catch (IOException ioe) {
            System.out.println("Exception: " + ioe.getMessage());
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
