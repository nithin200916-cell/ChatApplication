import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    Socket socket;

    BufferedReader input;
    PrintWriter output;

    String username;

    public ClientHandler(Socket socket) {

        this.socket = socket;

        try {

            input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

            output = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {

            username = input.readLine();

            ChatServer.broadcast(
                    username + " joined the chat.",
                    this
            );

            String message;

            while ((message = input.readLine()) != null) {

                System.out.println(
                        username + ": " + message
                );

                ChatServer.broadcast(
                        username + ": " + message,
                        this
                );
            }

        } catch (IOException e) {

            System.out.println(
                    username + " disconnected."
            );

        } finally {

            ChatServer.removeClient(this);

            ChatServer.broadcast(
                    username + " left the chat.",
                    this
            );

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {

        output.println(message);
    }
}