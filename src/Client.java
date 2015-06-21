/**
 * Client.java
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private Socket sock = null;
    private Scanner kb = null;
    private DataOutputStream streamOut = null;

    private static final int ARG_LENGTH = 2;
    private static final String DISCONNECT = ".disconnect";

    public Client(String host, int port) {

        // Connect to server
        try {
            sock = new Socket(host, port);
            kb = new Scanner(System.in);
            streamOut = new DataOutputStream(sock.getOutputStream());
        }
        catch (UnknownHostException uhe) {
            System.out.println("Can not connect to host '" + uhe.getMessage() + "'");
            closeConnection();
            return;
        }
        catch (IOException ioe) {
            System.out.println("Exception: " + ioe.getMessage());
            closeConnection();
            return;
        }

        String data = "";
        // Loop until client wants to disconnect
        while (!data.equals(DISCONNECT)) {
            try {
                data = kb.nextLine();
                streamOut.writeUTF(data);
                streamOut.flush();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Close connections at end of transfer.
     */
    public void closeConnection() {
        try {
            if (kb != null)
                kb.close();
            if (sock != null)
                sock.close();
            if (streamOut != null)
                streamOut.close();
        }
        catch(IOException ioe){
            System.out.println("Exception: " + ioe.getMessage());
        }
    }

    public static void main (String args[]) {
        Client client = null;
        if (args.length != ARG_LENGTH) {
            System.out.println("Usage: java Client [host] [port]");
            System.exit(0);
        }
        client = new Client(args[0], Integer.parseInt(args[1]));
    }
}
