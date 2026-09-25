import java.io.*;
import java.net.*;

public class ChatClient {

    private Socket socket;

    private BufferedReader input;
    private PrintWriter output;

    public ChatClient(String username)
            throws IOException {

        socket = new Socket(
                "localhost",
                5000
        );

        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );

        output = new PrintWriter(
                socket.getOutputStream(),
                true
        );

        output.println(username);
    }

    public void sendMessage(String message) {

        output.println(message);
    }

    public void receiveMessages(
            MessageListener listener) {

        Thread thread = new Thread(() -> {

            try {

                String message;

                while ((message =
                        input.readLine()) != null) {

                    listener.onMessage(message);
                }

            } catch (IOException e) {

                listener.onMessage(
                        "Disconnected from server."
                );
            }
        });

        thread.start();
    }

    public interface MessageListener {

        void onMessage(String message);
    }
}