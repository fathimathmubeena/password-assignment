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
    @Column(name = "user_id")
    private Long userId;

    public FolderTable(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Folder toFolder() {
        return new Folder(this.id, this.name, this.userId);
    }
}
