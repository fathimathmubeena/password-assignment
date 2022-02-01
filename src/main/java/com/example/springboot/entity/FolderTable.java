package com.example.springboot.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "folder_tbl")
@Data
public class FolderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long mobile;

    public FolderTable(Long id, String name, Long mobile) {
        this.id = id;
        this.name = name;
    }

    public Folder toFolder() {
        return new Folder(this.id, this.name, this.mobile);
    }
}
