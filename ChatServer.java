import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    static final int PORT = 5000;

    static Vector<ClientHandler> clients =
            new Vector<>();

    public static void main(String[] args) {

        System.out.println("Chat Server Started...");

        try (ServerSocket serverSocket =
                     new ServerSocket(PORT)) {

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println(
                        "New client connected!"
                );

                ClientHandler client =
                        new ClientHandler(socket);

                clients.add(client);

                client.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(
            String message,
            ClientHandler sender) {

        for (ClientHandler client : clients) {

            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void removeClient(
            ClientHandler client) {

        clients.remove(client);
    }
}