package com.example.carsharing.model;

import jakarta.persistence.*;
import java.util.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, message = "Username must be at least 4 characters")
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @NotBlank
    @Email(message = "Please enter a valid email")
    @Column(unique = true)
    private String email;



    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
