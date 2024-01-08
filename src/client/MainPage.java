package client;

import server.Book;
import server.Comment;
import server.Server;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainPage extends JFrame {
    private ArrayList<BookPanel> bookPanels;

    public MainPage(Server server) {
        setTitle("Books Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 600);
        setResizable(false);

        // Initialize components
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(booksPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Example data (replace it with your actual data)
        this.bookPanels = new ArrayList<>();
        for (Book book : server.getBooks()) {
            BookPanel bookPanel = new BookPanel(book, server.getLoggedInUser());
            booksPanel.add(bookPanel);
            bookPanels.add(bookPanel);
        }

        setLocationRelativeTo(null); // Center the frame

        setVisible(true);
    }

    public static class BookPanel extends JPanel {
        public BookPanel(Book book, User user) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Initial border color
            setPreferredSize(new Dimension(80, 120)); // Adjusted preferred size

            // Load and resize the image
            try {
                URL imageURL = MainPage.class.getResource(book.getCover());
                if (imageURL != null) {
                    ImageIcon imageIcon = new ImageIcon(resizeImage(imageURL, 70, 100));
                    JLabel imageLabel = new JLabel(imageIcon);

                    add(imageLabel, BorderLayout.CENTER);
                } else {
                    System.err.println("Image not found: " + book.getCover());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Title and Author label
            JLabel detailsLabel = new JLabel("<html>Written by " + book.getAuthor() + "</html>");
            detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            detailsLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label horizontally

            // Add detailsLabel to BookPanel
            add(detailsLabel, BorderLayout.SOUTH);

            // Add MouseListener to open a new window on click
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new BookDetailsDialog(book, user);
                }
            });
        }

        private static Image resizeImage(URL imageURL, int width, int height) {
            ImageIcon imageIcon = new ImageIcon(imageURL);
            Image image = imageIcon.getImage();
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    public static class BookDetailsDialog extends JDialog {
        private ArrayList<JPanel> commentPanels;
        private JPanel commentsPanel; // Declare at class level
        private JPanel commentsPane;

        public BookDetailsDialog(Book book, User user) {
            setTitle("Book Details");
            setLayout(new BorderLayout());

            // Main panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            // Comments panel
            commentsPanel = new JPanel(new BorderLayout());
            commentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            JTextField commentsField = createCommentTextArea();
            JLabel commentsLabel = new JLabel("Comments:");
            commentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
            Dimension d = new Dimension(commentsPanel.getMaximumSize());
            d.height = 15;
            commentsPanel.setMaximumSize(d);

            JButton postButton = new JButton("Post"); // Replace new User with the logged-in User
            postButton.addActionListener(e -> postComment(commentsField, user, book));

            commentsPanel.add(commentsLabel, BorderLayout.WEST);
            commentsPanel.add(commentsField, BorderLayout.CENTER);
            commentsPanel.add(postButton, BorderLayout.EAST);

            // Book details panel
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BorderLayout());

            // Cover image with padding
            ImageIcon coverIcon = new ImageIcon(BookPanel.resizeImage(MainPage.class.getResource(book.getCover()), 200, 250));
            JLabel coverLabel = new JLabel(coverIcon);
            coverLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            detailsPanel.add(coverLabel, BorderLayout.WEST);

            // Text details panel with BoxLayout
            JPanel textDetailsPanel = new JPanel();
            textDetailsPanel.setLayout(new BoxLayout(textDetailsPanel, BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("Title: " + book.getTitle());
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            textDetailsPanel.add(titleLabel);

            JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
            authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            textDetailsPanel.add(authorLabel);

            // Create some space between the text details and the description
            textDetailsPanel.add(Box.createVerticalStrut(10));

            // Description panel
            JLabel descriptionLabel = new JLabel("<html><b>Description:</b><p style='width: 300px'>" + book.getDescription() + "</p></html>");

            descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

            // Add descriptionLabel to the mainPanel
            textDetailsPanel.add(descriptionLabel, BorderLayout.CENTER);

            // Add textDetailsPanel to detailsPanel
            detailsPanel.add(textDetailsPanel, BorderLayout.CENTER);

            // Add detailsPanel to the mainPanel
            mainPanel.add(detailsPanel);

            // Initialize commentsScrollPanePanel
            commentsPane = new JPanel();
            commentsPane.setLayout(new BoxLayout(commentsPane, BoxLayout.Y_AXIS));

            // Load and display comments
            Comment[] comments = book.getComments();
            commentPanels = new ArrayList<>();
            for (Comment comment : comments) {
                JPanel commentPanel = createCommentPanel(comment);
                commentPanels.add(commentPanel);
                commentsPane.add(commentPanel);
            }

            // Add commentsPanel to the mainPanel
            mainPanel.add(commentsPanel);

            // Add commentsScrollPanePanel to the mainPanel
            mainPanel.add(commentsPane);

            // Wrap the mainPanel in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(mainPanel);

            // Add the scrollPane to the dialog
            add(scrollPane, BorderLayout.CENTER);

            setLocationRelativeTo(null); // Center the dialog
            setSize(650, 500);
            setResizable(false);
            setVisible(true);
        }


        private JPanel createCommentPanel(Comment comment) {
            JPanel commentPanel = new JPanel(new BorderLayout());
            commentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel userLabel = new JLabel("<html><b>" + comment.getUser().getUsername() + "</b></html>");
            userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = comment.getPostedAt().format(formatter);

            JLabel postTimeLabel = new JLabel("<html><i>" + formattedDateTime + "</i></html>");
            postTimeLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

            JLabel contentsLabel = new JLabel("<html><p style='width: 400px'>" + comment.getContent() + "</p></html>");
            userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            commentPanel.add(userLabel, BorderLayout.NORTH);
            commentPanel.add(postTimeLabel, BorderLayout.CENTER);
            commentPanel.add(contentsLabel, BorderLayout.SOUTH);

            Dimension preferredSize = commentPanel.getPreferredSize();
            preferredSize.width = 400; // Set your desired width
            commentPanel.setPreferredSize(preferredSize);


            return commentPanel;
        }

        private JTextField createCommentTextArea() {
            JTextField commentsField = new JTextField();

            // Add border and margin
            commentsField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY), // Border color
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            return commentsField;
        }

        private void updateCommentsPanel(Book book) {
            // Clear existing comment panels
            commentsPane.removeAll();

            // Load and display updated comments
            Comment[] comments = book.getComments();
            for (Comment comment : comments) {
                JPanel commentPanel = createCommentPanel(comment);
                commentPanels.add(commentPanel);
                commentsPane.add(commentPanel);
            }

            // Refresh the layout
            commentsPane.revalidate();
            commentsPane.repaint();
        }

        private void postComment(JTextField commentsField, User user, Book book) {
            String comment = commentsField.getText();
            if (!comment.isEmpty()) {
                // You can add the logic to handle the posted comment here
                book.addComment(new Comment(user, comment));
                commentsField.setText(""); // Clear the comment area after posting
                updateCommentsPanel(book);
            }
        }
    }
}