/**
 * Handle reading of incoming data at client side
 */

import java.net.*;
import java.io.*;

public class ReaderThread extends Thread {
    private DataInputStream stream = null;
    private Socket sock = null;

    public ReaderThread(Socket sock) {
        try {
            this.sock = sock;
            stream = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
            start();
        }
        catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Handle incoming data
     */
    public void run() {
        boolean active = true;
        while (active) {
            try {
                String data = stream.readUTF(); // Conversion
                System.out.println(data);
            }
            catch (IOException ioe) {
                active = false;
                System.out.println("Server connection lost, please disconnect and try again.");
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
