import client.forms.LoginRegisterForm;
import server.*;

import java.util.Arrays;

// TODO: 1) Handle Admin accounts in /db 2) Fix image issues 3) Forms feedback

public class Main {
    public static void main(String[] args) {
        // Create an instance of the Server class
        Server server = new Server(ORMManager.loadUsers(), ORMManager.loadBooks());
        server.addUser(new Admin("pepo", "1234"));

        new LoginRegisterForm(server);

        // Save modified data back to files
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ORMManager.saveUsers(Arrays.stream(server.getUsers()).toList());
            ORMManager.saveBooks(Arrays.stream(server.getBooks()).toList());
        }));
    }
}