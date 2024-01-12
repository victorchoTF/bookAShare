import client.LoginRegisterPage;
import server.*;

import javax.swing.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Theme", JOptionPane.ERROR_MESSAGE);
        }

        Server server = new Server(ORMManager.loadUsers(), ORMManager.loadBooks());

        new LoginRegisterPage(server);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ORMManager.saveUsers(Arrays.stream(server.getUsers()).toList());
            ORMManager.saveBooks(Arrays.stream(server.getBooks()).toList());
        }));
    }
}