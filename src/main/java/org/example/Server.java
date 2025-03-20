package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Server will listen to clients that'll connect and spawns a new thread to handle them
public class Server {

    // Object will be responsible for listening for incoming connections or clients and creating a socket object to communicate with them
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    //Keep server running
    public void startServer(){
        try {
            while (!serverSocket.isClosed()) {
                //Blocking method. Program is halted here until a client connects
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");

                //Will implement runnable. runnable implemented on a class whose instances will each be executed on a separate thread (multithreading allows multiple clients)
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                //To begin execution of thread
                thread.start();
            }
        } catch (IOException e) {

        }
    }

    //If error occurs, want to shut down server socket
    public void closeServerSocket(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}