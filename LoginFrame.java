import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Chat Application - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String sql =
                "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!"
                );

                new ChatFrame(username);
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password"
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}