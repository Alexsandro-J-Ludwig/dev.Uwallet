package dev.annonymoys.dev_uwallet.user;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Entity
@Table(name = "user")
public class UserModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    private String password;

    public UserModal() {
    }
    public UserModal(String username, String email, String password) {
        this.username = username;
        this.email = email;
        setPassword(password);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordHash.hashPassword(password);
    }


}

class PasswordHash {
    private PasswordHash() {}

    public static String hashPassword(@NotNull String password) {
        Argon2 argon2 = Argon2Factory.create();
        char[] passwordChars = password.toCharArray();
        try{
            return argon2.hash(10, 65536, 1, passwordChars);
        }finally {
            argon2.wipeArray(passwordChars);
        }
    }
    public static boolean verifyPassword(String hashedPassword, @org.jetbrains.annotations.NotNull String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(hashedPassword, password.toCharArray());
    }
}