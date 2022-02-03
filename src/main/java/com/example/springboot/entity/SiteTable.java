package com.example.springboot.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "site_tbl")
@Data
@RequiredArgsConstructor
public class SiteTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String name;
    private String username;
    private String password;
    @Column(name = "folder_id")
    private Long folderId;
    private String notes;
    @Column(name = "user_id")
    private Long userId;
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public SiteTable(Long id, String url, String name, String username, String password, Long folderId, String notes, Long userId) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.username = username;
        this.password = password;
        this.folderId = folderId;
        this.notes = notes;
        this.userId = userId;
    }

    public Site toSite() {
        return new Site(this.id, this.url, this.name, this.username, this.password, this.folderId, this.notes, this.userId);
    }
}

