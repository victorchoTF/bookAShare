import client.forms.LoginRegisterForm;
import server.*;

import java.util.Arrays;

// TODO:
//  1) Finish CRUD for admins (Create admins, Update books, Remove books)
//  2) Forms feedback (Use JOptionPane)

public class Main {
    public static void main(String[] args) {
        // Create an instance of the Server class
        Server server = new Server(ORMManager.loadUsers(), ORMManager.loadBooks());

        new LoginRegisterForm(server);

        // Save modified data back to files
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ORMManager.saveUsers(Arrays.stream(server.getUsers()).toList());
            ORMManager.saveBooks(Arrays.stream(server.getBooks()).toList());
        }));
    }
}