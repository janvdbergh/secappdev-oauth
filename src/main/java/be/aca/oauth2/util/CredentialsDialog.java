package be.aca.oauth2.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CredentialsDialog extends JDialog {

    private final JTextField userName;
    private final JPasswordField password;
    private final JTextField securityToken;
    private boolean result;

    public CredentialsDialog() {
        super((JFrame) null, "Credentials", true);

        setLayout(new GridBagLayout());

        add(new JLabel("User name:"), createConstraints(0, 0, 1, 1));
        add(userName = new JTextField(20), createConstraints(1, 0, 1, 1));

        add(new JLabel("Password:"), createConstraints(0, 1, 1, 1));
        add(password = new JPasswordField(20), createConstraints(1, 1, 1, 1));

        add(new JLabel("Security token:"), createConstraints(0, 2, 1, 1));
        add(securityToken = new JTextField(20), createConstraints(1, 2, 1, 1));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(buttonPanel, createConstraints(0, 3, 2, 1));

        JButton okButton= new JButton("Ok");
        okButton.addActionListener(actionEvent -> setResult(true));
        buttonPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(actionEvent -> setResult(false));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                dispose();
            }
        });

        pack();
    }

    public boolean askCredentials() {
        setVisible(true);
        return getResult();
    }

    public boolean getResult() {
        return result;
    }

    private void setResult(boolean result) {
        this.result = result;
        this.setVisible(false);
        this.dispose();
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getSecurityToken() {
        return securityToken.getText();
    }

    private GridBagConstraints createConstraints(int x, int y, int width, int height) {
        return new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);
    }
}
