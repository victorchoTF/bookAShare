package server;

import server.exception.InvalidEmailException;
import server.exception.MaxLengthException;
import server.exception.MinLengthException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) throws InvalidEmailException, MinLengthException, MaxLengthException{
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
    }

    public void setUsername(String username) throws MinLengthException, MaxLengthException {
        final int minLength = 5;
        final int maxLength = 50;
        final int usernameLength = username.length();

        if (usernameLength < minLength)
            throw new MinLengthException("Username", minLength);

        if (usernameLength > maxLength)
            throw new MaxLengthException("Username", maxLength);


        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (! User.validEmail(email))
            throw new InvalidEmailException(email);

        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) throws MinLengthException {
        final int minLength = 4;
        if (password.length() < 4)
            throw new MinLengthException("Password", minLength);

        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public static boolean validEmail(String email){
        String regex = "^[^@]{4,40}@(gmail\\.com|abv\\.bg|outlook\\.com|yahoo\\.com|mail\\.com)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
