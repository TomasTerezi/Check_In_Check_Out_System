import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePasswordDialog extends JDialog {

    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ChangePasswordDialog(JFrame parent) {
        super(parent, "Change Password", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(207, 217, 224));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblCurrentPassword = new JLabel("Current Password:");
        lblCurrentPassword.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        panel.add(lblCurrentPassword);

        currentPasswordField = new JPasswordField();
        panel.add(currentPasswordField);

        JLabel lblNewPassword = new JLabel("New Password:");
        lblNewPassword.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        panel.add(lblNewPassword);

        newPasswordField = new JPasswordField();
        panel.add(newPasswordField);

        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        panel.add(lblConfirmPassword);

        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        JButton btnChange = new JButton("Change");
        btnChange.setBorder(null);
        btnChange.setBackground(new Color(39, 85, 160));
        btnChange.setForeground(new Color(255, 255, 255));
        btnChange.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        panel.add(btnChange);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setForeground(new Color(255, 255, 255));
        btnCancel.setBackground(new Color(39, 85, 160));
        btnCancel.setBorder(null);
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancel);

        getContentPane().add(panel);
    }

    private void changePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate new password and confirmation
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        // Perform the password change in the database
        Connection connection = DbConnection.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database connection error.");
            return;
        }

        String username = "UPDATE users SET password = ? WHERE username = ?"; // Replace this with the actual username of the logged-in user
        String query = "UPDATE accounts SET password = ? WHERE username = ? AND password = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, currentPassword);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Password changed successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect current password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating password: " + e.getMessage());
        }
    }

}
