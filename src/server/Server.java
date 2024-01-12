package server;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final ArrayList<User> users;
    private final ArrayList<Book> books;
    private User loggedInUser;

    public Server(List<User> users, List<Book> books){
        this.users = new ArrayList<>(users);
        this.books = new ArrayList<>(books);
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void removeBook(Book book){
        this.books.remove(book);
    }

    public Book[] getBooks(){
        return this.books.toArray(new Book[0]);
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public User[] getUsers(){
        return this.users.toArray(new User[0]);
    }

    public User getLoggedInUser(){
        return this.loggedInUser;
    }

    public void setLoggedInUser(User user){
        this.loggedInUser = user;
    }
}
