package server;

public class Admin extends User{
    public Admin(String username, String password){
        super(username, username + "_admin@mail.com", password);
    }
}
