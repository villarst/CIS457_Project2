import java.io.*;
import java.net.*;
 
/**
 * This class is responsible for reading server's input and printing it
 * to the console. It runs in an infinite loop until the client disconnects
 * from the server.
 */
public class readInput extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private cClient client;
 
    // Constructor that takes in a socket and client and sets those instance
    // variables. It also is used for taking in input and setting that to a
    // buffered reader.
    public readInput(Socket socket, cClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    // The run method waits for a response and until "quit" is sent, 
    // will run indefinitely. 
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
 
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                break;
            }
        }
    }
}
