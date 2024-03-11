package AuctionUseCase;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class AuctionClient {
    private static String serverIp = "127.0.0.1";
    private static int serverPort = 5005;
    private static Socket server = null;

    public static void main(String[] args) {
        try {
            System.out.println("----> Client Started Successfully");
            server = new Socket(serverIp, serverPort);
            System.out.println("----> Client connected Successfully");
            System.out.println("Server port number: " + server.getPort());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(server.getOutputStream());

            DataInputStream in = new DataInputStream(new BufferedInputStream(server.getInputStream()));

            ClinetListener listener = new ClinetListener(in);
            Thread listenerThread = new Thread(listener);
            listenerThread.start();

            String message = "";
            do {
                System.out.println("Enter a message:");
                message = reader.readLine();
                out.writeUTF(message);
                System.out.println("----> Message sent to server");
            } while (!message.equals("end"));
            listenerThread.interrupt();

            // System.out.println("Server port number: " + server.getPort());
            server.close();
            System.out.println("----> Connection closed Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ClinetListener implements Runnable {

        private DataInputStream in ;
        public ClinetListener( DataInputStream input) {
            in = input;
        }

        @Override
        public void run() {

            try {
                
                while (!server.isClosed()) {
                    
                    String msg = in.readUTF();
                    System.out.println("New Message from server : " + msg);
                }
                return ;
            } catch (SocketException s) {
                // This exception is thrown when the server socket is closed
                System.out.println("Server has closed the connection.");
                
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in receiving message from server");
            }
        }

    }
}
