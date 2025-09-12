import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SimpleSipClient {

    public static void main(String[] args) throws Exception {
        String sipServerIp = "127.0.0.1"; // Change to your SIP server IP
        int sipServerPort = 5060;          // SIP default port

        String fromUser = "alice";
        String toUser = "bob";
        String fromDomain = "example.com";
        String toDomain = "example.com";
        String callId = "1234567890@example.com";

        // Create a simple raw SIP INVITE message
        String sipInvite =
                "INVITE sip:" + toUser + "@" + toDomain + " SIP/2.0\r\n" +
                        "Via: SIP/2.0/UDP " + InetAddress.getLocalHost().getHostAddress() + ":5060;branch=z9hG4bK776asdhds\r\n" +
                        "Max-Forwards: 70\r\n" +
                        "From: \"" + fromUser + "\" <sip:" + fromUser + "@" + fromDomain + ">;tag=1928301774\r\n" +
                        "To: <sip:" + toUser + "@" + toDomain + ">\r\n" +
                        "Call-ID: " + callId + "\r\n" +
                        "CSeq: 1 INVITE\r\n" +
                        "Contact: <sip:" + fromUser + "@" + InetAddress.getLocalHost().getHostAddress() + ":5060>\r\n" +
                        "Content-Type: application/sdp\r\n" +
                        "Content-Length: 0\r\n" +
                        "\r\n";

        System.out.println("Sending SIP INVITE:\n" + sipInvite);

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(sipServerIp);

        byte[] buf = sipInvite.getBytes();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, sipServerPort);

        socket.send(packet);

        System.out.println("SIP INVITE sent to " + sipServerIp + ":" + sipServerPort);

        socket.close();
    }
}
