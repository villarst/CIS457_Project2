import java.net.*;
import java.io.*;
 
/**
 * This is the chat client program. This program is
 * used to allow different users on the "localhost"
 * and port number "8976" to be able to send messages
 * between eachother. To quit the program type "quit"
 */

public class cClient {
    private String hostname;
    private int port;
    private String userName;
 
    // Constructor that takes in a hostname ("localhost") and port number ("8976").
    public cClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
 
    // This method creates a socket and binds the hostname and port number to the socket.
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");
            new readInput(socket, this).start();
            new writeOutput(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
 
    // Setter that sets the username of the user.
    void setUserName(String userName) {
        this.userName = userName;
    }
 
    // Simple getter to return the username
    String getUserName() {
        return this.userName;
    }
    
    // Main method to run an instance of cClient. Running this mulitple times 
    // connects more users the server.
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = Integer.parseInt("8976");
        cClient client = new cClient(hostname, port);
        client.execute();
    }
}