import client.LoginRegisterPage;
import server.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception{
        Server server = new Server(ORMManager.loadUsers(), ORMManager.loadBooks());

        new LoginRegisterPage(server);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ORMManager.saveUsers(Arrays.stream(server.getUsers()).toList());
            ORMManager.saveBooks(Arrays.stream(server.getBooks()).toList());
        }));
    }
}