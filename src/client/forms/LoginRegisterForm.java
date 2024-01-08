package client.forms;

import server.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterForm extends JFrame {

    private JTabbedPane tabbedPane;
    private JPanel loginPanel, registerPanel;
    private JTextField loginEmailField, registerUsernameField, registerEmailField;
    private JPasswordField loginPasswordField, registerPasswordField;
    private JButton loginButton, registerButton;

    public LoginRegisterForm() {
        super("bookAShare Login/Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        tabbedPane = new JTabbedPane();

        loginPanel();
        registerPanel();

        tabbedPane.addTab("Login", loginPanel);
        tabbedPane.addTab("Register", registerPanel);

        add(tabbedPane);
        pack();
        setVisible(true);
    }

    private void loginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel loginTitle = new JLabel("Log in to your bookAShare");
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginEmailField = new JTextField(20);
        loginPasswordField = new JPasswordField(20);
        loginButton = createSubmitButton("Login", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login button click
                // You can add logic to authenticate the user here
            }
        });

        loginPanel.add(createPaddedPanel(loginTitle));
        loginPanel.add(createFieldPanel("Email:", loginEmailField));
        loginPanel.add(createFieldPanel("Password:", loginPasswordField));
        loginPanel.add(createSubmitPanel(loginButton));
    }

    private void registerPanel() {
        registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        JLabel registerTitle = new JLabel("Create a brand new bookAShare account");
        registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerUsernameField = new JTextField(20);
        registerEmailField = new JTextField(20);
        registerPasswordField = new JPasswordField(20);
        registerButton = createSubmitButton("Register", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle register button click
                // You can create a new User instance with the provided data
                String username = registerUsernameField.getText();
                String email = registerEmailField.getText();
                String password = new String(registerPasswordField.getPassword());

                User newUser = new User(username, email, password);
                // You can add further logic to save the user information
            }
        });

        registerPanel.add(createPaddedPanel(registerTitle));
        registerPanel.add(createFieldPanel("Username:", registerUsernameField));
        registerPanel.add(createFieldPanel("Email:", registerEmailField));
        registerPanel.add(createFieldPanel("Password:", registerPasswordField));
        registerPanel.add(createSubmitPanel(registerButton));
    }

    private JPanel createFieldPanel(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
        panel.add(component);
        return panel;
    }

    private JButton createSubmitButton(String buttonText, ActionListener actionListener) {
        JButton submitButton = new JButton(buttonText);
        submitButton.addActionListener(actionListener);
        return submitButton;
    }

    private JPanel createSubmitPanel(JButton submitButton) {
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        return submitPanel;
    }

    private JPanel createPaddedPanel(Component component) {
        JPanel paddedPanel = new JPanel();
        paddedPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        paddedPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        paddedPanel.add(component);
        return paddedPanel;
    }
}
