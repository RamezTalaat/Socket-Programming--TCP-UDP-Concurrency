package UDP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient2 {
    private static int serverPort = 5010;

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            byte[] buffer = new byte[1000];

            // For reading input from terminal
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message = "";
            do {
                System.out.println("Enter a message:");
                message = reader.readLine();
                // Converting the message to bytes
                buffer = message.getBytes();

                DatagramPacket request = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"),
                        serverPort);
                clientSocket.send(request);
            } while (!message.equals("end"));

            // Converting the message to bytes
            buffer = message.getBytes();

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
