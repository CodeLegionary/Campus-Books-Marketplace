package com.example.demo.model;

public class UserDto {
    public Long id;
    public String username;
    public String email;
    public String role;
    public boolean active;
    public boolean reported;

    // Constructors
    public UserDto() {}

    public UserDto(Long id, String username, String email, String role, boolean active, boolean reported) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
        this.reported = reported;
    }

    // Getters and Setters not necessary
}