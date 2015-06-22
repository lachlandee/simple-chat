/**
 * Thread to handle a client connection on the server.
 */

import java.net.*;
import java.io.*;
public class ConnectionThread extends Thread {
    private Socket sock = null;
    private Server server = null;
    private DataInputStream stream = null;

    private int port;

    public int getPort() {
        return port;
    }

    public Socket getSock() {
        return sock;
    }

    public ConnectionThread(Server server, Socket sock) {
        try {
            this.server = server;
            this.sock = sock;
            this.port = sock.getPort();
            stream = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
            start();
        }
        catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Handle the connection of individual client
     */
    public void run() {
        // Handle connection
        System.out.println("Client connected: " + sock);
        boolean active = true;
        while (active) {
            try {
                String data = stream.readUTF(); // Conversion
                System.out.println("Port " + port + ": " + data);
                // Send to other clients
                server.sendAll(data, port);
            }
            catch (IOException ioe) {
                active = false;
                System.out.println("Client disconnected: " + sock);
                server.removeConnection(this);
            }
        }
        closeConnection();
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
}
