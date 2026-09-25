import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame {

    JTextArea chatArea;
    JTextField messageField;
    JButton sendButton;

    ChatClient client;

    String username;

    public ChatFrame(String username) {

        this.username = username;

        setTitle("Chat Application - " + username);

        setSize(600, 500);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // Chat area

        chatArea = new JTextArea();

        chatArea.setEditable(false);

        chatArea.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JScrollPane scrollPane =
                new JScrollPane(chatArea);

        // Bottom panel

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        messageField = new JTextField();

        sendButton =
                new JButton("Send");

        bottomPanel.add(
                messageField,
                BorderLayout.CENTER
        );

        bottomPanel.add(
                sendButton,
                BorderLayout.EAST
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // Connect to server

        try {

            client =
                    new ChatClient(username);

            client.receiveMessages(
                    message -> {

                        SwingUtilities.invokeLater(() -> {

                            chatArea.append(
                                    message + "\n"
                            );

                        });
                    }
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not connect to server."
            );
        }

        sendButton.addActionListener(
                e -> sendMessage()
        );

        messageField.addActionListener(
                e -> sendMessage()
        );

        setVisible(true);
    }

    private void sendMessage() {

        String message =
                messageField.getText().trim();

        if (!message.isEmpty()) {

            client.sendMessage(message);

            chatArea.append(
                    "You: " + message + "\n"
            );

            messageField.setText("");
        }
    }
}