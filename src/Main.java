import client.LoginRegisterPage;
import server.*;

import java.util.Arrays;

// TODO:
//  1) Forms feedback (Use JOptionPane)

public class Main {
    public static void main(String[] args) {
        Server server = new Server(ORMManager.loadUsers(), ORMManager.loadBooks());

        new LoginRegisterPage(server);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ORMManager.saveUsers(Arrays.stream(server.getUsers()).toList());
            ORMManager.saveBooks(Arrays.stream(server.getBooks()).toList());
        }));
    }
}