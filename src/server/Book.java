package server;

import java.util.ArrayList;

public class Book {
    private String title;
    private String cover;
    private String author;
    private String description;
    private final ArrayList<Comment> comments;

    public Book(String title, String cover, String author, String description){
        this.setTitle(title);
        this.setCover(cover);
        this.setAuthor(author);
        this.setDescription(description);
        this.comments = new ArrayList<>();
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setCover(String cover){
        this.cover = cover;
    }

    public String getCover(){
        return this.cover;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public Comment getComment(int idx){
        return this.comments.get(idx);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void removeComment(int idx){
        this.comments.remove(idx);
    }

    public Comment[] getComments(){
        return this.comments.toArray(new Comment[0]);
    }
}
