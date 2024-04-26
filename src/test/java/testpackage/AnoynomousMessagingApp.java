package testpackage;

import java.lang.annotation.*;
import java.io.*;
import java.net.*;
import java.util.Base64;
public class AnonymousMessagingApp {
    public static void main(String[] args) {
        int port = 12345; // Port number for communication
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                
                // Start a new thread to handle client communication
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                // Assume the first character determines the message type: 'T' for text, 'I' for image
                char messageType = inputLine.charAt(0);
                
                if (messageType == 'T') {
                    // Received text message
                    String message = inputLine.substring(1);
                    System.out.println("Received text message from client: " + message);
                    
                    // Echo the message back to the client
                    writer.println("Your message was received: " + message);
                } else if (messageType == 'I') {
                    // Received image encoded in base64
                    String base64Image = inputLine.substring(1);
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    
                    // Save the image to disk or perform any other processing
                    // For simplicity, let's just print the length of the image bytes
                    System.out.println("Received image with length: " + imageBytes.length);
                    
                    // Acknowledge the image receipt
                    writer.println("Image received successfully");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
 }
}
}

