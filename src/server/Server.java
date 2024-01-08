package server;

public class Server {
    public Book[] getBooks(){
        return new Book[0];
    }

    public User getLoggedInUser(){
        return new User("Hello", "Shte se samopriklucha", "1234");
    }
}
