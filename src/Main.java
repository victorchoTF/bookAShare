import client.MainPage;
import client.albumCoponents.CreateABook;
import client.forms.LoginRegisterForm;
import server.*;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Admin admin = new Admin("Dido", "1234");
        server.addUser(admin);

        for(int i = 0; i < 50; i++)
            server.addBook(new Book("title", "../assets/cover.png", "Az", "Ti"));

        new LoginRegisterForm(server);
    }
}