package server;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ORMManager {
    private static final String USER_FILE = "src/db/users.txt";
    private static final String BOOK_FILE = "src/db/books.txt";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] metaData = line.split(":");
                String[] parts = metaData[1].split(",");
                if (parts.length == 3) {
                    users.add( (metaData[0].equals("User")) ?
                            new User(parts[0], parts[1], parts[2]) :
                            new Admin(parts[0], parts[2])
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Book:")) {
                    String[] parts = line.replace("Book:", "").split(",");
                    Book book = new Book(parts[0], parts[1], parts[2], parts[3]);

                    books.add(book);
                }
                else if (line.startsWith("Comments:")) {
                    for (String comment : line.replace("Comments:", "").replace("!", "").split("!")) {
                        String[] commentParts = comment.split(",");

                        if (commentParts.length != 3)
                            continue;

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        books.getLast().addComment(new Comment(commentParts[0], commentParts[1], LocalDateTime.parse(commentParts[2], formatter)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(user.getClass().getName().replace("server.", "")
                        + ":" + user.getUsername() + "," + user.getEmail() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (Book book : books) {
                writer.write("Book:"+book.getTitle() + "," + book.getCover() + "," +
                        book.getAuthor() + "," + book.getDescription());

                Comment[] comments = book.getComments();
                if (comments.length > 0) {
                    writer.write("\nComments:");
                    for (Comment comment : comments) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        writer.write(comment.getUsername() +
                                "," + comment.getContent() +
                                "," + comment.getPostedAt().format(formatter) + "!");
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
