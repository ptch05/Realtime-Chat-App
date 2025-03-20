import java.net.ServerSocket;

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
            while(!serverSocket.isClosed()){
                //Blocking method. Program is halted here until a client connects
                Server socket = serverSocket.accept();
                System.out.println("A new client has connected");

                //Will implement runnable. runnable implemented on a class who's instances will each be executed on a separate thread (multithreading allows multiple clients)
                ClientHandler clientHandler = new ClientHandler();
            }
        }

    }
}