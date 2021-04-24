import java.io.*;
import java.net.*;
 
/**
 * This class is responsible for reading user's input and sending it
 * to the server. It runs in an infinite loop until the user types
 * "quit" to quit.
 */
public class writeOutput extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private cClient client;
 
    // This sets the socket an client variables of writeOutput.
    public writeOutput(Socket socket, cClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    // Creates a new console for the users name and prints it to the terminal.
    // This method also checks for if the user has typed in "quit".
    public void run() {
        Console console = System.console();
        String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);
        writer.println(userName);
        String text;
        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
 
        } while (!text.equals("quit"));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}