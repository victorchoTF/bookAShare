package client;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

import server.Admin;
import server.Book;
import server.Server;
import server.User;

public class AdminPage extends JFrame {

    private final JTabbedPane tabbedPane;
    private final Server server;

    public AdminPage(Server server) {
        super("Admin Page");
        this.server = server;
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        createRegisterTab();
        createBookCreationTab();
        createEditBookTab();
        createDeleteBookTab();

        add(tabbedPane);
        setVisible(true);
    }

    private void createBookCreationTab() {
        JPanel createBookPanel = new JPanel();
        createBookPanel.setLayout(new BoxLayout(createBookPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Share a book to bookAShare");
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JTextField bookTitleField = new JTextField(20);
        JTextField bookCoverField = new JTextField(20);
        JTextField bookAuthorField = new JTextField(20);
        JTextField bookDescriptionField = new JTextField(20);

        createBookPanel.add(createPaddedPanel(title));
        createBookPanel.add(createFieldPanel("Title:", bookTitleField));
        createBookPanel.add(createFieldPanel("Cover:", createFilePanel(bookCoverField)));
        createBookPanel.add(createFieldPanel("Author:", bookAuthorField));
        createBookPanel.add(createFieldPanel("Description:", bookDescriptionField));

        JButton submitButton = createSubmitButton(e -> createBook(bookTitleField, bookCoverField, bookAuthorField, bookDescriptionField));

        JPanel submitPanel = createSubmitPanel(submitButton);
        createBookPanel.add(submitPanel);

        tabbedPane.addTab("Create a Book", createBookPanel);
    }

    private void createRegisterTab() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        JLabel registerTitle = new JLabel("Create a new Admin for bookAShare");
        registerTitle.setFont(new Font("Arial", Font.BOLD, 16));
        registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField registerUsernameField = new JTextField(20);
        JPasswordField registerPasswordField = new JPasswordField(20);

        registerPanel.add(createPaddedPanel(registerTitle));
        registerPanel.add(createFieldPanel("Username:", registerUsernameField));
        registerPanel.add(createFieldPanel("Password:", registerPasswordField));

        JButton registerButton = createSubmitButton(e -> registerUser(registerUsernameField, registerPasswordField));

        JPanel submitPanel = createSubmitPanel(registerButton);
        registerPanel.add(submitPanel);

        tabbedPane.addTab("Register", registerPanel);
    }

    private void createEditBookTab() {
        JPanel editBookPanel = new JPanel();
        editBookPanel.setLayout(new BoxLayout(editBookPanel, BoxLayout.Y_AXIS));

        JLabel editBookTitle = new JLabel("Edit a Book from bookAShare");
        makeTitleBold(editBookPanel, editBookTitle);

        tabbedPane.insertTab("Edit Book", null, editBookPanel, null, 2);
    }

    private void makeTitleBold(JPanel editBookPanel, JLabel editBookTitle) {
        editBookTitle.setFont(new Font("Arial", Font.BOLD, 16));
        editBookTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 2, 10, 10));

