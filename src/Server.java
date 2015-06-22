/**
 * Server.java
 *
 * A simple server program to handle chat clients.
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    private Socket sock = null;
    private ServerSocket serverSock = null;
    private DataOutputStream streamOut = null;

    private ArrayList<ConnectionThread> connections = null;

    private static final int NUM_ARGS = 1;

    /**
     * Main loop to run the server. Accepts incoming connections and creates
     * thread to handle each.
     * @param port Port number for the server to run on.
     */
    public Server(int port) {
        connections = new ArrayList<ConnectionThread>();

        try {
            serverSock = new ServerSocket(port);    // Bind
            // Accept loop
            while ((sock = serverSock.accept()) != null) {
                // Create the thread
                ConnectionThread thread = new ConnectionThread(this, sock);
                connections.add(thread);
            }

            // Close connection
            closeConnection();
        }
        catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Send message to all clients.
     * THIS COULD POSSIBLY GO IN THE THREAD ITSELF (the sending part).
     */
    public synchronized void sendAll(String message, int port) throws IOException {
        for (ConnectionThread connection : connections) {
            try {
                streamOut = new DataOutputStream(connection.getSock().getOutputStream());
                streamOut.writeUTF("" + port + ": " + message);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage());
            }

        }
    }

    /**
     * Delete a connection from list after disconnect.
     * @param connection ConnectionThread to be removed.
     */
    public synchronized void removeConnection(ConnectionThread connection) {
        connections.remove(connection);
    }

    /**
     * Close connections at end of transfer.
     */
    public void closeConnection() {
        try {
            if (sock != null)
                sock.close();
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
