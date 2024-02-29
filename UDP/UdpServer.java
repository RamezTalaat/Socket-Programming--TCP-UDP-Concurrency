package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer {

    private static int serverPort = 5010;

    public static void main(String[] args) {

        try {
            // openning server
            DatagramSocket serverSocket = new DatagramSocket(serverPort);
            System.out.println("----> Server Started Successfully");

            // receiving message
            System.out.println("----> Server waiting for packets");

            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(request);
                // DatagramPacket reply = new DatagramPacket(request.getData(),
                // request.getLength(), request.getAddress(),
                // request.getPort());

                String message = new String(request.getData(), 0, request.getLength());
                System.out.println("----> Message recieved from ip = " + request.getAddress() + " , port number = "
                        + request.getPort() + " , Message = ( " + message + " )");

                // serverSocket.send(reply);
            }

            // serverSocket.close();
            // System.out.println("----> Server Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}