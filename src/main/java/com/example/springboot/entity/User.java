package com.example.springboot.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {
    @NotNull
    private Long mobile;
    @NotNull
    private Long mpin;


    public UserTable toUserTable() {
        return new UserTable(this.mobile, this.mpin);
    }
}
