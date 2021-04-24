import java.io.*;
import java.net.*;
 
/**
 * This class handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 */
public class userInput extends Thread {
    private Socket socket;
    private cServer server;
    private PrintWriter writer;
 
    // Constructor that sets the socket and server to their corresponding variables.
    public userInput(Socket socket, cServer server) {
        this.socket = socket;
        this.server = server;
    }
 
    // This method runs and takes in input and reads it to a buffered reader.
    // It also lets the server know a new user has connected. This method also
    // takes into account if the user has entered "quit".
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            String userName = reader.readLine();
            server.addUserName(userName);
 
            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);
 
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);
 
            } while (!clientMessage.equals("quit"));
 
            server.removeUser(userName, this);
            socket.close();
 
            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    // Sends a list of online users to the newly connected user.
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }
 
    // Sends a message to the client.
    void sendMessage(String message) {
        writer.println(message);
    }
}