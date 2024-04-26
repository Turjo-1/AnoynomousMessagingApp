package testpackage;

import java.io.*;
import java.net.*;
import java.util.Base64;

public class run {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change this to your server's IP address or hostname
        int serverPort = 12345; // Change this to your server's port number
        
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            
            // Sending a text message
            String textMessage = "Hello, this is a text message";
            writer.println("T" + textMessage); // Prefix 'T' indicates a text message
            
            // Receiving response from the server
            String response = reader.readLine();
            System.out.println("Server response: " + response);
            
            // Sending an image (encoded in base64)
            String imagePath = "path/to/your/image.jpg";
            String base64Image = encodeImage(imagePath);
            writer.println("I" + base64Image); // Prefix 'I' indicates an image message
            
            // Receiving response from the server
            response = reader.readLine();
            System.out.println("Server response: " + response);
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to encode an image to base64 string
    private static String encodeImage(String imagePath) {
        try {
            File file = new File(imagePath);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            imageInFile.close();
            return Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
}
}
}
