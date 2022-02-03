package com.example.springboot.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Data
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @NotNull
    private Long username;
    @NotNull
    private Long password;

    public User(Long id, Long username, Long password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    public UserTable toUserTable() {
        return new UserTable(this.username, this.password);
    }
}




