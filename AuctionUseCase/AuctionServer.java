package AuctionUseCase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AuctionServer {

    private static int serverPort = 5005;
    private static ArrayList<ClientHandler> clientsList = new ArrayList<ClientHandler>();

    public static void main(String[] args) {

        try {
            // openning server
            ServerSocket socket = new ServerSocket(serverPort);
            System.out.println("----> Server Started Successfully");

            while (true) {
                // waiting for connection
                System.out.println("----> Waiting for connections , Number of current clients = " + clientsList.size());
                Socket clientSocket = socket.accept();

                //New Client thread instantiation
                ClientHandler newClient = new ClientHandler(clientSocket);

                Thread newClientThread = new Thread(newClient);
                newClientThread.start();

                //Add new client to the list of clients
                clientsList.add(newClient);

            }

            
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
                    this.send(message);
                }

                //clientSocket.close();
                // System.out.println("----> Connection closed for client of port " + clientSocket.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void send(String msg){
            try{
                for (ClientHandler clientHandler : clientsList) {
                
                    if(clientHandler.clientSocket !=  this.clientSocket){ //to not send the message again to the sender
                        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientHandler.clientSocket.getOutputStream()));
                        out.writeUTF(msg);
                        out.flush();
                        //out.close();
                    }
                }
                
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Could not send message to client " /*+ clientHandler.clientSocket.toString()*/);
            }
            
        }

    }
}
