package server;

import java.util.ArrayList;

public class Server {
    private ArrayList<User> users;
    private ArrayList<Book> books;
    private User loggedInUser;

    public Server(){
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public Book getBook(int idx){
        return this.books.get(idx);
    }

    public void removeBook(int idx){
        this.books.remove(idx);
    }

    public Book[] getBooks(){
        return this.books.toArray(new Book[0]);
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(int idx){
        this.books.remove(idx);
    }

    public User getUser(int idx){
        return this.users.get(idx);
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
