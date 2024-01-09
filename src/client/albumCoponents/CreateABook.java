package client.albumCoponents;

import server.Book;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CreateABook extends JFrame {

    private JPanel bookCreationPanel;
    private JTextField bookTitleField, bookCoverField, bookAuthorField, bookDescriptionField;

    public CreateABook(Server server) {
        super("Create a Book");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        bookCreationPanel = new JPanel();
        bookCreationPanel.setLayout(new BoxLayout(bookCreationPanel, BoxLayout.Y_AXIS));

        createBookCreationTab(server);

        add(bookCreationPanel);
        pack();
        setVisible(true);
    }

    private void createBookCreationTab(Server server) {
        // Create components for the Book Creation tab
        JLabel title = new JLabel("Share a book to bookAShare");
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel titleLabel = new JLabel("Title:");
        bookTitleField = new JTextField(20);

        JLabel coverLabel = new JLabel("Cover:");
        bookCoverField = new JTextField(20);

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        JLabel authorLabel = new JLabel("Author:");
        bookAuthorField = new JTextField(20);

        JLabel descriptionLabel = new JLabel("Description:");
        bookDescriptionField = new JTextField(20);

        // Add components to the Book Creation tab
        bookCreationPanel.add(createPaddedPanel(title));
        bookCreationPanel.add(createFieldPanel(titleLabel, bookTitleField));
        bookCreationPanel.add(createFieldPanel(coverLabel, browseButton));
        bookCreationPanel.add(createFieldPanel(authorLabel, bookAuthorField));
        bookCreationPanel.add(createFieldPanel(descriptionLabel, bookDescriptionField));

        // Create Submit button for the Book Creation tab
        JButton submitButton = createSubmitButton("Submit Book", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBook(server);
            }
        });

        // Create a panel for the Submit button with padding and center it
        JPanel submitPanel = createSubmitPanel(submitButton);
        bookCreationPanel.add(submitPanel);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            bookCoverField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void createBook(Server server) {
        String title = bookTitleField.getText();
        String cover = bookCoverField.getText();
        String author = bookAuthorField.getText();
        String description = bookDescriptionField.getText();

        for (Book book: server.getBooks())
            if (book.getTitle().equals(title) && book.getAuthor().equals(author))
                return;

        server.addBook(new Book(title, cover, author, description));

        bookTitleField.setText("");
        bookCoverField.setText("");
        bookAuthorField.setText("");
        bookDescriptionField.setText("");
    }

    private <C extends  Component> JPanel createFieldPanel(JLabel label, C component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(component);
        return panel;
    }

    private JButton createSubmitButton(String buttonText, ActionListener actionListener) {
        JButton submitButton = new JButton(buttonText);
        submitButton.addActionListener(actionListener);
        return submitButton;
    }

    private JPanel createSubmitPanel(JButton submitButton) {
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        submitPanel.add(submitButton);
        return submitPanel;
    }

    private JPanel createPaddedPanel(Component component) {
        JPanel paddedPanel = new JPanel();
        paddedPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        paddedPanel.add(component);
        return paddedPanel;
    }
}
