package server;

import java.time.Clock;
import java.time.LocalDateTime;

public class Comment {
    private User user;
    private String username;
    private String content;
    private final LocalDateTime postedAt;

    public Comment(User user, String content){
        this.setUser(user);
        this.setContent(content);
        this.postedAt = LocalDateTime.now(Clock.systemDefaultZone());
        this.setUsername(user.getUsername());
    }

    public Comment(String username, String content, LocalDateTime postedAt){
        this.setUsername(username);
        this.setContent(content);
        this.postedAt = postedAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public LocalDateTime getPostedAt() {
        return this.postedAt;
    }
}
