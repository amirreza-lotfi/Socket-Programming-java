import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static int SERVER_PORT = 1080;

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println("New Client ("+clientHandler.getUsername()+") Is Connected.");
                Thread newThread = new Thread(clientHandler);
                newThread.start();
            }
        }catch (Exception e){
            shutDownServerSocket();
        }
    }

    public void shutDownServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
