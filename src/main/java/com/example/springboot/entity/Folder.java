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
    private final Long mobile;


    public Folder(Long id, String name, Long mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public FolderTable toFolderTable() {
        return new FolderTable(this.id, this.name, this.mobile);
    }
}
