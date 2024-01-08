import server.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner ui = new Scanner(System.in);
        User user = new User("hackera", "hack@gmail.com", "1234567");
        Comment[] comments = new Comment[10];

        for (int i=0; i < 10; i++){
            System.out.print("Input comment: ");
            String content = ui.nextLine();
            comments[i] = new Comment(user, content);
        }

        Book[] books = new Book[5];
        for (int i = 0; i < 5; i++){
            System.out.print("Input title: ");
            String title = ui.nextLine();
            Book book = new Book(title, "cover", "Ivan", "A book");
            user.addBook(book);
            book.addComment(comments[i]);
            book.addComment(comments[i+1]);

            books[i] = book;
        }

        for (Book book: books) {
            System.out.println(book.getTitle());
            for (Comment comment : book.getComments()) {
                System.out.println(comment.getContent());
                System.out.println(comment.getPostedAt());
            }
        }
    }
}