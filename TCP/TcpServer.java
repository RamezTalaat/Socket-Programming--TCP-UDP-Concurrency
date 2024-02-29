package TCP;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    private static int serverPort = 5005;

    public static void main(String[] args) {

        try {
            // openning server
            ServerSocket socket = new ServerSocket(serverPort);
            System.out.println("----> Server Started Successfully");

            while (true) {
                // waiting for connection
                System.out.println("----> Waiting for connections");
                Socket clientSocket = socket.accept();

                //New Client thread instantiatino 
                Thread newClientThread = new Thread(new ClientHandler(clientSocket));
                newClientThread.start();

            }

            // // receiving message
            // DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));

            // String message = "";
            // while (!message.equals("end")) {
            //     message = in.readUTF();
            //     System.out.println("Message from client of port " + client.getPort() + ": ( " + message + " )");
            // }

            //socket.close();
            //System.out.println("----> Server Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

                String message = "";
                while (!message.equals("end")) {
                    message = in.readUTF();
                    System.out
                            .println("Message from client of port " + clientSocket.getPort() + ": ( " + message + " )");
                }

                clientSocket.close();
                System.out.println("----> Connection closed for client of port " + clientSocket.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
