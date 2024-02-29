package TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClient1 {
    private static String serverIp = "127.0.0.1";
    private static int serverPort = 5005;

    public static void main(String[] args) {
        try {
            System.out.println("----> Client1 Started Successfully");
            Socket server = new Socket(serverIp, serverPort);
            System.out.println("----> Client1 connected Successfully");
            System.out.println("Server port number: " + server.getPort());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(server.getOutputStream());

            String message = "";
            do {

                System.out.println("Enter a message:");
                message = reader.readLine();
                out.writeUTF(message);
                System.out.println("----> Message sent to server");
            } while (!message.equals("end"));

            System.out.println("Server port number: " + server.getPort());
            server.close();
            System.out.println("----> Connection closed Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
