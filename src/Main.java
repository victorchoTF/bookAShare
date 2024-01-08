import client.MainPage;
import client.albumCoponents.CreateABook;
import server.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        new MainPage(server);
    }
}