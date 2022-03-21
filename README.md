# Socket-Programming-java
This is a simple chatroom that clients can communicate with each other. There are three classes in this project; Server, Client, and ClientHandler.<br/><br/>
# Server
The server class establishes the connection between the clients. When we click on the run button, its main method is executed and the results are displayed in the console.
In the main method, we first create a serverSocket object that is responsible for listening to clients and establishing a connection between the server and clients.
We must assign a port to the constructor of this class. Clients who want to connect to this server must use this port. 
In fact, this port will be like a password for them. Then we create an object from our server class and pass the server socket to it.<br/>
With startServer method, serverSocket starts working.In this method, the socket server waits for the clients to connect. This is done with the accept method. This method is a blocking method. That is, the next lines will not run until a client is connected.
After a client connects to the server (how to connect it will be described in detail), this method returns an object of type socket. Using this object,
communication can be established between the server and the client and data can be exchanged. 
Then we give this socket to the client handler class and create an object of this class with it. The
very important point is that the operations of this class must be performed in a separate thread. Otherwise
the server can only listen to one client and the rest of the clients go to the stack unless that user is disconnected
so that the rest of the clients are called from the stack, which is not to our liking. After the client is connected,
its information is displayed on the server console. If there is a problem with the server (the server is out of run or
an exception occurs), the shutDownServerSocket method is called.<br/>
# ClientHandler

As the name implies, this class manages clients. In such a way that sending or receiving messages from each client is such that it does 
not disrupt the performance of the entire program.
This class has a socket object that, as mentioned earlier, is used to keep the server and client connected. The bufferReader and bufferWriter objects are used for writing and reading data only. Each socket object has an output stream that can be used to send data and an input stream to receive data from anywhere it is connected.
Each client types its username after connecting to the server. Then, using the readLine method, we get this username and set the property of this class.
This class also has a static array that contains the rest of the clients. This presentation is for the purpose of sending messages to clients. To do this, we use the broadcastMessage method, the implementation of which will be shown below. Also, if an exception occurs, the socket will be closed and the client will be deleted from the client array.

# Client
When the main method of this class is executed, the user first enters his username. A socket object is then created and connected to the same port on which the socket server is created. This socket is the bridge between the client and the server. As long as this socket is connected, the connection between the server and the socket is established. This socket is available on the server via the serverSocket.accept method. This socket is then provided to the handler client and the various operations are performed in a separate thread. There are also two other methods in the main method of the client class. Named: getMessageFromOtherUsers and sendMessage. These two methods are two infinite loops that receive and send data. But the reason these two loops work at the same time is that the first loop runs in a new thread
