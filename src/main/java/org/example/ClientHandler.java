package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    //Keeps track of all clients so that whenever one sends a message, you can loop through all clients and send the message to the others
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    //Reads data (more specifically messages) sent from the client
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    @Override
    public void run(){
        //Listen for messages
        String messageFromClient;

        while(socket.isConnected()){
            try {
                messageFromClient = bufferedReader.readLine();
            }
        }
    }
}
