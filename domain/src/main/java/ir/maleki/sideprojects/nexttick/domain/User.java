package ir.maleki.sideprojects.nexttick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    @Column(nullable = false, name = "PASSWORD")
    private String encodedPassword;

    public User(String username, String encodedPassword) {
        this.username = username;
        this.encodedPassword = encodedPassword;
    }

    public User() {
    }

    public String username() {
        return username;
    }

    public String encodedPassword() {
        return encodedPassword;
    }
}
