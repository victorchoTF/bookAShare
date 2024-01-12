package server;

import server.exception.MaxLengthException;
import server.exception.MinLengthException;

import java.util.ArrayList;

public class Book {
    private String title;
    private String cover;
    private String author;
    private String description;
    private final ArrayList<Comment> comments;

    public Book(String title, String cover, String author, String description) throws MinLengthException, MaxLengthException{
        this.setTitle(title);
        this.setCover(cover);
        this.setAuthor(author);
        this.setDescription(description);
        this.comments = new ArrayList<>();
    }

    public void setTitle(String title) throws MinLengthException, MaxLengthException {
        final int minLength = 2;
        final int maxLength = 100;
        final int titleLength = title.length();

        if (titleLength < minLength)
            throw new MinLengthException("Title", minLength);

        if (titleLength > maxLength)
            throw new MaxLengthException("Title", maxLength);

        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setCover(String cover) throws MinLengthException, MaxLengthException{
        final int minLength = 2;
        final int maxLength = 50;
        final int coverLength = cover.length();

        if (coverLength < minLength)
            throw new MinLengthException("Cover", minLength);

        if (coverLength > maxLength)
            throw new MaxLengthException("Cover", maxLength);

        this.cover = cover;
    }

    public String getCover(){
        return this.cover;
    }

    public void setDescription(String description) throws MinLengthException, MaxLengthException{
        final int minLength = 5;
        final int maxLength = 1000;
        final int descriptionLength = description.length();

        if (descriptionLength < minLength)
            throw new MinLengthException("Description", minLength);

        if (descriptionLength > maxLength)
            throw new MaxLengthException("Description", maxLength);

        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setAuthor(String author) throws MinLengthException, MaxLengthException{
        final int minLength = 5;
        final int maxLength = 100;
        final int authorLength = author.length();

        if (authorLength < minLength)
            throw new MinLengthException("Author", minLength);

        if (authorLength > maxLength)
            throw new MaxLengthException("Author", maxLength);

        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public Comment[] getComments(){
        return this.comments.toArray(new Comment[0]);
    }
}
