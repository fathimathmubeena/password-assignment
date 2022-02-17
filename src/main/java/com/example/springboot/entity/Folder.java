package com.example.springboot.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;

@Data
public class Folder {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NotBlank
    private final String name;
    private final Long userId;


    public Folder(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public FolderTable toFolderTable() {
        return new FolderTable(this.id, this.name, this.userId);
    }
}
