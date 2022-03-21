import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage(username+" has entered the chat!!!");
        }catch (Exception e){
            closeEveryThings();
        }
    }

    public void closeEveryThings(){
        removeClientHandler();
        try {
            if(bufferedReader != null)
                bufferedReader.close();
            if(bufferedWriter!=null)
                bufferedWriter.close();
            if(socket!=null)
                socket.isClosed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void broadcastMessage(String message){
        for(ClientHandler c: clientHandlers){
            try{
                if(!c.username.equals(username)) {
                    c.bufferedWriter.write(message);
                    c.bufferedWriter.newLine();
                    c.bufferedWriter.flush();
                }
            }catch (Exception ex){
                closeEveryThings();
            }
         }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage(username+" has left the chat!!");
    }
    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        String messageFromClient;
        while(!socket.isClosed()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }catch (Exception e){
                closeEveryThings();
                break;
            }
        }
    }

}
