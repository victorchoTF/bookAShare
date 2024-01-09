package server;

import client.MainPage;
import client.forms.LoginRegisterForm;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ORMManager {
    private static final String USER_FILE = "src/db/users.txt";
    private static final String BOOK_FILE = "src/db/books.txt";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
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
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Book book = new Book(parts[0], parts[1], parts[2], parts[3]);

                    // Check if there are comments
                    if (parts.length > 4 && parts[4].equals("Comments:")) {
                        int commentIndex = 5;
                        while (commentIndex < parts.length - 2 && parts[commentIndex + 2].equals("Comments:")) {
                            User user = new User(parts[commentIndex], "", ""); // Assuming you don't store email and password in comments
                            Comment comment = new Comment(user, parts[commentIndex + 1]);
                            book.addComment(comment);
                            commentIndex += 3;
                        }
                    }

                    books.add(book);
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
                writer.write(user.getUsername() + "," + user.getEmail() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getCover() + "," +
                        book.getAuthor() + "," + book.getDescription());

                Comment[] comments = book.getComments();
                if (comments.length > 0) {
                    writer.write("Comments:");
                    for (Comment comment : comments) {
                        writer.write(comment.getUser().getUsername() +
                                "," + comment.getContent() +
                                "," + comment.getPostedAt());
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
