package dev.annonymoys.dev_uwallet.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable; // Importe org.springframework.lang.Nullable

public class UserUpdateDTO {
    @Nullable
    private String username;

    @Nullable
    @Email(message = "Formato de email inválido")
    private String email;

    @Nullable
    @Size(min = 8)
    private String password;

    public UserUpdateDTO(String req) {}

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
