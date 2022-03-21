import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;
import java.util.Scanner;


public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    Client(Socket socket, String username){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }catch (Exception e){
            closeEveryThings();
        }
    }
    public void closeEveryThings(){
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
    public void sendMassage(){
        try {
            //This method returns the time in millis

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String messageOfUser = scanner.nextLine();
                bufferedWriter.write(username + ": "+ messageOfUser);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (Exception e){
            closeEveryThings();
        }
    }
    public void getMessagesFromOtherUsers(){
        new Thread(() -> {
            try {

                while (socket.isConnected()) {
                    System.out.println(bufferedReader.readLine());
                }
            }catch(Exception e){
                closeEveryThings();
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username : ");
        String username = sc.nextLine();
        Socket socket = new Socket("localhost",Server.SERVER_PORT);
        Client client = new Client(socket,username);
        client.getMessagesFromOtherUsers();
        client.sendMassage();
    }
}