        for (Book existingBook : server.getBooks()) {
            try {
                JPanel bookPanel = createBookPanel(existingBook);
                booksPanel.add(bookPanel);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, existingBook.getCover() + " not found!", "Invalid Path", JOptionPane.ERROR_MESSAGE);
            }
        }

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        editBookPanel.add(createPaddedPanel(editBookTitle));
        editBookPanel.add(scrollPane);
    }

    private void editBook(Book book) {
        JTextField bookTitleField = new JTextField(20);
        JTextField bookCoverField = new JTextField(20);
        JTextField bookAuthorField = new JTextField(20);
        JTextField bookDescriptionField = new JTextField(20);

        bookTitleField.setText(book.getTitle());
        bookCoverField.setText(book.getCover());
        bookAuthorField.setText(book.getAuthor());
        bookDescriptionField.setText(book.getDescription());

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.add(createFieldPanel("Title:", bookTitleField));
        editPanel.add(createFieldPanel("Cover:", createFilePanel(bookCoverField)));
        editPanel.add(createFieldPanel("Author:", bookAuthorField));
        editPanel.add(createFieldPanel("Description:", bookDescriptionField));

        int option = JOptionPane.showConfirmDialog(this, editPanel, "Edit Book", JOptionPane.OK_CANCEL_OPTION);
        String title = bookTitleField.getText();
        String author = bookAuthorField.getText();
        if (option == JOptionPane.OK_OPTION) {
            for (Book currentBook : server.getBooks())
                if (currentBook.getTitle().equals(title) && currentBook.getAuthor().equals(author)){
                    JOptionPane.showMessageDialog(null,
                            "A book titled "+ title +" has already been written by " + author +"!",
                            "Invalid Book Data", JOptionPane.ERROR_MESSAGE);

                    editBook(book);
                    return;
                }

            try {
                book.setTitle(title);
                book.setCover(bookCoverField.getText());
                book.setAuthor(author);
                book.setDescription(bookDescriptionField.getText());
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Book Data", JOptionPane.ERROR_MESSAGE);

                editBook(book);
                return;
            }
            updateBookTab(2);
            updateBookTab(3);
        }
    }

    private void createDeleteBookTab() {
        JPanel deleteBookPanel = new JPanel();
        deleteBookPanel.setLayout(new BoxLayout(deleteBookPanel, BoxLayout.Y_AXIS));

        JLabel deleteBookTitle = new JLabel("Delete a Book from bookAShare");
        makeTitleBold(deleteBookPanel, deleteBookTitle);

        tabbedPane.addTab("Delete Book", deleteBookPanel);
    }

    private JPanel createBookPanel(Book book) throws Exception {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BorderLayout());
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        bookPanel.setPreferredSize(new Dimension(80, 120));

        URL imageURL = MainPage.class.getResource(book.getCover());
        if (imageURL != null) {
            ImageIcon imageIcon = new ImageIcon(resizeImage(imageURL, 70, 100));
            JLabel imageLabel = new JLabel(imageIcon);

            bookPanel.add(imageLabel, BorderLayout.CENTER);
        } else throw new Exception();

        JLabel detailsLabel = new JLabel("<html>Written by " + book.getAuthor() + "</html>");
        detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        detailsLabel.setHorizontalAlignment(JLabel.CENTER);

        bookPanel.add(detailsLabel, BorderLayout.SOUTH);

        bookPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tabbedPane.getSelectedIndex() == 3)
                    deleteBook(book);
                else if (tabbedPane.getSelectedIndex() == 2)
                    editBook(book);
            }
        });

        Dimension d = bookPanel.getMaximumSize();
        d.height = 120;
        bookPanel.setMaximumSize(d);

        return bookPanel;
    }

    private Image resizeImage(URL imageURL, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imageURL);
        Image image = imageIcon.getImage();
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void createBook(JTextField titleField, JTextField coverField, JTextField authorField, JTextField descriptionField) {
        String title = titleField.getText();
        String cover = coverField.getText();
        String author = authorField.getText();
        String description = descriptionField.getText();

        for (Book book : server.getBooks())
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)){
                JOptionPane.showMessageDialog(null,
                        "A book titled "+ title +" has already been written by " + author +"!",
                        "Invalid Book Data", JOptionPane.ERROR_MESSAGE);
                return;
            }

        try {
            server.addBook(new Book(title, cover, author, description));
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Book Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        titleField.setText("");
        coverField.setText("");
        authorField.setText("");
        descriptionField.setText("");

        updateBookTab(2);
        updateBookTab(3);
    }

    private void registerUser(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        for (User user : server.getUsers())
            if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(null, username + " already has an admin account!", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                return;
            }

        try {
            server.addUser(new Admin(username, password));
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Admin Data", JOptionPane.ERROR_MESSAGE);
            return;
        }
        usernameField.setText("");
        passwordField.setText("");
    }

    private JPanel createFieldPanel(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
        panel.add(component);
        return panel;
    }

    private JPanel createFilePanel(JTextField fileField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> selectFile(fileField));
        panel.add(browseButton);

        return panel;
    }

    private void selectFile(JTextField fileField) {
        JFileChooser fileChooser = new JFileChooser("/home/victorchotf/IdeaProjects/bookAShare/src/assets");

        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String baseDirectory = "../assets";

            if (isImageFile(selectedFile)) {
                String relativePath = getRelativePath(baseDirectory,
                        Arrays.stream(selectedFile.getAbsolutePath().split("/")).toList().getLast());
                fileField.setText(relativePath);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid image file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    private static String getRelativePath(String baseDirectory, String absolutePath) {
        return baseDirectory + "/" + absolutePath;
    }

    private JButton createSubmitButton(ActionListener actionListener) {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(actionListener);
        return submitButton;
    }

    private JPanel createSubmitPanel(JButton submitButton) {
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        return submitPanel;
    }

    private JPanel createPaddedPanel(Component component) {
        JPanel paddedPanel = new JPanel();
        paddedPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        paddedPanel.add(component);
        return paddedPanel;
    }

    private void deleteBook(Book book) {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + book.getTitle() + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            server.removeBook(book);
            updateBookTab(2);
            updateBookTab(3);
        }
    }

    private void updateBookTab(int tabIdx) {
        SwingUtilities.invokeLater(() -> {
            JPanel booksPanel = new JPanel();
            booksPanel.setLayout(new GridLayout(0, 2, 10, 10));

            for (Book book : server.getBooks()) {
                try {
                    JPanel bookPanel = createBookPanel(book);
                    booksPanel.add(bookPanel);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, book.getCover() + " not found!", "Invalid Path", JOptionPane.ERROR_MESSAGE);
                }
            }

            Component[] components = ((JPanel) tabbedPane.getComponentAt(tabIdx)).getComponents();
            for (Component component : components) {
                if (component instanceof JScrollPane scrollPane) {
                    scrollPane.setViewportView(booksPanel);
                    return;
                }
            }
        });
    }
}
