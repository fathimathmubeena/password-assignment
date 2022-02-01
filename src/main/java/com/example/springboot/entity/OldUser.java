package com.example.springboot.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OldUser {
    @NotNull
    private Long username;
    @NotNull
    private Long password;


    public UserTable toUserTable() {
        return new UserTable(this.username, this.password);
    }
}




