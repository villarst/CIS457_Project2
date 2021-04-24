import java.io.*;
import java.net.*;
import java.util.*;
 
/**
 * This is the chat server program that monitors
 * and allows mulitple clients to communicate
 * between eachother.
 * Press Ctrl + C to quit the program.
 */
public class cServer {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<userInput> userInputs = new HashSet<>();
 
    // This constructor is used to assign the int port variable to the assigned port "8976".
    public cServer(int port) {
        this.port = port;
    }
 
    // This method sets a server socket and uses the port variable "8976".
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Chat Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
 
                userInput newUser = new userInput(socket, this);
                userInputs.add(newUser);
                newUser.start();
 
            }
 
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    // Main driver method that assigns portnum and creates an instance of cServer.
    public static void main(String[] args) { 
        String portNum = "8976";
        int port = Integer.parseInt(portNum);
        cServer server = new cServer(port);
        server.execute();
    }
 
    // Delivers a message from one user to others (broadcasting)
    void broadcast(String message, userInput excludeUser) {
        for (userInput aUser : userInputs) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
 
    // Stores username of the newly connected client.
    void addUserName(String userName) {
        userNames.add(userName);
    }
 
    // When a client is disconneted, removes the associated username and userInput.
    void removeUser(String userName, userInput aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userInputs.remove(aUser);
            System.out.println("The user " + userName + " quitted. Press Ctrl + C to terminate the server.");
        }
    }
 
    // Getter to return the username of a user connected to the server.
    Set<String> getUserNames() {
        return this.userNames;
    }
 
    // Returns true if there are other users connected (not count the currently connected user).
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
