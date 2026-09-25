import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public RegisterFrame() {

        setTitle("Create Account");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        registerButton.addActionListener(e -> register());

        backButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private void register() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields"
            );
            return;
        }

        String sql =
                "INSERT INTO users(username,password) VALUES(?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!"
            );

            new LoginFrame();
            dispose();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username already exists!"
            );
        }
    }
}