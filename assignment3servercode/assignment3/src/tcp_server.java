import java.io.*;
import java.net.*;

public class tcp_server {
    public static void main(String[] args) {
        //demo/random port number that we assigned 
        final int PORT = 12845;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            //prints the port number that the client is listening through 
            System.out.println("Server is listening on port " + PORT + "...");

            while (true) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a new thread to handle the client
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

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            // Read the message from the client
            String clientMessage = in.readLine();
            System.out.println("Client says: " + clientMessage);

            // Capitalize the message
            String modifiedMessage = clientMessage.toUpperCase();

            // Send the modified message back to the client
            out.println(modifiedMessage);
            System.out.println("Modified message sent to client: " + modifiedMessage);

            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}