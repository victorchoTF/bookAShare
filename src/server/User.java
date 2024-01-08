package server;

import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private String password;
    private final ArrayList<Book> books;

    public User(String username, String email, String password){
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.books = new ArrayList<>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public Book getBook(int idx){
        return this.books.get(idx);
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void removeBook(int idx){
        this.books.remove(idx);
    }

    public Book[] getBooks(){
        return this.books.toArray(new Book[0]);
    }
}
