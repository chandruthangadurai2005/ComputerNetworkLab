import java.io.*;
import java.net.*;

public class TCPEchoClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String userInput;
            System.out.println("Type messages to send to the server (type 'bye' to quit):");
            while ((userInput = keyboard.readLine()) != null) {
                out.println(userInput);
                String response = in.readLine();
                System.out.println(response);
                if ("bye".equalsIgnoreCase(userInput.trim())) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
