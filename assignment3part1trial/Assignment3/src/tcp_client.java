import java.io.*;
import java.net.*;

public class tcp_client {
    public static void main(String[] args) {
        final String server_address = "localhost";
        final int server_port = 12845;

        try (
            Socket socket = new Socket(server_address, server_port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            //asks the user to enter the message that they want to send it to the server
            System.out.print("Message to server: ");
            //reads the user input from the console
            String userInput = consoleInput.readLine();

            // sends the user message to the server
            out.println(userInput);

            //receives and prints the message that is modified from the server
            String modifiedMessage = in.readLine();
            //prints the modified message that we get from the server
            System.out.println("Server response: " + modifiedMessage);

            //closes the client socket connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}