package client;

import server.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class MainPage extends JFrame {
    private ArrayList<BookPanel> bookPanels;

    public MainPage(ArrayList<Book> books) {
        setTitle("Books Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setResizable(false);

        // Initialize components
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns, with 10px horizontal and vertical gap
        JScrollPane scrollPane = new JScrollPane(booksPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Example data (replace it with your actual data)
        this.bookPanels = new ArrayList<>();
        for (Book book : books) {
            BookPanel bookPanel = new BookPanel(book.getCover(), book.getTitle(), book.getAuthor());
            booksPanel.add(bookPanel);
            bookPanels.add(bookPanel);
        }

        setLocationRelativeTo(null); // Center the frame

        setVisible(true);
    }

    public BookPanel[] getBooks() {
        return this.bookPanels.toArray(new BookPanel[0]);
    }

    public static class BookPanel extends JPanel {
        public BookPanel(String cover, String title, String author) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Initial border color
            setPreferredSize(new Dimension(100, 150)); // Adjusted preferred size

            // Load and resize the image
            try {
                URL imageURL = MainPage.class.getResource(cover);
                if (imageURL != null) {
                    ImageIcon imageIcon = new ImageIcon(resizeImage(imageURL, 70, 100));
                    JLabel imageLabel = new JLabel(imageIcon);

                    add(imageLabel, BorderLayout.CENTER);
                } else {
                    System.err.println("Image not found: " + cover);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Title and Author label
            JLabel detailsLabel = new JLabel("<html>Written by " + author + "</html>");
            detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            detailsLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label horizontally

            // Add detailsLabel to BookPanel
            add(detailsLabel, BorderLayout.SOUTH);

            // Add MouseListener to open a new window on click
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new BookDetailsDialog(title, author);
                }
            });
        }

        private Image resizeImage(URL imageURL, int width, int height) {
            ImageIcon imageIcon = new ImageIcon(imageURL);
            Image image = imageIcon.getImage();
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    public static class BookDetailsDialog extends JDialog {
        public BookDetailsDialog(String title, String author) {
            setTitle("Book Details");
            setLayout(new BorderLayout());
            setSize(400, 300);
            setResizable(false);

            // Book details panel
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BorderLayout());

            // Title and Author labels
            JLabel titleLabel = new JLabel("Title: " + title);
            JLabel authorLabel = new JLabel("Author: " + author);

            // Add title and author labels to detailsPanel
            detailsPanel.add(titleLabel, BorderLayout.NORTH);
            detailsPanel.add(authorLabel, BorderLayout.CENTER);

            // Add detailsPanel to the dialog
            add(detailsPanel, BorderLayout.NORTH);

            // Description panel
            JLabel descriptionLabel = new JLabel("<html><b>Description:</b> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed accumsan sodales ex, id viverra justo tempus sit amet. In tincidunt, orci eu pulvinar tristique, tellus ligula bibendum nulla, vel gravida odio quam id leo. Suspendisse nec urna vitae leo aliquam tristique id vitae dui. Etiam eu venenatis urna. In hac habitasse platea dictumst. Nunc euismod arcu eu libero luctus, nec tempus erat fringilla.</html>");
            descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add descriptionLabel to the dialog
            add(descriptionLabel, BorderLayout.CENTER);

            // Comments panel
            JPanel commentsPanel = new JPanel(new BorderLayout());
            commentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            JTextArea commentsArea = new JTextArea();
            commentsArea.setLineWrap(true);
            JScrollPane commentsScrollPane = new JScrollPane(commentsArea);
            commentsPanel.add(commentsScrollPane, BorderLayout.CENTER);

            JButton postButton = new JButton("Post");
            postButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    postComment(commentsArea);
                }
            });
            commentsPanel.add(postButton, BorderLayout.EAST);

            // Add commentsPanel to the dialog
            add(commentsPanel, BorderLayout.SOUTH);

            setLocationRelativeTo(null); // Center the dialog

            setVisible(true);
        }

        private void postComment(JTextArea commentsArea) {
            String comment = commentsArea.getText();
            if (!comment.isEmpty()) {
                // You can add the logic to handle the posted comment here
                System.out.println("Posted Comment: " + comment);
                commentsArea.setText(""); // Clear the comment area after posting
            }
        }
    }

    public static void main(String[] args) {
        // Example usage
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            books.add(new Book("Book " + i, "../assets/book-cover.jpg", "Author " + i, "Description " + i));
        new MainPage(books);
    }
}
