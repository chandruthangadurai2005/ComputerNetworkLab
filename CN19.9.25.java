📦 1. TCPChatServer.java
import java.io.*;
import java.net.*;

public class TCPChatServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("Server started. Waiting for client...");
        Socket s = ss.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String msg;
        while (true) {
            msg = in.readLine();
            if (msg.equalsIgnoreCase("exit")) break;
            System.out.println("Client: " + msg);

            System.out.print("Server: ");
            String reply = console.readLine();
            out.println(reply);
        }
        s.close();
        ss.close();
    }
}

📦 2. TCPChatClient.java
import java.io.*;
import java.net.*;

public class TCPChatClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 1234);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String msg;
        while (true) {
            System.out.print("Client: ");
            msg = console.readLine();
            out.println(msg);
            if (msg.equalsIgnoreCase("exit")) break;

            String reply = in.readLine();
            System.out.println("Server: " + reply);
        }
        s.close();
    }
}

📦 3. TCPEchoServer.java
import java.io.*;
import java.net.*;

public class TCPEchoServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1235);
        Socket s = ss.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String msg;
        while ((msg = in.readLine()) != null) {
            System.out.println("Received: " + msg);
            out.println("Echo: " + msg);
        }

        s.close();
        ss.close();
    }
}

📦 4. TCPEchoClient.java
import java.io.*;
import java.net.*;

public class TCPEchoClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 1235);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String msg;
        while (true) {
            System.out.print("Enter message: ");
            msg = console.readLine();
            if (msg.equalsIgnoreCase("exit")) break;
            out.println(msg);
            System.out.println(in.readLine());
        }

        s.close();
    }
}

📦 5. UDPChatServer.java
import java.net.*;

public class UDPChatServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(1236);
        byte[] buffer = new byte[1024];
        BufferedReader console = new BufferedReader(new java.io.InputStreamReader(System.in));

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            String msg = new String(request.getData(), 0, request.getLength());
            System.out.println("Client: " + msg);
            if (msg.equalsIgnoreCase("exit")) break;

            System.out.print("Server: ");
            String reply = console.readLine();
            DatagramPacket response = new DatagramPacket(
                reply.getBytes(), reply.length(), request.getAddress(), request.getPort()
            );
            socket.send(response);
        }

        socket.close();
    }
}

📦 6. UDPChatClient.java
import java.net.*;

public class UDPChatClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        BufferedReader console = new BufferedReader(new java.io.InputStreamReader(System.in));

        byte[] buffer = new byte[1024];
        while (true) {
            System.out.print("Client: ");
            String msg = console.readLine();
            DatagramPacket request = new DatagramPacket(
                msg.getBytes(), msg.length(), serverAddress, 1236
            );
            socket.send(request);

            if (msg.equalsIgnoreCase("exit")) break;

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            String reply = new String(response.getData(), 0, response.getLength());
            System.out.println("Server: " + reply);
        }

        socket.close();
    }
}

📦 7. UDPEchoServer.java
import java.net.*;

public class UDPEchoServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(1237);
        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + msg);

            String echo = "Echo: " + msg;
            DatagramPacket response = new DatagramPacket(
                echo.getBytes(), echo.length(), packet.getAddress(), packet.getPort()
            );
            socket.send(response);
        }
    }
}

📦 8. UDPEchoClient.java
import java.net.*;

public class UDPEchoClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");
        BufferedReader console = new BufferedReader(new java.io.InputStreamReader(System.in));

        byte[] buffer = new byte[1024];
        while (true) {
            System.out.print("Enter message: ");
            String msg = console.readLine();
            DatagramPacket packet = new DatagramPacket(
                msg.getBytes(), msg.length(), address, 1237
            );
            socket.send(packet);
            if (msg.equalsIgnoreCase("exit")) break;

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            String reply = new String(response.getData(), 0, response.getLength());
            System.out.println(reply);
        }

        socket.close();
    }
}
