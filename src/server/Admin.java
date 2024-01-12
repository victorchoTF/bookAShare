package server;

public class Admin extends User{
    public Admin(String username, String password) throws Exception{
        super(username, username + "_admin@mail.com", password);
    }
}
