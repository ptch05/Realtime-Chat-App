package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    //Send messages to ClientHandler
    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend = scanner.nextLine(); // When user presses enter after typing something into the terminal, it's captured here
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    //New thread for each client for listening. Waiting for messages that are broadcasting from broadcastMessage
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            //Any code placed in here will be executed on a separate thread
            public void run() {
                String msgFromGroupChat;

                //only want to get messages from gc when connected to the server
                while(socket.isConnected()){
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start(); //Creating the object then just starting it
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter you username for the group chat: ");
        String username = scanner.nextLine();
    }
}
